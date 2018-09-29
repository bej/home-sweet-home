package de.derjonk.home_sweet_home;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Account() {

    }

    public static Account withName(String name) {
        Account account = new Account();
        account.name = name;
        return account;
    }

    public String getName() {
        return name;
    }
}
