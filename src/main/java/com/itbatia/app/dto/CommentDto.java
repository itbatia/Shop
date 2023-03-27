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
    @JsonProperty("item")
    private ItemToUpdateDto item;

    public static CommentDto fromComment(Comment comment) {

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .item(ItemToUpdateDto.fromItem(comment.getItem()))
                .build();
    }

    public Comment toComment() {

        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(content);
        comment.setItem(item.toItem());

        return comment;
    }

    public static List<CommentDto> fromComments(List<Comment> comments) {
        return comments.stream().map(CommentDto::fromComment).collect(Collectors.toList());
    }
}
