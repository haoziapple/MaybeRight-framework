package github.fast.xauth.repository;

import github.fast.xauth.domain.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Workspace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, JpaSpecificationExecutor<Workspace> {

    @Query(value = "select distinct workspace from Workspace workspace left join fetch workspace.sites left join fetch workspace.templates",
        countQuery = "select count(distinct workspace) from Workspace workspace")
    Page<Workspace> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct workspace from Workspace workspace left join fetch workspace.sites left join fetch workspace.templates")
    List<Workspace> findAllWithEagerRelationships();

    @Query("select workspace from Workspace workspace left join fetch workspace.sites left join fetch workspace.templates where workspace.id =:id")
    Optional<Workspace> findOneWithEagerRelationships(@Param("id") Long id);

}
