package com.itbatia.app.rest;

import com.itbatia.app.dto.ItemToUpdateDto;
import com.itbatia.app.dto.ItemToCreateDto;
import com.itbatia.app.model.Item;
import com.itbatia.app.model.OrganizationStatus;
import com.itbatia.app.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.itbatia.app.dto.ItemToUpdateDto.*;

@RestController
@RequestMapping(value = "/api/v1/items")
@RequiredArgsConstructor
public class ItemRestControllerV1 {

    private final ItemService itemService;

    /**
     * The admin can create the items in the store<br/>
     * The user can add request for registration of item (i.e. create the item with status NEW)
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemToCreateDto itemDto, Principal principal) {
        Item itemToSave = itemDto.toItem();
        Item createdItem = itemService.createItem(itemToSave, principal);
        return new ResponseEntity<>(fromItem(createdItem), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        ItemToUpdateDto itemDto = ItemToUpdateDto.fromItem(itemService.findById(id));
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    /**
     * The admin can change any information about the items in the store
     */
    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ItemToUpdateDto itemDto) {
        Item itemToUpdate = itemDto.toItem();
        itemService.updateItem(itemToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * User can leave reviews about items and rate them. But only purchased items!
     */
    @Secured("ROLE_USER")
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody ItemToUpdateDto itemDto, Principal principal) {
        Item itemToUpdate = itemDto.toItem();
        itemService.updateItemByUser(itemToUpdate, principal);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * The user can get a list of available items. These items are from active organizations only.
     *
     * @see OrganizationStatus
     */
    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<?> getAll(Principal principal) {
        List<Item> items = itemService.findAllItemsByActiveOrganizations(principal);
        return new ResponseEntity<>(fromItems(items), HttpStatus.OK);
    }
}
