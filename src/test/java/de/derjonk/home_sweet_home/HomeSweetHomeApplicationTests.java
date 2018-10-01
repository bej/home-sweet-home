package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiFunction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeSweetHomeApplicationTests {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OnIncomeCreatedEventListener onIncomeCreatedEventListener;

    Logger logger = LoggerFactory.getLogger(HomeSweetHomeApplicationTests.class);

    @After
    public void cleanup() {
        transactionRepository.deleteAll();
        expenseRepository.deleteAll();
        incomeRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void injectsExpenseRepository() {
        Assert.assertThat(expenseRepository, is(notNullValue()));
    }

    @Test
    public void injectsIncomeRepository() {
        Assert.assertThat(incomeRepository, is(notNullValue()));
    }

    @Test
    public void findAccountByName() {
        accountRepository.save(Account.withName("TestAccount"));
        Assert.assertThat(accountRepository.findByNameIgnoreCase("testaccount").getName(), is("TestAccount"));
    }

    @Test
    public void findExpenseByAccount() {
        Account account = accountRepository.save(Account.withName("ExpenseAccount"));
        expenseRepository.save(
                Expense.forAccount(account)
                        .withAmount(100)
                        .withTitle("Nebenkosten")
                        .end()
        );
        Assert.assertThat(expenseRepository.findByAccountName("ExpenseAccount").size(), is(1));
    }

    @Test
    public void findIncompleteAndCompleteExpenses() {
        Account account = accountRepository.save(Account.withName("IncompleteExpenseAccount"));
        Expense completeExpense = expenseRepository.save(
                Expense.forAccount(account)
                        .withAmount(60)
                        .withTitle("payment complete")
                        .end()
        );
        Expense incompleteExpense = expenseRepository.save(
                Expense.forAccount(account)
                        .withAmount(40)
                        .withTitle("payment incomplete")
                        .end()
        );
        Income income = incomeRepository.save(
                Income.forAccount(account)
                        .withAmount(99)
                        .withTitle("Income")
                        .end()
        );
        transactionRepository.save(Transaction
                .withValue(60)
                .from(income)
                .to(completeExpense)
                .end());
        transactionRepository.save(Transaction
                .withValue(39)
                .from(income)
                .to(incompleteExpense)
                .end());

        {
            List<Expense> expenses = expenseRepository.findAllIncompleteExpenses();
            Assert.assertThat(expenses.size(), is(1));
            Assert.assertThat(expenses.get(0).getTitle(), is(incompleteExpense.getTitle()));
        }

        {
            List<Expense> expenses = expenseRepository.findAllCompleteExpenses();
            Assert.assertThat(expenses.size(), is(1));
            Assert.assertThat(expenses.get(0).getTitle(), is(completeExpense.getTitle()));
        }
    }

    @Test
    public void findIncomeWithRemainingCredit() {
        Account account = accountRepository.save(Account.withName("IncomeWithCreditAccount"));
        Expense completeExpense = expenseRepository.save(
                Expense.forAccount(account)
                        .withAmount(65)
                        .withTitle("payment complete")
                        .end()
        );
        Income income = incomeRepository.save(
                Income.forAccount(account)
                        .withAmount(60)
                        .withTitle("Income to be consumed")
                        .end()
        );

        Income incomeWithCredit = incomeRepository.save(
                Income.forAccount(account)
                        .withAmount(10)
                        .withTitle("Income with credit")
                        .end()
        );
        transactionRepository.save(Transaction
                .withValue(60)
                .from(income)
                .to(completeExpense)
                .end());
        transactionRepository.save(Transaction
                .withValue(5)
                .from(incomeWithCredit)
                .to(completeExpense)
                .end());

        {
            List<Income> allWithRemainingCredit = incomeRepository.findAllWithRemainingCredit();
            Assert.assertThat(allWithRemainingCredit.size(), is(1));
            Assert.assertThat(allWithRemainingCredit.get(0).getTitle(), is(incomeWithCredit.getTitle()));
        }
    }

    @Test
    public void roundtripTest() {
        Account account = accountRepository.save(Account.withName("Mietkonto Meier"));

        BiFunction<Integer, String, Income> incomeGenerator = (amount, title) -> Income.forAccount(account)
                .withTitle(title)
                .withAmount(amount)
                .end();

        BiFunction<Integer, String, Expense> expenseGenerator = (amount, title) -> Expense.forAccount(account)
                .withTitle(title)
                .withAmount(amount)
                .end();

        expenseRepository.saveAll(Arrays.asList(
                expenseGenerator.apply(250, "Nebenkosten September"),
                expenseGenerator.apply(750, "Miete September"),
                expenseGenerator.apply(250, "Nebenkosten Oktober"),
                expenseGenerator.apply(750, "Miete Oktober"),
                expenseGenerator.apply(250, "Nebenkosten November"),
                expenseGenerator.apply(750, "Miete November")
        ));


        onIncomeCreatedEventListener.onIncomeCreated(incomeRepository.save(incomeGenerator.apply(1000, "September")));
        {
            List<Expense> allCompleteExpenses = expenseRepository.findAllCompleteExpenses();
            Assert.assertThat(allCompleteExpenses.size(), is(2));
            Assert.assertThat(allCompleteExpenses.get(0).getTitle(), is("Nebenkosten September"));
            Assert.assertThat(allCompleteExpenses.get(1).getTitle(), is("Miete September"));
        }

        onIncomeCreatedEventListener.onIncomeCreated(incomeRepository.save(incomeGenerator.apply(999, "Oktober")));
        {
            List<Expense> allCompleteExpenses = expenseRepository.findAllCompleteExpenses();
            Assert.assertThat(allCompleteExpenses.size(), is(3));
            Assert.assertThat(allCompleteExpenses.get(2).getTitle(), is("Nebenkosten Oktober"));
            List<Expense> allIncompleteExpenses = expenseRepository.findAllIncompleteExpenses();
            Assert.assertThat(allIncompleteExpenses.size(), is(3));
            Assert.assertThat(allIncompleteExpenses.get(0).getTitle(), is("Miete Oktober"));
            List<Transaction> transactions = transactionRepository.findAllByTo(allIncompleteExpenses.get(0));
            Assert.assertThat(transactions.size(), is(1));
            Assert.assertThat(transactions.get(0).getValue(), is(749));
        }
        onIncomeCreatedEventListener.onIncomeCreated(incomeRepository.save(incomeGenerator.apply(1001, "November")));
        {
            List<Expense> allCompleteExpenses = expenseRepository.findAllCompleteExpenses();
            Assert.assertThat(allCompleteExpenses.size(), is(6));
            Expense miete_oktober = expenseRepository.findOneByTitle("Miete Oktober");
            List<Transaction> allByTo = transactionRepository.findAllByTo(miete_oktober);
            Assert.assertThat(allByTo.size(), is(2));
            Assert.assertThat(allByTo.get(0).getValue(), is(749));
            Assert.assertThat(allByTo.get(1).getValue(), is(1));
        }


        incomeRepository.findAll().forEach(income -> {
            logger.info("----");
            transactionRepository.findAllByFrom(income).forEach(transaction -> {
                logger.info(income.getTitle() + "\t\t" + transaction.getValue() + "\t" + transaction.getTo().getTitle() + "(" + transaction.getTo().getAmount() + ")");
            });
        });

    }

}
