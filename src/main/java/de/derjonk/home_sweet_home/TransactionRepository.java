package de.derjonk.home_sweet_home;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    List<Transaction> findAllByTo(Expense expense);
}
