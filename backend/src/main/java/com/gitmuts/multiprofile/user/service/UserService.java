package com.gitmuts.multiprofile.user.service;

import com.gitmuts.multiprofile.model.Constants;
import com.gitmuts.multiprofile.user.entity.Role;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.UserRegistration;
import com.gitmuts.multiprofile.user.repo.RoleRepo;
import com.gitmuts.multiprofile.user.repo.UserRegRepo;
import com.gitmuts.multiprofile.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRegRepo userRegRepo;

    @Autowired
    RoleRepo roleRepo;

    @Value("${app.url}")
    private String appUrl;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Querying DB");
        final User user = findByUsername(s);
        log.info("Got response from db");

        if (user == null || user.getId() == 0) {
            throw new UsernameNotFoundException("User Not found");
        }

        if (user.getPassword() == null) {
            user.setPassword("");
        }

        return user;
    }


    public User findByUsername(String username){

        return userRepo.findByUsername(username);
    }

    private List<SimpleGrantedAuthority> getAuthority() {

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        return authorities;
    }

    public String createUser(User user){
        try{

            Optional<Role> roleOptional = roleRepo.findById(user.getRoleId());

            if(!roleOptional.isPresent()){
                return String.format("Role with id %s not found", user.getRoleId());
            }

            user.setRole(roleOptional.get());

            User savedUser = userRepo.save(user);
            //create reg token
            String token = generateToken();

            if(token == null || token.isEmpty()){
                return "Failed to generate token";
            }

            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setToken(token);
            userRegistration.setUser(savedUser);
            userRegistration.setUsed(0);
            userRegRepo.save(userRegistration);


            //send email with reg token

           String emailSentStatus=  sendEmail(user, token);
           if(!emailSentStatus.equals(Constants.Response.SUCCESS.toString())){
               return  emailSentStatus;
           }

           return Constants.Response.SUCCESS.toString();
        }catch (Exception e){
            log.error("Error occurred while creating user {}", e.getMessage());
            return e.getMessage() != null ? e.getMessage() : "Error occurred while creating user";
        }
    }

    private String sendEmail(User user, String token){
        try{

            String link = generateLink(token);
            String message = String.format("Hello %s, \n" +
                    "Click on the link below to complete registration. \n" +
                    "%s", user.getName(), link);
            //emailService.sendSimpleMessage(user.getUsername(), "User Registration", message);
            return Constants.Response.SUCCESS.toString();
        }catch (Exception e){
            log.error("Error while sending email notification {}", e.getMessage());
            return  "Failed to send email notification";
        }
    }


    private String generateLink(String token){
        String link = String.format("%s/user_signup/%s", appUrl, token);
        return  link;
    }
    private String generateToken() {
        try{
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            return  uuid;
        }catch (Exception e){
            log.error("Error occurred while creating token {}", e.getMessage());
            return  null;
        }
    }

    public String updateUser(User user) {
        try{
            Optional<Role> roleOptional = roleRepo.findById(user.getRoleId());
            if(!roleOptional.isPresent()){
                return String.format("Role with id %s not found", user.getRoleId());
            }
            user.setRole(roleOptional.get());
            User savedUser = userRepo.save(user);
            return Constants.Response.SUCCESS.toString();
        }catch (Exception e){
            log.error("Error occurred while calling update user {}", e.getMessage());
            return e.getMessage() != null ? e.getMessage() : "Error occurred while updating user";
        }
    }
}
