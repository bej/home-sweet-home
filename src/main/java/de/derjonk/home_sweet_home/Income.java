package de.derjonk.home_sweet_home;


import javax.persistence.*;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Account account;

    public static Income forAccount(final Account account) {
        Income income = new Income();
        income.account = account;

        return income;
    }
}
