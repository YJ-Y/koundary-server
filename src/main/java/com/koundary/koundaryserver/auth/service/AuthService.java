package com.koundary.koundaryserver.auth.service;

import com.koundary.koundaryserver.auth.domain.User;
import com.koundary.koundaryserver.auth.dto.LoginRequest;
import com.koundary.koundaryserver.auth.dto.SignupRequest;
import com.koundary.koundaryserver.auth.dto.TokenResponse;
import com.koundary.koundaryserver.auth.repository.UserRepository;
import com.koundary.koundaryserver.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignupRequest req){
        // 중복 가입 방지
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("이미 사용 중인 username입니다.");
        }

        // 비밀번호는 반드시 해시로 저장
        String hash = passwordEncoder.encode(req.password());

        User user = User.builder()
                .username(req.username())
                .passwordHash(hash)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException("username 또는 password가 올바르지 않습니다."));

        // 해시 비교는 matches로 검증
        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("username 또는 password가 올바르지 않습니다.");
        }

        String token = jwtProvider.generateAccessToken(user.getUsername());
        return new TokenResponse(token);
    }

}
