package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CommentDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;

    public static CommentDto fromComment(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .content(content)
                .build();
    }

    public static List<CommentDto> fromComments(List<Comment> comments) {
        return comments != null ? comments.stream().map(CommentDto::fromComment).collect(Collectors.toList()) : null;
    }

    public static List<Comment> toComments(List<CommentDto> comments) {
        return comments != null ? comments.stream().map(CommentDto::toComment).collect(Collectors.toList()) : null;
    }
}
