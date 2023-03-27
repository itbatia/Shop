package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Characteristic;
import com.itbatia.app.model.Item;
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
    @JsonProperty("item_id")
    private Long itemId;

    public static CharacteristicDto fromCharacteristic(Characteristic characteristic) {

        return CharacteristicDto.builder()
                .id(characteristic.getId())
                .name(characteristic.getName())
                .content(characteristic.getContent())
                .itemId(characteristic.getItem().getId())
                .build();
    }

    public Characteristic toCharacteristic() {

        Characteristic characteristic = new Characteristic();
        characteristic.setId(id);
        characteristic.setName(name);
        characteristic.setContent(content);
        characteristic.setItem(Item.builder().id(itemId).build());

        return characteristic;
    }

    public static List<CharacteristicDto> fromCharacteristics(List<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicDto::fromCharacteristic).collect(Collectors.toList());
    }
}
