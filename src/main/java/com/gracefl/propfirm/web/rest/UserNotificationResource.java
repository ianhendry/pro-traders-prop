package com.gracefl.propfirm.web.rest;

import com.gracefl.propfirm.domain.UserNotification;
import com.gracefl.propfirm.repository.UserNotificationRepository;
import com.gracefl.propfirm.service.UserNotificationQueryService;
import com.gracefl.propfirm.service.UserNotificationService;
import com.gracefl.propfirm.service.criteria.UserNotificationCriteria;
import com.gracefl.propfirm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.gracefl.propfirm.domain.UserNotification}.
 */
@RestController
@RequestMapping("/api")
public class UserNotificationResource {

    private final Logger log = LoggerFactory.getLogger(UserNotificationResource.class);

    private static final String ENTITY_NAME = "userNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserNotificationService userNotificationService;

    private final UserNotificationRepository userNotificationRepository;

    private final UserNotificationQueryService userNotificationQueryService;

    public UserNotificationResource(
        UserNotificationService userNotificationService,
        UserNotificationRepository userNotificationRepository,
        UserNotificationQueryService userNotificationQueryService
    ) {
        this.userNotificationService = userNotificationService;
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationQueryService = userNotificationQueryService;
    }

    /**
     * {@code POST  /user-notifications} : Create a new userNotification.
     *
     * @param userNotification the userNotification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userNotification, or with status {@code 400 (Bad Request)} if the userNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-notifications")
    public ResponseEntity<UserNotification> createUserNotification(@Valid @RequestBody UserNotification userNotification)
        throws URISyntaxException {
        log.debug("REST request to save UserNotification : {}", userNotification);
        if (userNotification.getId() != null) {
            throw new BadRequestAlertException("A new userNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserNotification result = userNotificationService.save(userNotification);
        return ResponseEntity
            .created(new URI("/api/user-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-notifications/:id} : Updates an existing userNotification.
     *
     * @param id the id of the userNotification to save.
     * @param userNotification the userNotification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userNotification,
     * or with status {@code 400 (Bad Request)} if the userNotification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userNotification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-notifications/{id}")
    public ResponseEntity<UserNotification> updateUserNotification(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserNotification userNotification
    ) throws URISyntaxException {
        log.debug("REST request to update UserNotification : {}, {}", id, userNotification);
        if (userNotification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userNotification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserNotification result = userNotificationService.update(userNotification);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userNotification.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-notifications/:id} : Partial updates given fields of an existing userNotification, field will ignore if it is null
     *
     * @param id the id of the userNotification to save.
     * @param userNotification the userNotification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userNotification,
     * or with status {@code 400 (Bad Request)} if the userNotification is not valid,
     * or with status {@code 404 (Not Found)} if the userNotification is not found,
     * or with status {@code 500 (Internal Server Error)} if the userNotification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-notifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserNotification> partialUpdateUserNotification(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserNotification userNotification
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserNotification partially : {}, {}", id, userNotification);
        if (userNotification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userNotification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserNotification> result = userNotificationService.partialUpdate(userNotification);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userNotification.getId().toString())
        );
    }

    /**
     * {@code GET  /user-notifications} : get all the userNotifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userNotifications in body.
     */
    @GetMapping("/user-notifications")
    public ResponseEntity<List<UserNotification>> getAllUserNotifications(
        UserNotificationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserNotifications by criteria: {}", criteria);
        Page<UserNotification> page = userNotificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-notifications/count} : count all the userNotifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-notifications/count")
    public ResponseEntity<Long> countUserNotifications(UserNotificationCriteria criteria) {
        log.debug("REST request to count UserNotifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(userNotificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-notifications/:id} : get the "id" userNotification.
     *
     * @param id the id of the userNotification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userNotification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-notifications/{id}")
    public ResponseEntity<UserNotification> getUserNotification(@PathVariable Long id) {
        log.debug("REST request to get UserNotification : {}", id);
        Optional<UserNotification> userNotification = userNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userNotification);
    }

    /**
     * {@code DELETE  /user-notifications/:id} : delete the "id" userNotification.
     *
     * @param id the id of the userNotification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-notifications/{id}")
    public ResponseEntity<Void> deleteUserNotification(@PathVariable Long id) {
        log.debug("REST request to delete UserNotification : {}", id);
        userNotificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
