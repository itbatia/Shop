package com.itbatia.app.rest;

import com.itbatia.app.dto.DiscountDto;
import com.itbatia.app.model.Discount;
import com.itbatia.app.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.itbatia.app.dto.DiscountDto.fromDiscount;

@RestController
@RequestMapping(value = "/api/v1/discounts")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class DiscountRestControllerV1 {

    private final DiscountService discountService;

    /**
     * Admin can add discounts for an item or group of items
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DiscountDto discountDto) {
        Discount discountToSave = discountDto.toDiscount();
        Discount newDiscount = discountService.createDiscount(discountToSave);
        return new ResponseEntity<>(fromDiscount(newDiscount), HttpStatus.CREATED);
    }

    /**
     * Admin can change discounts for an item or group of items
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody DiscountDto discountDto) {
        Discount discountToUpdate = discountDto.toDiscount();
        discountService.updateDiscount(discountToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
