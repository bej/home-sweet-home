package de.derjonk.home_sweet_home.accounting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface IncomeRepository extends PagingAndSortingRepository<Income, Integer> {
    List<Income> findByAccountName(String accountName);

    List<Income> findAllByAccount(@Param("account") Account account);

    @Query("SELECT i FROM Income i WHERE " +
            "i.account = :account " +
            "AND " +
            // there don't exist any transactions for this income
            "(NOT EXISTS (SELECT t FROM Transaction t WHERE t.from = i) " +
            "OR " +
            // sum of all transactions < income amount
            "i.amount > (SELECT SUM(t.value) FROM Transaction t WHERE t.from = i))")
    List<Income> findAllWithRemainingCreditByAccount(@Param("account") Account account);
}
