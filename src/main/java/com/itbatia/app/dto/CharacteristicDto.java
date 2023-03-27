package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Characteristic;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static com.itbatia.app.dto.ItemToCreateDto.*;

@Getter
@Setter
@Builder
public class CharacteristicDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
    @JsonProperty("item_id")
    private ItemToCreateDto itemDto;

    public static CharacteristicDto fromCharacteristic(Characteristic characteristic) {
        return CharacteristicDto.builder()
                .id(characteristic.getId())
                .name(characteristic.getName())
                .content(characteristic.getContent())
                .itemDto(fromItem(characteristic.getItem()))
                .build();
    }

    public Characteristic toCharacteristic() {
        return Characteristic.builder()
                .id(id)
                .name(name)
                .content(content)
                .item(null)
                .build();
    }

    public static List<CharacteristicDto> fromCharacteristics(List<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicDto::fromCharacteristic).collect(Collectors.toList());
    }

    public static List<Characteristic> toCharacteristics(List<CharacteristicDto> characteristicsDto) {
        return characteristicsDto != null ? characteristicsDto.stream().map(CharacteristicDto::toCharacteristic)
                .collect(Collectors.toList()) : null;
    }
}
