package com.example.project.user.service;

import com.example.project.global.util.JwtTokenUtil;
import com.example.project.user.domain.User;
import com.example.project.user.dto.Token;
import com.example.project.user.dto.request.UserJoinRequest;
import com.example.project.user.dto.request.UserLoginRequest;
import com.example.project.user.dto.response.UserJoinResponse;
import com.example.project.user.dto.response.UserLoginResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.access-token-expiration}")
    private long expireAccessDate;

    @Value("${jwt.token.refresh-token-expiration}")
    private long expireRefreshDate;

    public UserJoinResponse join(final UserJoinRequest userJoinRequest) {

        // 사용자 email이 중복되면 예외 발생
        userRepository.findByUserEmail(userJoinRequest.getUserEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원입니다.");
                });

        // 사용자 비밀번호 암호화
        User saveUser = userRepository.save(userJoinRequest.toEntity(passwordEncoder.encode(userJoinRequest.getUserPassword())));

        return UserJoinResponse.fromEntity(saveUser);
    }

    public UserLoginResponse login(final UserLoginRequest userLoginRequest) {

        User user = userRepository.findByUserEmail(userLoginRequest.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(userLoginRequest.getUserPassword(), user.getUserPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        Token token = JwtTokenUtil.createToken(user.getUserId(), secretKey, expireAccessDate, expireRefreshDate);

        return UserLoginResponse.builder()
                .token(token)
                .build();
    }

    public User getUserByUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }
}
