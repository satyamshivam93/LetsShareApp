package com.app.share.repository;

import com.app.share.domain.MyPostResponse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyPostResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyPostResponseRepository extends JpaRepository<MyPostResponse, Long> {
}
