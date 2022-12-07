package com.example.project.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long userId;
    private String userEmail;
    private String userPassword;
    private String userName;
}
