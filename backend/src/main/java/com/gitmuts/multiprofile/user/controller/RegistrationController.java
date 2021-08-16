package com.gitmuts.multiprofile.user.controller;

import com.gitmuts.multiprofile.model.RestResponse;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.CompleteReg;
import com.gitmuts.multiprofile.user.model.UserRegistration;
import com.gitmuts.multiprofile.user.repo.UserRegRepo;
import com.gitmuts.multiprofile.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@Slf4j
public class RegistrationController {

    @Autowired
    UserRegRepo userRegRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/{token}")
    public ResponseEntity<?> getPermissions(@PathVariable(name="token") String token) {
        try{
            UserRegistration userRegistration = userRegRepo.findByToken(token);
            if(userRegistration == null){
                return new ResponseEntity(new RestResponse(true, "Token not found or expired"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(userRegistration.getUser(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new RestResponse(true, "Error occurred, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody CompleteReg completeReg) {
        try{
            User user = userRepo.findByUsername(completeReg.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(completeReg.getPassword()));
            userRepo.save(user);

            UserRegistration userRegistration = userRegRepo.findByToken(completeReg.getToken());
            userRegistration.setUsed(1);
            userRegRepo.save(userRegistration);

            return new ResponseEntity(new RestResponse(false, "User sign up complete, Please login"), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error occurred {}", e.getMessage(), e);
            return new ResponseEntity(new RestResponse(true, e.getMessage() != null ? e.getMessage() : "Error occurred, try later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
