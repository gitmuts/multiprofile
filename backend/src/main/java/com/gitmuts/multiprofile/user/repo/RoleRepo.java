package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
}
