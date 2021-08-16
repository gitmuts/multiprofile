package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.UserSession;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepo extends CrudRepository<UserSession, Long> {

    UserSession findByUser(User admin);
}
