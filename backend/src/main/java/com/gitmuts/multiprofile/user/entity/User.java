package com.gitmuts.multiprofile.user.entity;

import com.gitmuts.multiprofile.user.repo.UserOrganisation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    Set<UserOrganisation> userOrganisations;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
    @Transient
    private long roleId;
    @Transient
    List<String> permissions = new ArrayList<>();
    @Transient
    Organization selectedOrganization;
    @Transient
    List<Organization> organizations;

    @Transient
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority( permission));
        });
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roleId=" + roleId +
                ", permissions=" + permissions +
                '}';
    }
}
