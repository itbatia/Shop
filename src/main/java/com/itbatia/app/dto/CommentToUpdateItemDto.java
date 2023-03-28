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
public class CommentToUpdateItemDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("item")
    private ItemToUpdateDto item;

    public static CommentToUpdateItemDto fromComment(Comment comment) {
        return CommentToUpdateItemDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .content(content)
                .item(item.toItem())
                .build();
    }

    public static List<CommentToUpdateItemDto> fromComments(List<Comment> comments) {
        return comments != null ? comments.stream().map(CommentToUpdateItemDto::fromComment).collect(Collectors.toList()) : null;
    }

    public static List<Comment> toComments(List<CommentToUpdateItemDto> comments) {
        return comments != null ? comments.stream().map(CommentToUpdateItemDto::toComment).collect(Collectors.toList()) : null;
    }
}
