package com.koundary.koundaryserver.board.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 글 제목 - null 불가, 길이 제한 */
    @Column(nullable = false, length = 100)
    private String title;

    /*
        글 내용 = 길이가 길 수 있어 TEXT로 매핑
        columnDefinition = "TEXT"는 MySQL에서 긴 문자열에 적합
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /*
        ManyToOne: 게시판 1개에 글이 여러 개
        필요한 순간에만 board를 가져오도록 지연 로딩
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false) // posts 테이블에 board_id 컬럼 생성
    private Board board;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Post() {}

    public Post(Board board, String title, String content){
        this.board = board;
        this.title = title;
        this.content = content;
    }

    @PrePersist
    private void prePersist(){
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Board getBoard() { return board; }

}
