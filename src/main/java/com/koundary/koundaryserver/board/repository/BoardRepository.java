package com.koundary.koundaryserver.board.repository;

import com.koundary.koundaryserver.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{

    // slug로 게시판 찾기 (나중에 /api/boards/{slug} 같은 API에서 사용
    Optional<Board> findBySlug(String slug);

    // seed 중복 생성 방지용
    boolean existsBySlug(String slug);
}
