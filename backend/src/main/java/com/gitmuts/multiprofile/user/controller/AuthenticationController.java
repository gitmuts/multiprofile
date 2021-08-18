package com.gitmuts.multiprofile.user.controller;

import com.gitmuts.multiprofile.model.*;
import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.Permission;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.repo.UserOrganisation;
import com.gitmuts.multiprofile.user.repo.UserRepo;
import com.gitmuts.multiprofile.user.repo.UserSessionRepo;
import com.gitmuts.multiprofile.security.JwtTokenUtil;
import com.gitmuts.multiprofile.user.service.UserService;
import com.gitmuts.multiprofile.user.model.Login;
import com.gitmuts.multiprofile.user.model.LoginToken;
import com.gitmuts.multiprofile.user.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/token")
@Slf4j
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSessionRepo userSessionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<?> generateToken(@RequestBody Login login) {
        try {

            User user = userRepo.findByUsername(login.getUsername());

            if (user == null) {
                log.info("User {} not found", login.getUsername());

                return new ResponseEntity<>(new RestResponse(true, "User not Found."), HttpStatus.UNAUTHORIZED);
            }

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserSession userSession = userSessionRepo.findByUser(user);

            if(userSession ==  null){
                userSession = new UserSession();
                userSession.setUser(user);
                userSession.setLoggedIn(0);
                userSessionRepo.save(userSession);
            }

            List<Organization> organizations = new ArrayList<>(user.getUserOrganisations().stream().map(UserOrganisation::getOrganization).collect(Collectors.toList()));
            user.setSelectedOrganization(organizations.get(0));
            String token = jwtTokenUtil.generateToken(user, organizations.get(0));
            List<String> permissions = user.getRole() != null ? user.getRole().getPermissions().stream().map(Permission::getAction).collect(Collectors.toList()): new ArrayList<>();
            user.setPermissions(permissions);
            userSession.setLoggedIn(1);
            userSessionRepo.save(userSession);

            UserOrganisation userOrganisation = user.getUserOrganisations().stream().filter(userOrg -> userOrg.getOrganization().equals(organizations.get(0))).findFirst().get();

            user.setRole(userOrganisation.getRole());
            user.setOrganizations(organizations);

            return new ResponseEntity(new LoginToken(user, token), HttpStatus.OK);
        } catch (AuthenticationException authe){
            log.error("Authentication error for  {} Ex: {}", login.getUsername(), authe.getMessage());
            return new ResponseEntity<>(new RestResponse(true, "Wrong username/Password."), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            log.error("Error occurred while calling generateToken ", e);
            return new ResponseEntity(new RestResponse(true, "Error occurred, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout/{token}")
    public ResponseEntity<?> logout(@PathVariable("token") String token) {
        try{

            if(token == null || token.equals("")){
                return new ResponseEntity<>(new RestResponse(true, "Failed to logout, token is empty"), HttpStatus.OK);
            }

            String username = jwtTokenUtil.getUsernameUnlimitedSkew(token);
            User user = userService.findByUsername(username);
            if(log.isDebugEnabled()){
                log.debug("Received a request to log out {}", username);
            }
            UserSession userSession = userSessionRepo.findByUser(user);

            if(userSession == null){
                return new ResponseEntity<>(new RestResponse(false, "User session not found"), HttpStatus.OK);
            }

            userSession.setLoggedIn(0);
            userSessionRepo.save(userSession);
            return new ResponseEntity<>(new RestResponse(false, "User logged out"), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error occurred while calling {} for {} Ex: {}", "logout", token, e);
            return new ResponseEntity<>(new RestResponse(true, "Failed to Logout, Try Later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
