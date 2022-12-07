package com.example.project.user.dto.response;

import com.example.project.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinResponse {
    private String userEmail;
    private String userName;

    public static UserJoinResponse fromEntity(final User user) {
        return UserJoinResponse.builder()
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }
}
