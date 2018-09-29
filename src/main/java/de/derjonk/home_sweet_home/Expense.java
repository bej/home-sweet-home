package de.derjonk.home_sweet_home;


import javax.persistence.*;

@Entity
public class Expense extends AccountingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public static ExpenseBuilder forAccount(final Account account) {
        return (ExpenseBuilder) new ExpenseBuilder().forAccount(account);
    }

    public static class ExpenseBuilder extends AccountingEntryBuilder<Expense> {

        protected ExpenseBuilder() {
            super(new Expense());
        }
    }

}
