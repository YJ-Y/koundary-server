package com.koundary.koundaryserver.board.repository;

import com.koundary.koundaryserver.board.domain.Board;
import com.koundary.koundaryserver.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /* 특정 게시판의 글 목록 조회
        createdAt 내림차순으로 최신 글이 위로 오게 정렬
     */
    List<Post> findByBoardOrderByCreatedAtDesc(Board board);
}
