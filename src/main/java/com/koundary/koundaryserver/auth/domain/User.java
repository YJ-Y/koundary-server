package com.koundary.koundaryserver.auth.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // username은 로그인 식별자, 중복 가입 방지 위해 unique
    @Column(nullable = false, unique = true)
    private String username;

    // 비밀번호는 평문 저장 금지, 반드시 해시로 저장
    @Column(nullable = false)
    private String passwordHash;

    // 초기 단계는 ROLE_USER만 사용
    @Column(nullable = false)
    private String role;

    @Builder
    private User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}
