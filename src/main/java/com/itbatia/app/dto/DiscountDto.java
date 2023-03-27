package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Discount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class DiscountDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("discount_amount")
    private BigDecimal discountAmount; //in money
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;

    public static DiscountDto fromDiscount(Discount discount) {
        return DiscountDto.builder()
                .id(discount.getId())
                .discountAmount(discount.getDiscountAmount())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .build();
    }

    public Discount toDiscount() {
        return Discount.builder()
                .id(id)
                .discountAmount(discountAmount)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
