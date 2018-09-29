package de.derjonk.home_sweet_home;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
interface ExpenseRepository extends PagingAndSortingRepository<Expense, Integer> {

    List<Expense> findByAccountName(String accountName);

    @Query("SELECT e FROM Expense e WHERE " +
            // there don't exist any transactions for this expense
            "(SELECT t FROM Transaction t WHERE t.to = e) IS NULL " +
            "OR " +
            // sum of all transactions < expense amount
            "e.amount > (SELECT SUM(t.value) FROM Transaction t WHERE t.to = e)")
    List<Expense> findAllIncompleteExpenses();

    @Query("SELECT e FROM Expense e WHERE " +
            // sum of all transactions = expense amount
            "e.amount = (SELECT SUM(t.value) FROM Transaction t WHERE t.to = e)")
    List<Expense> findAllCompleteExpenses();
}
