package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class TagDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    public static TagDto fromTag(Tag tag) {
        return TagDto.builder().id(tag.getId()).name(tag.getName()).build();
    }

    public Tag toTag() {
        return Tag.builder().id(id).name(name).build();
    }

    public static List<TagDto> fromTags(List<Tag> tags) {
        return tags != null ? tags.stream().map(TagDto::fromTag).collect(Collectors.toList()) : null;
    }

    public static List<Tag> toTags(List<TagDto> tagsDto) {
        return tagsDto != null ? tagsDto.stream().map(TagDto::toTag).collect(Collectors.toList()) : null;
    }
}
