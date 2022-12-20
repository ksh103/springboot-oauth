package com.example.project.user.dto.response;

import com.example.project.user.dto.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private Token token;
}
