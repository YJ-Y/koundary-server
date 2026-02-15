package com.koundary.koundaryserver.board.controller;

import com.koundary.koundaryserver.board.domain.Board;
import com.koundary.koundaryserver.board.repository.BoardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping
    public List<Board> list() {
        return boardRepository.findAll();
    }
}
