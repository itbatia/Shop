package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.itbatia.app.dto.CharacteristicDto.*;
import static com.itbatia.app.dto.TagDto.*;

@Getter
@Setter
@Builder
public class ItemToCreateDto {

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
    @JsonProperty("tags")
    private List<TagDto> tagsDto;
    @JsonProperty("characteristics")
    private List<CharacteristicDto> characteristicsDto;
    @JsonProperty("status")
    private String itemStatus;

    public static ItemToCreateDto fromItem(Item item) {
        return ItemToCreateDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .organizationId(item.getOrganization().getId())
                .price(item.getPrice())
                .totalAmount(item.getTotalAmount())
                .tagsDto(fromTags(item.getTags()))
                .characteristicsDto(fromCharacteristics(item.getCharacteristics()))
                .itemStatus(item.getItemStatus().name())
                .build();
    }

    public Item toItem() {
        return Item.builder()
                .name(name)
                .description(description)
                .organization(Organization.builder().id(organizationId).build())
                .price(price)
                .totalAmount(totalAmount)
                .tags(toTags(tagsDto))
                .characteristics(toCharacteristics(characteristicsDto))
                .itemStatus(itemStatus != null ? ItemStatus.valueOf(itemStatus) : null)
                .build();
    }

    public static List<ItemToCreateDto> fromItems(List<Item> items) {
        return items.stream().map(ItemToCreateDto::fromItem).collect(Collectors.toList());
    }
}
