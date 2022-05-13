package com.example.myhome.controller;

import java.util.List;

import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
class BoardApiController {
    @Autowired
    private BoardRepository repository;

    @GetMapping("/board")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/board")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    @GetMapping("/board/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElseThrow(null);
    }

    @PutMapping("/board/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/board/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
