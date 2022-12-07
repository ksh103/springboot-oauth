package com.example.project.user.dto.request;

import com.example.project.user.domain.Role;
import com.example.project.user.domain.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class UserJoinRequest {
    private String userEmail;
    private String userPassword;
    private String userName;

    public User toEntity(final String userPassword) {
        return User.builder()
                .userEmail(this.userEmail)
                .userPassword(userPassword)
                .userName(this.userName)
                .role(Role.USER)
                .build();
    }
}
