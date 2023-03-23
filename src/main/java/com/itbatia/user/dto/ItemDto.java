package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itbatia.user.dto.CharacteristicDto.fromCharacteristics;
import static com.itbatia.user.dto.TagDto.fromTags;

@Getter
@Setter
@Builder
public class ItemDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("organization")
    private OrganizationDto organizationDto;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("discount")
    private DiscountDto discountDto; //in money
    @JsonProperty("tags")
    private Set<TagDto> tags;
    @JsonProperty("characteristics")
    private List<CharacteristicDto> characteristics;
    @JsonProperty("grade")
    private Integer grade;
    @JsonProperty("status")
    private String itemStatus;

    public static ItemDto fromItem(Item item) {

        Set<TagDto> tagDtoSet = item.getTags() != null ? fromTags(item.getTags()) : null;
        List<CharacteristicDto> characteristicDtoList = item.getCharacteristics() != null ?
                fromCharacteristics(item.getCharacteristics()) : null;

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .organizationDto(OrganizationDto.fromOrganization(item.getOrganization()))
                .price(item.getPrice())
                .totalAmount(item.getTotalAmount())
                .discountDto(item.getDiscount() != null ? DiscountDto.fromDiscount(item.getDiscount()) : null)
                .tags(tagDtoSet)
                .characteristics(characteristicDtoList)
                .grade(item.getGrade())
                .itemStatus(item.getItemStatus().name())
                .build();
    }

    public Item toItem() {

        Set<Tag> itemTags = tags != null ? tags.stream().map(TagDto::toTag).collect(Collectors.toSet()) : null;
        List<Characteristic> itemCharacteristics = characteristics != null ?
                characteristics.stream().map(CharacteristicDto::toCharacteristic).collect(Collectors.toList()) : null;

        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setOrganization(organizationDto != null ? organizationDto.toOrganization() : null);
        item.setPrice(price);
        item.setTotalAmount(totalAmount);
        item.setDiscount(discountDto != null ? discountDto.toDiscount() : null);
        item.setTags(itemTags);
        item.setCharacteristics(itemCharacteristics);
        item.setGrade(grade != null ? grade : 0);
        item.setItemStatus(itemStatus != null ? ItemStatus.valueOf(itemStatus) : ItemStatus.NEW);

        return item;
    }

    public static List<ItemDto> fromItems(List<Item> items) {
        return items.stream().map(ItemDto::fromItem).collect(Collectors.toList());
    }
}
