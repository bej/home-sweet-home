package de.derjonk.home_sweet_home;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

    Account findByNameIgnoreCase(@Param("name") String name);
    Account findByName(@Param("name") String name);

}
