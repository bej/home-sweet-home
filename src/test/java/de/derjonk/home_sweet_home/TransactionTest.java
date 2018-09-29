package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TransactionTest {

    private static Account testAccount = Account.withName("MyAccount");

    private BiFunction<Integer, String, Expense> expenseGenerator = (amount, title) -> Expense
            .forAccount(testAccount)
            .withAmount(amount)
            .withTitle(title)
            .end();

    private BiFunction<Integer, String, Income> incomeGenerator = (amount, title) -> Income
            .forAccount(testAccount)
            .withAmount(amount)
            .withTitle(title)
            .end();

    @Test
    public void createTwoTransactionsForOneIncomeForTwoExpensesWithMatchingFunds() {
        List<Transaction> transactions = TransactionMagic
                .splitIncome(incomeGenerator.apply(150, "cash payment"))
                .toExpenses(
                        expenseGenerator.apply(100, "rent"),
                        expenseGenerator.apply(50, "heating and water")
                );

        Assert.assertThat(transactions.size(), is(2));
    }

    @Test
    public void createOneTransactionForOneIncomeForTwoExpensesWithInsufficientFunds() {
        List<Transaction> transactions = TransactionMagic
                .splitIncome(incomeGenerator.apply(100, "cash payment"))
                .toExpenses(
                        expenseGenerator.apply(100, "rent"),
                        expenseGenerator.apply(50, "heating and water")
                );

        Assert.assertThat(transactions.size(), is(1));
        Assert.assertThat(transactions.get(0).to.getTitle(), is("rent"));
    }

    public static class TransactionMagic {

        public static TransactionMagicBuilder splitIncome(Income income) {
            return new TransactionMagicBuilder(income);
        }

        public static class TransactionMagicBuilder {
            private final Income income;

            public TransactionMagicBuilder(Income income) {
                this.income = income;
            }

            public List<Transaction> toExpenses(Expense... expenses) {
                List<Transaction> transactions = Arrays.asList(expenses)
                        .stream()
                        .map(expense -> Transaction
                                .withValue(expense.getAmount())
                                .from(income)
                                .to(expense)
                                .end()
                        )
                        .collect(Collectors.toList());

                AtomicInteger funds = new AtomicInteger(this.income.getAmount());

                List<Transaction> backedTransactions = transactions
                        .stream()
                        .filter(transaction -> funds.addAndGet(-1 * transaction.getValue()) >= 0)
                        .collect(Collectors.toList());

                return backedTransactions;
            }
        }
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
