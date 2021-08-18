package com.gitmuts.multiprofile;

import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.Role;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MultiProfileApplication {

	@Autowired
	UserRepo userRepo;
	@Autowired
	OrganizationRepo organizationRepo;
	@Autowired
	UserOrganisationRepo userOrganisationRepo;
	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(MultiProfileApplication.class, args);
	}

	@Bean
	public void createUserAndOrg() {

		Role roleAdmin = roleRepo.findByName("admin");
		if(roleAdmin == null){
			roleAdmin = new Role();
			roleAdmin.setName("admin");
			roleRepo.save(roleAdmin);
		}

		Role roleGuest = roleRepo.findByName("guest");
		if(roleGuest == null){
			roleGuest = new Role();
			roleGuest.setName("guest");
			roleRepo.save(roleGuest);
		}


		User user = userRepo.findByUsername("admin");
		if(user == null){
			user = new User();
			user.setName("admin");
			user.setActive(1);
			user.setUsername("admin");
			user.setPassword("$2a$10$Vc8ANYhsuL7BUThuBrVlIuZWNSWzftJbi6ULLuD7QZdMDJjPNOj8G");
			userRepo.save(user);
		}


		Organization organization1 = organizationRepo.findByName("Test1");
		Organization organization2 = organizationRepo.findByName("Test2");

		if(organization1 == null){
			organization1 = new Organization();
			organization1.setName("Test1");
			organizationRepo.save(organization1);
		}

		if(organization2 == null){
			organization2 = new Organization();
			organization2.setName("Test2");
			organizationRepo.save(organization2);
		}


		try {
			Set<Organization> organizationSet = new HashSet<>();
			organizationSet.add(organization1);
			organizationSet.add(organization2);

			UserOrganisation userOrganisationAdmin = new UserOrganisation();
			userOrganisationAdmin.setUser(user);
			userOrganisationAdmin.setOrganization(organization1);
			userOrganisationAdmin.setRole(roleAdmin);
			userOrganisationRepo.save(userOrganisationAdmin);

			UserOrganisation userOrganisationGuest = new UserOrganisation();
			userOrganisationGuest.setUser(user);
			userOrganisationGuest.setOrganization(organization2);
			userOrganisationGuest.setRole(roleGuest);
			userOrganisationRepo.save(userOrganisationGuest);
		}catch (DataIntegrityViolationException de){
			System.out.println("e");
		} catch (Exception e){
			System.err.println(e);
		}


		//user.setOrganizations(organizationSet);
		//userRepo.save(user);
	}
}
