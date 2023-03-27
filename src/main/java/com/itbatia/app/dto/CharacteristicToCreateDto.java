package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Characteristic;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CharacteristicToCreateDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;

    public static CharacteristicToCreateDto fromCharacteristic(Characteristic characteristic) {
        return CharacteristicToCreateDto.builder()
                .id(characteristic.getId())
                .name(characteristic.getName())
                .content(characteristic.getContent())
                .build();
    }

    public Characteristic toCharacteristic() {
        return Characteristic.builder().id(id).name(name).content(content).build();
    }

    public static List<CharacteristicToCreateDto> fromCharacteristics(List<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicToCreateDto::fromCharacteristic).collect(Collectors.toList());
    }

    public static List<Characteristic> toCharacteristics(List<CharacteristicToCreateDto> characteristicsDto) {
        return characteristicsDto != null ? characteristicsDto.stream().map(CharacteristicToCreateDto::toCharacteristic)
                .collect(Collectors.toList()) : null;
    }
}
