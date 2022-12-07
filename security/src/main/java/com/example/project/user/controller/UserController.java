package com.example.project.user.controller;

import com.example.project.user.dto.request.UserJoinRequest;
import com.example.project.user.dto.response.UserJoinResponse;
import com.example.project.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 정보", description = "사용자 정보 API")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Tag(name = "사용자 정보")
    @Operation(summary = "회원가입", description = "사용자가 회원가입을 한다.")
    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        log.info("join - call");

        UserJoinResponse userJoinResponse = userService.join(userJoinRequest);

        return ResponseEntity.ok(userJoinResponse);
    }
}
