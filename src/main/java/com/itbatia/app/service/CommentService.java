package com.itbatia.app.service;

import com.itbatia.app.model.Comment;
import com.itbatia.app.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        log.info("IN createComment - Discount with id={} successfully created!", savedComment.getId());
        return savedComment;
    }
}
