package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
}
