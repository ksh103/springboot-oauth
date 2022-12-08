package com.example.project.user.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String userEmail;
    private String userPassword;
}
