package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.LinkedHashMap;
import java.util.List;

@RepositoryEventHandler(Income.class)
public class OnIncomeCreatedEventListener {

    private final ExpenseRepository expenseRepository;
    private final TransactionRepository transactionRepository;

    private Logger logger = LoggerFactory.getLogger(OnIncomeCreatedEventListener.class);

    @Autowired
    public OnIncomeCreatedEventListener(ExpenseRepository expenseRepository, TransactionRepository transactionRepository) {
        this.expenseRepository = expenseRepository;
        this.transactionRepository = transactionRepository;
    }

    @HandleAfterCreate
    public void onIncomeCreated(Income income) {
        logger.debug("onIncomeCreated " + income.getTitle());
        final LinkedHashMap<Expense, List<Transaction>> expenses = expenseRepository
                .findAllIncompleteExpensesByAccount(income.getAccount())
                .stream()
                .collect(
                        LinkedHashMap::new,
                        (linkedHashMap, expense) -> linkedHashMap.put(expense, transactionRepository.findAllByTo(expense)),
                        LinkedHashMap::putAll
                );

        final List<Transaction> transactions = IncomeToTransactionSplitter
                .splitIncome(income)
                .toExpenses(expenses);

        transactionRepository.saveAll(transactions);
    }
}
