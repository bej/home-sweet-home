package de.derjonk.home_sweet_home;

import de.derjonk.home_sweet_home.accounting.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

public class OnIncomeCreatedEventListener {

    private final ExpenseRepository expenseRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OnIncomeCreatedEventListener(ExpenseRepository expenseRepository, TransactionRepository transactionRepository) {
        this.expenseRepository = expenseRepository;
        this.transactionRepository = transactionRepository;
    }

    public void onIncomeCreated(Income income) {
        List<Expense> allIncompleteExpenses = expenseRepository.findAllIncompleteExpenses();
        LinkedHashMap<Expense, List<Transaction>> expenses = new LinkedHashMap<>();

        allIncompleteExpenses.forEach(expense -> expenses.put(expense, transactionRepository.findAllByTo(expense)));

        List<Transaction> transactions = IncomeToTransactionSplitter.splitIncome(income).toExpenses(expenses);
        transactionRepository.saveAll(transactions);
    }
}
