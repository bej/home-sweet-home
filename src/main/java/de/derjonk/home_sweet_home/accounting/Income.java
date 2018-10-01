package de.derjonk.home_sweet_home.accounting;


import javax.persistence.*;

@Entity
public class Income extends AccountingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public static IncomeBuilder forAccount(final Account account) {
        return (IncomeBuilder) new IncomeBuilder().forAccount(account);
    }

    public static class IncomeBuilder extends AccountingEntryBuilder<Income> {

        protected IncomeBuilder() {
            super(new Income());
        }
    }
}
