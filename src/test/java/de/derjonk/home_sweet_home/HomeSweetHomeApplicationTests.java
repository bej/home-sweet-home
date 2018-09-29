package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeSweetHomeApplicationTests {

	@Autowired private ExpenseRepository expenseRepository;
	@Autowired private IncomeRepository incomeRepository;
	@Autowired private AccountRepository accountRepository;

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
	    expenseRepository.save(Expense.forAccount(account));
	    Assert.assertThat(expenseRepository.findByAccountName("ExpenseAccount").size(), is(1));
    }

}
