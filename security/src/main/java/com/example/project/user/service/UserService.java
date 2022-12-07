package com.example.project.user.service;

import com.example.project.user.domain.User;
import com.example.project.user.dto.UserDto;
import com.example.project.user.dto.request.UserJoinRequest;
import com.example.project.user.dto.response.UserJoinResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expiredTimeMs = 1000 * 60 * 60; // 1시간

    public UserJoinResponse join(final UserJoinRequest userJoinRequest) {

        // 사용자 email이 중복되면 예외 발생
        userRepository.findByUserEmail(userJoinRequest.getUserEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원입니다.");
                });

        // 사용자 비밀번호 암호화
        User saveUser = userRepository.save(userJoinRequest.toEntity(bCryptPasswordEncoder.encode(userJoinRequest.getUserPassword())));

        return UserJoinResponse.fromEntity(saveUser);
    }

}
