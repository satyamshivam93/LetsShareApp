package com.app.share.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.app.share.domain.enumeration.MyPostReponseStatus;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "my_post_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyPostResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "my_post_response")
    private String myPostResponse;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "responder_id")
    private Integer responderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "my_post_reponse_status")
    private MyPostReponseStatus myPostReponseStatus;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties(value = "myPostResponses", allowSetters = true)
    private MyPost myPost;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyPostResponse() {
        return myPostResponse;
    }

    public MyPostResponse myPostResponse(String myPostResponse) {
        this.myPostResponse = myPostResponse;
        return this;
    }

    public void setMyPostResponse(String myPostResponse) {
        this.myPostResponse = myPostResponse;
    }

    public Integer getPostId() {
        return postId;
    }

    public MyPostResponse postId(Integer postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getResponderId() {
        return responderId;
    }

    public MyPostResponse responderId(Integer responderId) {
        this.responderId = responderId;
        return this;
    }

    public void setResponderId(Integer responderId) {
        this.responderId = responderId;
    }

    public MyPostReponseStatus getMyPostReponseStatus() {
        return myPostReponseStatus;
    }

    public MyPostResponse myPostReponseStatus(MyPostReponseStatus myPostReponseStatus) {
        this.myPostReponseStatus = myPostReponseStatus;
        return this;
    }

    public void setMyPostReponseStatus(MyPostReponseStatus myPostReponseStatus) {
        this.myPostReponseStatus = myPostReponseStatus;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public MyPostResponse createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public MyPostResponse createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public MyPostResponse modifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public MyPostResponse modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getComments() {
        return comments;
    }

    public MyPostResponse comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isActive() {
        return active;
    }

    public MyPostResponse active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MyPost getMyPost() {
        return myPost;
    }

    public MyPostResponse myPost(MyPost myPost) {
        this.myPost = myPost;
        return this;
    }

    public void setMyPost(MyPost myPost) {
        this.myPost = myPost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyPostResponse)) {
            return false;
        }
        return id != null && id.equals(((MyPostResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyPostResponse{" +
            "id=" + getId() +
            ", myPostResponse='" + getMyPostResponse() + "'" +
            ", postId=" + getPostId() +
            ", responderId=" + getResponderId() +
            ", myPostReponseStatus='" + getMyPostReponseStatus() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
