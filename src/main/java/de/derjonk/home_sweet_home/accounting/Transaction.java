package de.derjonk.home_sweet_home.accounting;

import javax.persistence.*;

@Entity
@Table(name = "t_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "expense_to_FK")
    private Expense to;
    @ManyToOne
    private Income from;

    @Column(name = "t_value")
    private Integer value;


    public static TransactionBuilder withValue(Integer value) {
        return new TransactionBuilder(value);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setFrom(Income from) {
        this.from = from;
    }

    public void setTo(Expense to) {
        this.to = to;
    }

    public Expense getTo() {
        return to;
    }

    public Income getFrom() {
        return from;
    }

    public Integer getValue() {
        return value;
    }

    public static class TransactionBuilder {
        private Transaction transaction;

        public TransactionBuilder(Integer value) {
            this.transaction = new Transaction();
            transaction.setValue(value);
        }

        public TransactionBuilder from(Income from) {
            this.transaction.setFrom(from);
            return this;
        }

        public TransactionBuilder to(Expense to) {
            this.transaction.setTo(to);
            return this;
        }

        public Transaction end() {
            return this.transaction;
        }
    }
}
