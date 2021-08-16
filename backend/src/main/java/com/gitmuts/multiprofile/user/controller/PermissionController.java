package com.gitmuts.multiprofile.user.controller;

import com.gitmuts.multiprofile.model.RestResponse;
import com.gitmuts.multiprofile.user.entity.Permission;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.repo.PermissionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
@Slf4j
public class PermissionController {

    @Autowired
    PermissionRepo permissionRepo;

    @GetMapping()
    public ResponseEntity<?> getPermissions(@AuthenticationPrincipal UserDetails userDetails) {
        try{
            User user = (User) userDetails;

            log.info("Received a request from {}", user.getSelectedOrganization());
            return new ResponseEntity(permissionRepo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, "Error occurred, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createPermission(@RequestBody Permission permission) {
        try{

            Permission savedPermission = permissionRepo.save(permission);
            if(savedPermission != null){
                return new ResponseEntity(new RestResponse(false, "Created successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new RestResponse(true, "Error occurred while creating permission"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updatePermission(@RequestBody Permission permission) {
        try{

            Permission savedPermission = permissionRepo.save(permission);
            if(savedPermission != null){
                return new ResponseEntity(new RestResponse(false, "Permission Updated successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new RestResponse(true, "Error occurred while updating permission"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deletePermission(@RequestBody Permission permission) {
        try{
        permissionRepo.delete(permission);
        return new ResponseEntity(new RestResponse(false, "Permission deleted successfully"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
