package com.itbatia.user.rest;

import com.itbatia.user.dto.ItemDto;
import com.itbatia.user.dto.UserDto;
import com.itbatia.user.model.Item;
import com.itbatia.user.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.itbatia.user.dto.UserDto.fromUser;

@RestController
@RequestMapping(value = "/api/v1/items")
@RequiredArgsConstructor
public class ItemRestControllerV1 {

    private final ItemService itemService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ItemDto itemDto) {
        Item itemToSave = itemDto.toItem();

        Item createdItem = itemService.createItem(itemToSave);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        ItemDto itemDto = ItemDto.fromItem(itemService.findById(id));
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody ItemDto itemDto) {
        Item itemToUpdate = itemDto.toItem();

        itemService.updateItem(itemToUpdate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
