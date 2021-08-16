package com.gitmuts.multiprofile.user.repo;

import com.gitmuts.multiprofile.user.entity.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepo extends CrudRepository<Organization, Long> {

    Organization findByName(String name);
   // Organization findById(Long id);
}
