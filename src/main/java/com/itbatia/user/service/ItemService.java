package com.itbatia.user.service;

import com.itbatia.user.exceptions.ItemNotFoundException;
import com.itbatia.user.model.Characteristic;
import com.itbatia.user.model.Item;
import com.itbatia.user.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final CharacteristicService characteristicService;

    @Transactional
    public Item createItem(Item itemToSave) {
        Item createdItem = itemRepository.save(itemToSave);
        log.info("IN createItem - Item with id=" + createdItem.getId() + " successfully created!");
        return createdItem;
    }

    @Transactional
    public Item updateItem(Item item) {

        Item itemToUpdate = itemRepository.findById(item.getId()).orElse(null);

        itemToUpdate.setName(item.getName());
        itemToUpdate.setDescription(item.getDescription());
        itemToUpdate.setOrganization(item.getOrganization());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setTotalAmount(item.getTotalAmount());
        itemToUpdate.setDiscount(item.getDiscount());
        itemToUpdate.setTags(item.getTags());
        itemToUpdate.setGrade(item.getGrade());
        itemToUpdate.setItemStatus(item.getItemStatus());

        //Вариант 1:
//        item.getCharacteristics().forEach(characteristicService::updateCharacteristic);
        //Вариант 2:
        updateCharacteristicList(item.getCharacteristics(), itemToUpdate.getCharacteristics());

        log.info("IN updateItem - Item with id=" + item.getId() + " successfully updated!");

        return itemToUpdate;
    }

    private void updateCharacteristicList(List<Characteristic> source, List<Characteristic> destination) {
        for (int i = 0; i < destination.size(); i++) {
            destination.get(i).setName(source.get(i).getName());
            destination.get(i).setContent(source.get(i).getContent());
        }
    }

    public Item findById(long id) {
        Item item = itemRepository.findById(id).orElse(null);

        if (item == null) {
            String message = "Item with id=" + id + " not found";
            log.error("IN findById - " + message);
            throw new ItemNotFoundException(message);
        }
        return item;
    }
}
