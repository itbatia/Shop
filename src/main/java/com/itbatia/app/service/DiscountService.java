package com.itbatia.app.service;

import com.itbatia.app.model.Discount;
import com.itbatia.app.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscountService {

    private final DiscountRepository discountRepository;

    @Transactional
    public Discount createDiscount(Discount discount) {
        discount.setStartDate(new Date());
        discount.setEndDate(new Date(new Date().getTime() + 86400000));
        Discount savedDiscount = discountRepository.save(discount);
        log.info("IN createDiscount - Discount with id={} successfully created!", savedDiscount.getId());
        return savedDiscount;
    }

    @Transactional
    public void updateDiscount(Discount discount) {
        discountRepository.save(discount);
        log.info("IN updateDiscount - Discount with id={} successfully updated!", discount.getId());
    }
}
