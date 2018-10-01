package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeSweetHomeApplication.class)
public class IncomeRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @After
    public void cleanup() {
        transactionRepository.deleteAll();
        expenseRepository.deleteAll();
        incomeRepository.deleteAll();
        accountRepository.deleteAll();
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
}
