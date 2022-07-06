package com.gracefl.propfirm.web.rest;

import com.gracefl.propfirm.domain.TradersChallengeTracker;
import com.gracefl.propfirm.repository.TradersChallengeTrackerRepository;
import com.gracefl.propfirm.service.TradersChallengeTrackerQueryService;
import com.gracefl.propfirm.service.TradersChallengeTrackerService;
import com.gracefl.propfirm.service.criteria.TradersChallengeTrackerCriteria;
import com.gracefl.propfirm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gracefl.propfirm.domain.TradersChallengeTracker}.
 */
@RestController
@RequestMapping("/api")
public class TradersChallengeTrackerResource {

    private final Logger log = LoggerFactory.getLogger(TradersChallengeTrackerResource.class);

    private static final String ENTITY_NAME = "tradersChallengeTracker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradersChallengeTrackerService tradersChallengeTrackerService;

    private final TradersChallengeTrackerRepository tradersChallengeTrackerRepository;

    private final TradersChallengeTrackerQueryService tradersChallengeTrackerQueryService;

    public TradersChallengeTrackerResource(
        TradersChallengeTrackerService tradersChallengeTrackerService,
        TradersChallengeTrackerRepository tradersChallengeTrackerRepository,
        TradersChallengeTrackerQueryService tradersChallengeTrackerQueryService
    ) {
        this.tradersChallengeTrackerService = tradersChallengeTrackerService;
        this.tradersChallengeTrackerRepository = tradersChallengeTrackerRepository;
        this.tradersChallengeTrackerQueryService = tradersChallengeTrackerQueryService;
    }

    /**
     * {@code POST  /traders-challenge-trackers} : Create a new tradersChallengeTracker.
     *
     * @param tradersChallengeTracker the tradersChallengeTracker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradersChallengeTracker, or with status {@code 400 (Bad Request)} if the tradersChallengeTracker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/traders-challenge-trackers")
    public ResponseEntity<TradersChallengeTracker> createTradersChallengeTracker(
        @RequestBody TradersChallengeTracker tradersChallengeTracker
    ) throws URISyntaxException {
        log.debug("REST request to save TradersChallengeTracker : {}", tradersChallengeTracker);
        if (tradersChallengeTracker.getId() != null) {
            throw new BadRequestAlertException("A new tradersChallengeTracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradersChallengeTracker result = tradersChallengeTrackerService.save(tradersChallengeTracker);
        return ResponseEntity
            .created(new URI("/api/traders-challenge-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /traders-challenge-trackers/:id} : Updates an existing tradersChallengeTracker.
     *
     * @param id the id of the tradersChallengeTracker to save.
     * @param tradersChallengeTracker the tradersChallengeTracker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradersChallengeTracker,
     * or with status {@code 400 (Bad Request)} if the tradersChallengeTracker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradersChallengeTracker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/traders-challenge-trackers/{id}")
    public ResponseEntity<TradersChallengeTracker> updateTradersChallengeTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TradersChallengeTracker tradersChallengeTracker
    ) throws URISyntaxException {
        log.debug("REST request to update TradersChallengeTracker : {}, {}", id, tradersChallengeTracker);
        if (tradersChallengeTracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tradersChallengeTracker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradersChallengeTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TradersChallengeTracker result = tradersChallengeTrackerService.update(tradersChallengeTracker);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tradersChallengeTracker.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /traders-challenge-trackers/:id} : Partial updates given fields of an existing tradersChallengeTracker, field will ignore if it is null
     *
     * @param id the id of the tradersChallengeTracker to save.
     * @param tradersChallengeTracker the tradersChallengeTracker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradersChallengeTracker,
     * or with status {@code 400 (Bad Request)} if the tradersChallengeTracker is not valid,
     * or with status {@code 404 (Not Found)} if the tradersChallengeTracker is not found,
     * or with status {@code 500 (Internal Server Error)} if the tradersChallengeTracker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/traders-challenge-trackers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TradersChallengeTracker> partialUpdateTradersChallengeTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TradersChallengeTracker tradersChallengeTracker
    ) throws URISyntaxException {
        log.debug("REST request to partial update TradersChallengeTracker partially : {}, {}", id, tradersChallengeTracker);
        if (tradersChallengeTracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tradersChallengeTracker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradersChallengeTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TradersChallengeTracker> result = tradersChallengeTrackerService.partialUpdate(tradersChallengeTracker);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tradersChallengeTracker.getId().toString())
        );
    }

    /**
     * {@code GET  /traders-challenge-trackers} : get all the tradersChallengeTrackers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradersChallengeTrackers in body.
     */
    @GetMapping("/traders-challenge-trackers")
    public ResponseEntity<List<TradersChallengeTracker>> getAllTradersChallengeTrackers(
        TradersChallengeTrackerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TradersChallengeTrackers by criteria: {}", criteria);
        Page<TradersChallengeTracker> page = tradersChallengeTrackerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /traders-challenge-trackers/count} : count all the tradersChallengeTrackers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/traders-challenge-trackers/count")
    public ResponseEntity<Long> countTradersChallengeTrackers(TradersChallengeTrackerCriteria criteria) {
        log.debug("REST request to count TradersChallengeTrackers by criteria: {}", criteria);
        return ResponseEntity.ok().body(tradersChallengeTrackerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /traders-challenge-trackers/:id} : get the "id" tradersChallengeTracker.
     *
     * @param id the id of the tradersChallengeTracker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradersChallengeTracker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/traders-challenge-trackers/{id}")
    public ResponseEntity<TradersChallengeTracker> getTradersChallengeTracker(@PathVariable Long id) {
        log.debug("REST request to get TradersChallengeTracker : {}", id);
        Optional<TradersChallengeTracker> tradersChallengeTracker = tradersChallengeTrackerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradersChallengeTracker);
    }

    /**
     * {@code DELETE  /traders-challenge-trackers/:id} : delete the "id" tradersChallengeTracker.
     *
     * @param id the id of the tradersChallengeTracker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/traders-challenge-trackers/{id}")
    public ResponseEntity<Void> deleteTradersChallengeTracker(@PathVariable Long id) {
        log.debug("REST request to delete TradersChallengeTracker : {}", id);
        tradersChallengeTrackerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
