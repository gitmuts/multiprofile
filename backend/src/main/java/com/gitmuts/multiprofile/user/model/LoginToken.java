package com.gitmuts.multiprofile.user.model;

import com.gitmuts.multiprofile.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginToken {

    private User user;
    private String token;
}
