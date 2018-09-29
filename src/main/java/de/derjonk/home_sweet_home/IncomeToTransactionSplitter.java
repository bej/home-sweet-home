package de.derjonk.home_sweet_home;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
            final AtomicInteger funds = new AtomicInteger(this.income.getAmount());

            Predicate<Expense> expensesBackedByIncome = expense -> funds.addAndGet(-1 * expense.getAmount()) >= 0;
            return expenses
                    .stream()
                    .sequential()
                    .filter(expensesBackedByIncome)
                    .map(expense -> Transaction
                            .withValue(expense.getAmount())
                            .from(income)
                            .to(expense)
                            .end()
                    )
                    .collect(Collectors.toList());
        }
    }
}
