package de.derjonk.home_sweet_home;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
interface IncomeRepository extends PagingAndSortingRepository<Income, Integer> {
    List<Income> findByAccountName(String accountName);
}
