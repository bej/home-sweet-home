package de.derjonk.home_sweet_home;


import javax.persistence.*;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Account account;

    public static Expense forAccount(final Account account) {
        Expense expense = new Expense();
        expense.account = account;

        return expense;
    }

}
