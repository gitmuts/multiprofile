package com.gitmuts.multiprofile.user.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_registration")
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private com.gitmuts.multiprofile.user.entity.User User;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "used", nullable = false)
    private int used;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
