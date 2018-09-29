package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TransactionTest {

    @Test
    public void createTwoTransactionsForOneIncomeForTwoExpenses() {
        Account testAccount = Account.withName("MyAccount");
        Income cashPayment = Income.forAccount(testAccount)
                .withAmount(150)
                .withTitle("Cash payment")
                .end();
        Expense rent = Expense.forAccount(testAccount)
                .withAmount(100)
                .withTitle("rent")
                .end();
        Expense heatingAndWater = Expense.forAccount(testAccount)
                .withAmount(50)
                .withTitle("heating and water")
                .end();


        List<Transaction> transactions = Arrays.asList(rent, heatingAndWater)
                .stream()
                .map(expense -> Transaction
                        .withValue(expense.getAmount())
                        .from(cashPayment)
                        .to(expense)
                        .end()
                )
                .collect(Collectors.toList());

        Assert.assertThat(transactions.size(), is(2));
    }

    public static class Transaction {
        private Expense to;
        private Income from;
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
}
