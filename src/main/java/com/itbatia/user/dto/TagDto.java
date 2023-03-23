package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Item;
import com.itbatia.user.model.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class TagDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("items")
    private List<ItemDto> items;

    public static TagDto fromTag(Tag tag) {

//        List<ItemDto> itemDtoList = tag.getItems() != null ?
//                tag.getItems().stream().map(ItemDto::fromItem).collect(Collectors.toList()) : null;

        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
//                .items(itemDtoList)
                .build();
    }

    public Tag toTag() {

//        List<Item> tagItems = items != null ?
//                items.stream().map(ItemDto::toItem).collect(Collectors.toList()) : null;

        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
//        tag.setItems(tagItems);

        return tag;
    }

    public static Set<TagDto> fromTags(Set<Tag> tags) {
        return tags != null ? tags.stream().map(TagDto::fromTag).collect(Collectors.toSet()) : null;
    }
}
