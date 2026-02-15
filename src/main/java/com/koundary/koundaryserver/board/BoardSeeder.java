package com.koundary.koundaryserver.board;

import com.koundary.koundaryserver.board.domain.Board;
import com.koundary.koundaryserver.board.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BoardSeeder implements CommandLineRunner {

    private final BoardRepository boardRepository;

    public BoardSeeder(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /* CommmandLineRunner = 스프링 부트가 완전히 뜬 뒤 1번 실행
        개발 테스트 환경에서 초깃값 넣는 용도
     */

    @Override
    public void run(String... args) {
        seed("notice", "공지");
        seed("free", "자유게시판");
        seed("qna", "질문게시판");
    }

    private void seed(String slug, String name) {
        // 이미 있으면 또 만들지 않게 중복 방지
        if (!boardRepository.existsBySlug(slug)) {
            boardRepository.save(new Board(slug, name));
        }
    }
}

