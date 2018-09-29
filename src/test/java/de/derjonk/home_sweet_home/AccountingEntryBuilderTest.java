package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class AccountingEntryBuilderTest {

    @Test
    public void buildExpense() {
        Expense expense = Expense.forAccount(Account.withName("TestAccount"))
                .withTitle("TestExpense")
                .withAmount(100)
                .end();
        Assert.assertThat(expense.getAmount(), is(100));
        Assert.assertThat(expense.getTitle(), is("TestExpense"));
    }

    @Test
    public void buildIncome() {
        Income income = Income.forAccount(Account.withName("TestAccount"))
                .withTitle("TestIncome")
                .withAmount(123)
                .end();
        Assert.assertThat(income.getAmount(), is(123));
        Assert.assertThat(income.getTitle(), is("TestIncome"));
    }
}
