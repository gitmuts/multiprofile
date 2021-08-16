package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.entity.Permission;
import org.springframework.data.repository.CrudRepository;


public interface PermissionRepo extends CrudRepository<Permission, Long> {
}
