package de.derjonk.home_sweet_home;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AccountingEntry {
    @ManyToOne
    private Account account;
    private Integer amount;
    private String title;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }
}
