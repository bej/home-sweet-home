package de.derjonk.home_sweet_home.accounting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Integer> {

    List<Expense> findByAccountName(String accountName);

    Expense findOneByAccountAndTitle(Account account, String title);

    @Query("SELECT e FROM Expense e WHERE " +
            "e.account = :account " +
            "AND (" +
            // there don't exist any transactions for this expense
            "(SELECT t FROM Transaction t WHERE t.to = e) IS NULL " +
            "OR " +
            // sum of all transactions < expense amount
            "e.amount > (SELECT SUM(t.value) FROM Transaction t WHERE t.to = e)" +
            ")")
    List<Expense> findAllIncompleteExpensesByAccount(@Param("account") Account account);

    @Query("SELECT e FROM Expense e WHERE " +
            "e.account = :account " +
            "AND " +
            // sum of all transactions = expense amount
            "e.amount = (SELECT SUM(t.value) FROM Transaction t WHERE t.to = e)")
    List<Expense> findAllCompleteExpensesByAccount(@Param("account") Account account);
}
