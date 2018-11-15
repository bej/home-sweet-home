package de.derjonk.home_sweet_home.accounting;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = TransactionWithFromAndToProjection.class)
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    @RestResource(exported = false)
    List<Transaction> findAllByTo(Expense expense);

    @RestResource(exported = false)
    List<Transaction> findAllByFrom(Income income);

    List<Transaction> findAllByFromAccount(@Param("account") Account account);
}
