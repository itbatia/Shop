package com.itbatia.app.service;

import com.itbatia.app.model.Characteristic;
import com.itbatia.app.repository.CharacteristicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;

    @Transactional
    public Characteristic updateCharacteristic(Characteristic characteristic) {

        Characteristic characteristicToUpdate = characteristicRepository.findById(characteristic.getId()).orElse(null);

        characteristicToUpdate.setName(characteristic.getName());
        characteristicToUpdate.setContent(characteristic.getContent());

        log.info("IN updateCharacteristic - Characteristic with id={} successfully updated!", characteristic.getId());

        return characteristicToUpdate;
    }
}
