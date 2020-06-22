package com.app.share.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.app.share.domain.enumeration.UserType;

import com.app.share.domain.enumeration.ItemType;

import com.app.share.domain.enumeration.MyPostStatus;

/**
 * A MyPost.
 */
@Entity
@Table(name = "my_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "my_post_status")
    private MyPostStatus myPostStatus;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "myPost")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MyPostResponse> myPostResponses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public MyPost userType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public MyPost itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public MyPost description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public MyPost createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public MyPost createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public MyPost modifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public MyPost modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public MyPostStatus getMyPostStatus() {
        return myPostStatus;
    }

    public MyPost myPostStatus(MyPostStatus myPostStatus) {
        this.myPostStatus = myPostStatus;
        return this;
    }

    public void setMyPostStatus(MyPostStatus myPostStatus) {
        this.myPostStatus = myPostStatus;
    }

    public Boolean isActive() {
        return active;
    }

    public MyPost active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<MyPostResponse> getMyPostResponses() {
        return myPostResponses;
    }

    public MyPost myPostResponses(Set<MyPostResponse> myPostResponses) {
        this.myPostResponses = myPostResponses;
        return this;
    }

    public MyPost addMyPostResponse(MyPostResponse myPostResponse) {
        this.myPostResponses.add(myPostResponse);
        myPostResponse.setMyPost(this);
        return this;
    }

    public MyPost removeMyPostResponse(MyPostResponse myPostResponse) {
        this.myPostResponses.remove(myPostResponse);
        myPostResponse.setMyPost(null);
        return this;
    }

    public void setMyPostResponses(Set<MyPostResponse> myPostResponses) {
        this.myPostResponses = myPostResponses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyPost)) {
            return false;
        }
        return id != null && id.equals(((MyPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyPost{" +
            "id=" + getId() +
            ", userType='" + getUserType() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", myPostStatus='" + getMyPostStatus() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
