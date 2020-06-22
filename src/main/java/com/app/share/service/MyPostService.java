package com.app.share.service;

import com.app.share.domain.MyPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MyPost}.
 */
public interface MyPostService {

    /**
     * Save a myPost.
     *
     * @param myPost the entity to save.
     * @return the persisted entity.
     */
    MyPost save(MyPost myPost);

    /**
     * Get all the myPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MyPost> findAll(Pageable pageable);


    /**
     * Get the "id" myPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyPost> findOne(Long id);

    /**
     * Delete the "id" myPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
