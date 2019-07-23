package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiMethodResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ApiMethodResource entity.
 */
@Repository
public interface ApiMethodResourceRepository extends JpaRepository<ApiMethodResource, Long> {

    @Query(value = "select distinct apiMethodResource from ApiMethodResource apiMethodResource left join fetch apiMethodResource.methods",
        countQuery = "select count(distinct apiMethodResource) from ApiMethodResource apiMethodResource")
    Page<ApiMethodResource> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apiMethodResource from ApiMethodResource apiMethodResource left join fetch apiMethodResource.methods")
    List<ApiMethodResource> findAllWithEagerRelationships();

    @Query("select apiMethodResource from ApiMethodResource apiMethodResource left join fetch apiMethodResource.methods where apiMethodResource.id =:id")
    Optional<ApiMethodResource> findOneWithEagerRelationships(@Param("id") Long id);

}
