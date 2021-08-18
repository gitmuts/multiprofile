package com.gitmuts.multiprofile.user.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="role_permission",
            joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="permission_id", referencedColumnName="id")})
    private List<Permission> permissions;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
