package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Discount;
import com.itbatia.user.model.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class DiscountDto {

    @JsonProperty("id")
    private Long id;
//    @JsonProperty("items")
//    private List<ItemDto> items;
    @JsonProperty("discount_amount")
    private BigDecimal discountAmount; //in money
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;

    public static DiscountDto fromDiscount(Discount discount) {

        return DiscountDto.builder()
                .id(discount.getId())
//                .items(itemDtoList)
                .discountAmount(discount.getDiscountAmount())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .build();
    }

    public Discount toDiscount() {

//        List<Item> discountItems = items.stream().map(ItemDto::toItem).collect(Collectors.toList());

        Discount discount = new Discount();
        discount.setId(id);
//        discount.setItems(discountItems);
        discount.setDiscountAmount(discountAmount);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);

        return discount;
    }

    public static List<DiscountDto> fromDiscounts(List<Discount> discounts) {
        return discounts.stream().map(DiscountDto::fromDiscount).collect(Collectors.toList());
    }
}
