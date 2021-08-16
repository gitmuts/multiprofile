package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
