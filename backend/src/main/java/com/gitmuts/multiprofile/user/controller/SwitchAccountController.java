package com.gitmuts.multiprofile.user.controller;

import com.gitmuts.multiprofile.model.RestResponse;
import com.gitmuts.multiprofile.security.JwtTokenUtil;
import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.Permission;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.LoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api")
public class SwitchAccountController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/switch_account")
    public ResponseEntity<?> switchAccount(@RequestBody Organization organization, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            User user = (User) userDetails;

            log.info("User org list size {}", user.getOrganizations().size());

            List<Long> orgIds = user.getOrganizations().stream().map(Organization::getId).collect(Collectors.toList());


            if(orgIds.contains(organization.getId())){
                log.info("Organization is linked to user");
            } else {
                log.info("Org ids {} {}", orgIds ,organization.toString());
                return new ResponseEntity<>(new RestResponse(true, "User not linked to org"), HttpStatus.UNAUTHORIZED);
            }

            String token = jwtTokenUtil.generateToken(user, organization);
            List<String> permissions = user.getRole() != null ? user.getRole().getPermissions().stream().map(Permission::getAction).collect(Collectors.toList()): new ArrayList<>();
            user.setPermissions(permissions);
            user.setSelectedOrganization(organization);

            return new ResponseEntity(new LoginToken(user, token), HttpStatus.OK);
        } catch (Exception e){
            log.error("Error occurred while calling switch account ", e);
            return new ResponseEntity(new RestResponse(true, "Error occurred, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
