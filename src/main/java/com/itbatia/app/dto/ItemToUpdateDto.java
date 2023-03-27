package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.itbatia.app.dto.CharacteristicToUpdateDto.*;
import static com.itbatia.app.dto.CommentDto.*;
import static com.itbatia.app.dto.DiscountDto.*;
import static com.itbatia.app.dto.TagDto.*;

@Getter
@Setter
@Builder
public class ItemToUpdateDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("organization_id")
    private Long organizationId;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("discount")
    private DiscountDto discountDto;
    @JsonProperty("comments")
    private List<CommentDto> commentsDto;
    @JsonProperty("tags")
    private List<TagDto> tagsDto;
    @JsonProperty("characteristics")
    private List<CharacteristicToUpdateDto> characteristicsDto;
    @JsonProperty("grade")
    private Integer grade;
    @JsonProperty("status")
    private String itemStatus;

    public static ItemToUpdateDto fromItem(Item item) {
        return ItemToUpdateDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .organizationId(item.getOrganization().getId())
                .price(item.getPrice())
                .totalAmount(item.getTotalAmount())
                .discountDto(item.getDiscount() != null ? fromDiscount(item.getDiscount()) : null)
                .commentsDto(fromComments(item.getComments()))
                .tagsDto(fromTags(item.getTags()))
                .characteristicsDto(fromCharacteristics(item.getCharacteristics()))
                .grade(item.getGrade())
                .itemStatus(item.getItemStatus().name())
                .build();
    }

    public Item toItem() {
        return Item.builder()
                .id(id != null ? id : null)
                .name(name)
                .description(description)
                .organization(Organization.builder().id(organizationId).build())
                .price(price)
                .totalAmount(totalAmount)
                .discount(discountDto != null ? discountDto.toDiscount() : null)
                .comments(toComments(commentsDto))
                .tags(toTags(tagsDto))
                .characteristics(toCharacteristics(characteristicsDto))
                .grade(grade != null ? grade : 0)
                .itemStatus(itemStatus != null ? ItemStatus.valueOf(itemStatus) : ItemStatus.NEW)
                .build();
    }

    public static List<ItemToUpdateDto> fromItems(List<Item> items) {
        return items.stream().map(ItemToUpdateDto::fromItem).collect(Collectors.toList());
    }

    public static List<Item> toItems(List<ItemToUpdateDto> items) {
        return items.stream().map(ItemToUpdateDto::toItem).collect(Collectors.toList());
    }
}
