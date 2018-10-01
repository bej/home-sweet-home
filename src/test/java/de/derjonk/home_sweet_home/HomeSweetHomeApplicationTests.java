package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.Account;
import de.derjonk.home_sweet_home.accounting.AccountRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeSweetHomeApplication.class)
public class HomeSweetHomeApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void cleanup() {
        accountRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void findAccountByName() {
        accountRepository.save(Account.withName("TestAccount"));
        Assert.assertThat(accountRepository.findByNameIgnoreCase("testaccount").getName(), is("TestAccount"));
    }

}
