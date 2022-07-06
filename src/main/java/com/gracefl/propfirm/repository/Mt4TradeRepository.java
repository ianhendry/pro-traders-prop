package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.Mt4Trade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mt4Trade entity.
 */
@Repository
public interface Mt4TradeRepository extends JpaRepository<Mt4Trade, Long>, JpaSpecificationExecutor<Mt4Trade> {
    default Optional<Mt4Trade> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Mt4Trade> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Mt4Trade> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct mt4Trade from Mt4Trade mt4Trade left join fetch mt4Trade.mt4Account",
        countQuery = "select count(distinct mt4Trade) from Mt4Trade mt4Trade"
    )
    Page<Mt4Trade> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct mt4Trade from Mt4Trade mt4Trade left join fetch mt4Trade.mt4Account")
    List<Mt4Trade> findAllWithToOneRelationships();

    @Query("select mt4Trade from Mt4Trade mt4Trade left join fetch mt4Trade.mt4Account where mt4Trade.id =:id")
    Optional<Mt4Trade> findOneWithToOneRelationships(@Param("id") Long id);
}
