package github.fast.xauth.repository;

import github.fast.xauth.domain.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, JpaSpecificationExecutor<Site> {

    @Query(value = "select distinct site from Site site left join fetch site.roles left join fetch site.templates",
        countQuery = "select count(distinct site) from Site site")
    Page<Site> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct site from Site site left join fetch site.roles left join fetch site.templates")
    List<Site> findAllWithEagerRelationships();

    @Query("select site from Site site left join fetch site.roles left join fetch site.templates where site.id =:id")
    Optional<Site> findOneWithEagerRelationships(@Param("id") Long id);

}
