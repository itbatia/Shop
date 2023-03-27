package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Characteristic;
import com.itbatia.app.model.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CharacteristicToUpdateDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
    @JsonProperty("item_id")
    private Long itemId;

    public static CharacteristicToUpdateDto fromCharacteristic(Characteristic characteristic) {
        return CharacteristicToUpdateDto.builder()
                .id(characteristic.getId())
                .name(characteristic.getName())
                .content(characteristic.getContent())
                .itemId(characteristic.getId())
                .build();
    }

    public Characteristic toCharacteristic() {
        return Characteristic.builder().id(id).name(name).content(content)
                .item(Item.builder().id(itemId).build()).build();
    }

    public static List<CharacteristicToUpdateDto> fromCharacteristics(List<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicToUpdateDto::fromCharacteristic).collect(Collectors.toList());
    }

    public static List<Characteristic> toCharacteristics(List<CharacteristicToUpdateDto> characteristicsDto) {
        return characteristicsDto != null ? characteristicsDto.stream().map(CharacteristicToUpdateDto::toCharacteristic)
                .collect(Collectors.toList()) : null;
    }
}
