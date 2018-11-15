package de.derjonk.home_sweet_home.accounting;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "t_expense")
public class Expense extends AccountingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Formula(value = "SELECT SUM(t.t_value) FROM t_transaction t WHERE t.expense_to_FK = id")
    private Integer sumOfTransactions;

    @JsonProperty("complete")
    public Boolean isComplete() {
        return this.getAmount().compareTo(sumOfTransactions) == 0;
    }

    @JsonProperty("balance")
    public Integer getBalance() {
        return this.sumOfTransactions - this.getAmount();
    }

    public static ExpenseBuilder forAccount(final Account account) {
        return (ExpenseBuilder) new ExpenseBuilder().forAccount(account);
    }

    public static class ExpenseBuilder extends AccountingEntryBuilder<Expense> {

        protected ExpenseBuilder() {
            super(new Expense());
        }
    }
}
