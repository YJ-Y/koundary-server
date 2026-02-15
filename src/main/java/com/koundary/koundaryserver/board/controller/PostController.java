package com.koundary.koundaryserver.board.controller;

import com.koundary.koundaryserver.board.domain.Board;
import com.koundary.koundaryserver.board.domain.Post;
import com.koundary.koundaryserver.board.dto.PostCreateRequest;
import com.koundary.koundaryserver.board.dto.PostResponse;
import com.koundary.koundaryserver.board.repository.BoardRepository;
import com.koundary.koundaryserver.board.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{slug}/posts")
public class PostController {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public PostController(BoardRepository boardRepository, PostRepository postRepository){
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
    }

    /*
    GET /api/boards/{slug}/posts
    : 특정 게시판의 글 목록 조회
     */
    @GetMapping
    public List<PostResponse> list(@PathVariable String slug) {
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found: " + slug));
        return postRepository.findByBoardOrderByCreatedAtDesc(board)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    /*
    Post /api/boards/{slug}/posts
    : 특정 게시판에 글 작성
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@PathVariable String slug, @RequestBody PostCreateRequest request) {
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found: " + slug));

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
        }

        Post saved = postRepository.save(new Post(board, request.getTitle(), request.getContent()));
        return PostResponse.from(saved);
    }

}
