package de.derjonk.home_sweet_home;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface ExpenseRepository extends PagingAndSortingRepository<Expense, Integer> {

}
