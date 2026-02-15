package com.koundary.koundaryserver.board.dto;

import com.koundary.koundaryserver.board.domain.Post;
import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostResponse(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    /* 엔티티 -> DTO 변환을 한 곳에서 처리
        컨트롤러가 깔끔해지고 응답 형식 관리 쉬움
     */

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

