package de.derjonk.home_sweet_home.accounting;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

    Account findByNameIgnoreCase(@Param("name") String name);
    Account findByName(@Param("name") String name);

}
