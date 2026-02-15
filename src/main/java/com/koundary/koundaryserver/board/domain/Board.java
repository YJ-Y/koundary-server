package com.koundary.koundaryserver.board.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "boards")

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* slug = URL에 들어갈 식별자 (null, 중복 모두 안 됨) */
    @Column(nullable = false, unique = true, length = 50)
    private String slug; // ex. free, notice

    /* name = 화면에 보여줄 게시판 이름 (null 안 됨) */
    @Column(nullable = false, length = 50)
    public String name; // ex. 자유게시판, 공지

    /* createdAt = 생성 시간, 저장되기 직전에 자동 세팅 */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* 외부에서 무분별한 생성 막음 */
    protected Board() {}

    public Board(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    /* 엔티티가 DB에 저장되기 직전에 실행
       createdAt이 비어있으면 현재 시간으로 채워서 저장 */
    @PrePersist
    private void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();

        }
    }

    /* Getter (Swagge/JSON 직렬화에 필요) */
    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
