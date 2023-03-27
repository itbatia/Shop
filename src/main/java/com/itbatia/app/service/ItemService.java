package com.itbatia.app.service;

import com.itbatia.app.exceptions.ForbiddenChangeException;
import com.itbatia.app.exceptions.ItemNotFoundException;
import com.itbatia.app.exceptions.UserNotFoundException;
import com.itbatia.app.model.*;
import com.itbatia.app.repository.ItemRepository;
import com.itbatia.app.repository.OrderRepository;
import com.itbatia.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * This is a method for saving an item with all related entities
     *
     * @param itemToSave The new item has ItemStatus NEW. And only the admin can change it.
     * @see ItemStatus
     */
    @Transactional
    public Item createItem(Item itemToSave) {
        Item savedItem = itemRepository.save(itemToSave);
        log.info("IN createItem - Item with id={} successfully created!", savedItem.getId());
        return savedItem;
    }

    public Item findById(long id) {
        return itemRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item with id=" + id + " not found");
        });
    }

    public List<Item> findAllItemsByActiveOrganizations(Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });
        List<Item> items = itemRepository
                .findItemsOnlyFromActiveOrganizations(currentUser.getId(), OrganizationStatus.ACTIVE);
        log.info("IN findAllItemsByActiveOrganizations - List of items of size={} found!", items.size());
        return items;
    }

    @Transactional
    public void updateItem(Item item) {
        itemRepository.save(item);
        log.info("IN updateItem - Item with id={} successfully updated!", item.getId());
    }

    @Transactional
    public void updateAmountOfItem(Long itemId, Integer itemAmount, Character mathSignOfOperation) {
        Item item = findById(itemId);

        if (mathSignOfOperation.equals('-'))
            item.setTotalAmount(item.getTotalAmount() - itemAmount);
        if (mathSignOfOperation.equals('+'))
            item.setTotalAmount(item.getTotalAmount() + itemAmount);

        log.info("IN updateAmountOfItem - Amount of item with id={} successfully updated!", itemId);
    }

    @Transactional
    public void updateItemByUser(Item item, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });

        List<Order> orders = orderRepository.findByUserId(currentUser.getId());
        if (listOrdersHasItem(orders, item)) {
            itemRepository.save(item);
            log.info("IN updateItem - Item with id={} successfully updated!", item.getId());
        } else {
            throw new ForbiddenChangeException("You can`t change this item without buying it");
        }
    }

    private boolean listOrdersHasItem(List<Order> orders, Item testItem) {
        AtomicBoolean itemPurchasedByUser = new AtomicBoolean(false);
        orders.stream().flatMap(order -> order.getItems().stream()).forEach(item -> {
            if (item.getId().equals(testItem.getId())) {
                itemPurchasedByUser.set(true);
            }
        });
        return itemPurchasedByUser.get();
    }


}
