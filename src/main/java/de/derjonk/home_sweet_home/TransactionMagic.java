package de.derjonk.home_sweet_home;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionMagic {

    public static TransactionMagicBuilder splitIncome(Income income) {
        return new TransactionMagicBuilder(income);
    }

    public static class TransactionMagicBuilder {
        private final Income income;

        public TransactionMagicBuilder(Income income) {
            this.income = income;
        }

        public List<Transaction> toExpenses(Expense... expenses) {
            final AtomicInteger funds = new AtomicInteger(this.income.getAmount());

            Predicate<Expense> expensesBackedByIncome = expense -> funds.addAndGet(-1 * expense.getAmount()) >= 0;
            return Arrays
                    .stream(expenses)
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
