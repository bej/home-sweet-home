package de.derjonk.home_sweet_home;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IncomeToTransactionSplitter {

    public static IncomeToTransactionSplitterBuilder splitIncome(Income income) {
        return new IncomeToTransactionSplitterBuilder(income);
    }

    public static class IncomeToTransactionSplitterBuilder {
        private final Income income;

        public IncomeToTransactionSplitterBuilder(Income income) {
            this.income = income;
        }

        public List<Transaction> toExpenses(Expense... expenses) {
            return toExpenses(Arrays.asList(expenses));
        }

        public List<Transaction> toExpenses(List<Expense> expenses) {
            LinkedHashMap<Expense, List<Transaction>> map = new LinkedHashMap<>();
            expenses.forEach(expense -> map.put(expense, Collections.emptyList()));
            return toExpenses(map);
        }

        public List<Transaction> toExpenses(LinkedHashMap<Expense, List<Transaction>> expenses) {
            final AtomicInteger funds = new AtomicInteger(this.income.getAmount());

            Function<Expense, Integer> sumOfTransactions = expense -> expenses.get(expense)
                    .stream()
                    .map(Transaction::getValue)
                    .reduce((integer, integer2) -> integer + integer2)
                    .orElse(0);

            Predicate<Expense> expenseWithinFunds = e -> funds.addAndGet(-1 * (e.getAmount() - sumOfTransactions.apply(e))) >= 0;

            Map<Boolean, List<Expense>> partitions = expenses
                    .keySet()
                    .stream()
                    .sequential()
                    .collect(Collectors.partitioningBy(expenseWithinFunds));

            List<Transaction> transactions = new ArrayList<>();

            {
                // transactions for completely paid expenses
                transactions.addAll(partitions
                        .get(true)
                        .stream()
                        .map(expense -> Transaction
                                .withValue(expense.getAmount() - sumOfTransactions.apply(expense))
                                .from(income)
                                .to(expense)
                                .end()
                        )
                        .collect(Collectors.toList())
                );
            }

            {
                // if there is any credit left from the income -> create a transaction with the amount that is left over
                // the expense will not be paid completely by this transaction
                int delta = income.getAmount() - transactions
                        .stream()
                        .map(Transaction::getValue)
                        .reduce((value, value2) -> value + value2)
                        .orElse(0);
                if (delta > 0 && !partitions.get(false).isEmpty()) {
                    transactions.add(Transaction
                            .withValue(delta)
                            .from(income)
                            .to(partitions.get(false).get(0))
                            .end()
                    );
                }
            }

            return transactions;
        }
    }
}
