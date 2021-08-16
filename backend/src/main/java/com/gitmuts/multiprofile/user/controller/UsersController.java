package com.gitmuts.multiprofile.user.controller;

import com.gitmuts.multiprofile.model.Constants;
import com.gitmuts.multiprofile.model.RestResponse;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.repo.UserRepo;
import com.gitmuts.multiprofile.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        try{
            return new ResponseEntity(userRepo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            String response = userService.createUser(user);

            if(response != null && response.equals(Constants.Response.SUCCESS.toString())){
                return new ResponseEntity(new RestResponse(false, "User created successfully"), HttpStatus.OK);
            }else {
                return new ResponseEntity(new RestResponse(true, response), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try{
            String response = userService.updateUser(user);

            if(response != null && response.equals(Constants.Response.SUCCESS.toString())){
                return new ResponseEntity(new RestResponse(false, "User updated successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new RestResponse(true, response), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        try{
            userRepo.delete(user);
            return new ResponseEntity(new RestResponse(false, "User deleted successfully"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
