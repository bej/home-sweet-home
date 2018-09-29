package de.derjonk.home_sweet_home;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
interface IncomeRepository extends PagingAndSortingRepository<Income, Integer> {
    List<Income> findByAccountName(String accountName);

    @Query("SELECT i FROM Income i WHERE " +
            // there don't exist any transactions for this income
            "(SELECT t FROM Transaction t WHERE t.from = i) IS NULL " +
            "OR " +
            // sum of all transactions < income amount
            "i.amount > (SELECT SUM(t.value) FROM Transaction t WHERE t.from = i)")
    List<Income> findAllWithRemainingCredit();
}
