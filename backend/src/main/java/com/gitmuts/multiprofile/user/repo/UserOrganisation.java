package com.gitmuts.multiprofile.user.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.Role;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.UserOrganisationKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_organisation")
@JsonIgnoreProperties({ "user", "organization" })
public class UserOrganisation {

    @EmbeddedId
    UserOrganisationKey id = new UserOrganisationKey();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name ="user_id")
    User user;

    @ManyToOne
    @MapsId("organisationId")
    @JoinColumn(name ="organisation_id")
    Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    Role role;

    public UserOrganisationKey getId() {
        return id;
    }

    public void setId(UserOrganisationKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
