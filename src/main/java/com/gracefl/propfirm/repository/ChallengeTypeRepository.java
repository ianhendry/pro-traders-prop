package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.ChallengeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChallengeType entity.
 */
@Repository
public interface ChallengeTypeRepository extends JpaRepository<ChallengeType, Long> {
    default Optional<ChallengeType> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ChallengeType> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ChallengeType> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct challengeType from ChallengeType challengeType left join fetch challengeType.siteAccount",
        countQuery = "select count(distinct challengeType) from ChallengeType challengeType"
    )
    Page<ChallengeType> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct challengeType from ChallengeType challengeType left join fetch challengeType.siteAccount")
    List<ChallengeType> findAllWithToOneRelationships();

    @Query("select challengeType from ChallengeType challengeType left join fetch challengeType.siteAccount where challengeType.id =:id")
    Optional<ChallengeType> findOneWithToOneRelationships(@Param("id") Long id);
}
