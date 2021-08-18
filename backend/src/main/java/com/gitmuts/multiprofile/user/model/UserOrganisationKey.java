package com.gitmuts.multiprofile.user.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserOrganisationKey implements Serializable {

    @Column(name ="user_id")
    long userId;

    @Column(name ="organisation_id")
    long organisationId;

    public UserOrganisationKey() {
    }

    public UserOrganisationKey(long userId, long organisationId) {
        this.userId = userId;
        this.organisationId = organisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrganisationKey that = (UserOrganisationKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(organisationId, that.organisationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, organisationId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }
}


