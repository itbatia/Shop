package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Characteristic;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
    @JsonProperty("item")
    private ItemDto itemDto;

    public static CharacteristicDto fromCharacteristic(Characteristic characteristic) {

        return CharacteristicDto.builder()
                .id(characteristic.getId())
                .name(characteristic.getName())
                .content(characteristic.getContent())
//                .itemDto(ItemDto.fromItem(characteristic.getItem()))
                .build();
    }

    public Characteristic toCharacteristic() {

        Characteristic characteristic = new Characteristic();
        characteristic.setId(id);
        characteristic.setName(name);
        characteristic.setContent(content);
        characteristic.setItem(itemDto != null ? itemDto.toItem() : null);

        return characteristic;
    }

    public static List<CharacteristicDto> fromCharacteristics(List<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicDto::fromCharacteristic).collect(Collectors.toList());
    }
}
