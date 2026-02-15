package com.koundary.koundaryserver.global.security;

import com.koundary.koundaryserver.auth.domain.User;
import com.koundary.koundaryserver.auth.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 헤더에서 Authorization: Bearer 토큰 추출
        String token = resolveBearerToken(request);

        // 2. 토큰이 없거나 유효하지 않으면 -> 로그인 처리 안 하고 그냥 통과
        //    GET 공개 API들은 그대로 접근 가능해야 됨
        if (token == null || !jwtProvider.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰이 유효하면 username(subject) 뽑기
        String username = jwtProvider.getUsername(token);

        // 4. DB에서 유저 존재 확인
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            // 5. Spring Security가 이해하는 인증 객체 생성
            var auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), // 로그인 유저 식별자
                    null,
                    List.of(new SimpleGrantedAuthority(user.getRole())) // 권한
            );

            // 6. SecurityContext에 등록 = 이 요청은 로그인한 요청
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 7. 다음 필터/컨트롤러로 이동
        filterChain.doFilter(request, response);

    }

    private String resolveBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        // Bearer: 로 시작하면 토큰 부분만 잘라서 변환
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}

