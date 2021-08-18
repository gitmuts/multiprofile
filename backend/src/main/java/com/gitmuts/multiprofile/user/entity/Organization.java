package com.gitmuts.multiprofile.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gitmuts.multiprofile.user.repo.UserOrganisation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "organization")
@JsonIgnoreProperties({ "users" })
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
    Set<UserOrganisation> userOrganisations;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
