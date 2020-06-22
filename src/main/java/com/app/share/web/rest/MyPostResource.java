package com.app.share.web.rest;

import com.app.share.domain.MyPost;
import com.app.share.service.MyPostService;
import com.app.share.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.share.domain.MyPost}.
 */
@RestController
@RequestMapping("/api")
public class MyPostResource {

    private final Logger log = LoggerFactory.getLogger(MyPostResource.class);

    private static final String ENTITY_NAME = "myPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyPostService myPostService;

    public MyPostResource(MyPostService myPostService) {
        this.myPostService = myPostService;
    }

    /**
     * {@code POST  /my-posts} : Create a new myPost.
     *
     * @param myPost the myPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myPost, or with status {@code 400 (Bad Request)} if the myPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-posts")
    public ResponseEntity<MyPost> createMyPost(@RequestBody MyPost myPost) throws URISyntaxException {
        log.debug("REST request to save MyPost : {}", myPost);
        if (myPost.getId() != null) {
            throw new BadRequestAlertException("A new myPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyPost result = myPostService.save(myPost);
        return ResponseEntity.created(new URI("/api/my-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-posts} : Updates an existing myPost.
     *
     * @param myPost the myPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myPost,
     * or with status {@code 400 (Bad Request)} if the myPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-posts")
    public ResponseEntity<MyPost> updateMyPost(@RequestBody MyPost myPost) throws URISyntaxException {
        log.debug("REST request to update MyPost : {}", myPost);
        if (myPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyPost result = myPostService.save(myPost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /my-posts} : get all the myPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myPosts in body.
     */
    @GetMapping("/my-posts")
    public ResponseEntity<List<MyPost>> getAllMyPosts(Pageable pageable) {
        log.debug("REST request to get a page of MyPosts");
        Page<MyPost> page = myPostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /my-posts/:id} : get the "id" myPost.
     *
     * @param id the id of the myPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-posts/{id}")
    public ResponseEntity<MyPost> getMyPost(@PathVariable Long id) {
        log.debug("REST request to get MyPost : {}", id);
        Optional<MyPost> myPost = myPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myPost);
    }

    /**
     * {@code DELETE  /my-posts/:id} : delete the "id" myPost.
     *
     * @param id the id of the myPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-posts/{id}")
    public ResponseEntity<Void> deleteMyPost(@PathVariable Long id) {
        log.debug("REST request to delete MyPost : {}", id);
        myPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
