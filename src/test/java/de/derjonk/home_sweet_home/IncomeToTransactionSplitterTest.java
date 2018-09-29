package de.derjonk.home_sweet_home;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class IncomeToTransactionSplitterTest {

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
        List<Transaction> transactions = IncomeToTransactionSplitter
                .splitIncome(incomeGenerator.apply(150, "cash payment"))
                .toExpenses(
                        expenseGenerator.apply(100, "rent"),
                        expenseGenerator.apply(50, "heating and water")
                );

        Assert.assertThat(transactions.size(), is(2));
    }

    @Test
    public void createOneTransactionForOneIncomeForTwoExpensesWithInsufficientFunds() {
        List<Transaction> transactions = IncomeToTransactionSplitter
                .splitIncome(incomeGenerator.apply(100, "cash payment"))
                .toExpenses(
                        expenseGenerator.apply(100, "rent"),
                        expenseGenerator.apply(50, "heating and water")
                );

        Assert.assertThat(transactions.size(), is(1));
        Assert.assertThat(transactions.get(0).getTo().getTitle(), is("rent"));
    }

    @Test
    public void createTransactionsForExpensesThatAreBackedByIncome() {
        List<Transaction> transactions = IncomeToTransactionSplitter
                .splitIncome(incomeGenerator.apply(10, "cash"))
                .toExpenses(
                        expenseGenerator.apply(3, "one"),
                        expenseGenerator.apply(3, "two"),
                        expenseGenerator.apply(3, "three"),
                        expenseGenerator.apply(3, "toBeSplitted")
                );
        Assert.assertThat(transactions.size(), is(4));
        Assert.assertThat(transactions.get(2).getTo().getTitle(), is("three"));
        Assert.assertThat(transactions.get(3).getValue(), is(1));
        Assert.assertThat(transactions.get(3).getTo().getTitle(), is("toBeSplitted"));
    }

    @Test
    public void createTransactionsForExpensesThatHaveTransactions() {
        Expense splitted = expenseGenerator.apply(3, "toBeSplitted");
        List<Transaction> transactions = IncomeToTransactionSplitter
                .splitIncome(incomeGenerator.apply(10, "cash"))
                .toExpenses(
                        expenseGenerator.apply(3, "one"),
                        expenseGenerator.apply(3, "two"),
                        expenseGenerator.apply(3, "three"),
                        splitted
                );

        List<Transaction> splittedTransactions = transactions
                .stream()
                .filter(transaction -> transaction.getTo().equals(splitted))
                .collect(Collectors.toList());

        LinkedHashMap<Expense, List<Transaction>> map = new LinkedHashMap<>();
        map.put(splitted, splittedTransactions);
        List<Transaction> newSplittedTransactions = IncomeToTransactionSplitter.splitIncome(incomeGenerator.apply(2, "rest")).toExpenses(map);

        Assert.assertThat(newSplittedTransactions.size(), is(1));
        Assert.assertThat(newSplittedTransactions.get(0).getValue(), is(2));
        Assert.assertThat(newSplittedTransactions.get(0).getTo().getTitle(), is("toBeSplitted"));

    }


}
