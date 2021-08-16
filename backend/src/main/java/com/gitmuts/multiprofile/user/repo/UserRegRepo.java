package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.model.UserRegistration;
import org.springframework.data.repository.CrudRepository;

public interface UserRegRepo extends CrudRepository<UserRegistration, Long> {

    UserRegistration findByToken(String token);
}
