package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.Order;
import com.itbatia.app.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.itbatia.app.dto.ItemToUpdateDto.*;
import static com.itbatia.app.dto.UserDto.fromUser;

@Getter
@Setter
@Builder
public class OrderDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("items")
    private List<ItemToUpdateDto> items;
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("commission")
    private Integer commission; //percent
    @JsonProperty("status")
    private String orderStatus;

    public static OrderDto fromOrder(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .items(fromItems(order.getItems()))
                .user(fromUser(order.getUser()))
                .totalPrice(order.getTotalPrice())
                .commission(order.getCommission())
                .orderStatus(order.getOrderStatus().name())
                .build();
    }

    public Order toOrder() {
        return Order.builder()
                .id(id)
                .createdAt(createdAt)
                .items(toItems(items))
                .user(user.toUser())
                .totalPrice(totalPrice)
                .commission(commission)
                .orderStatus(orderStatus != null ? OrderStatus.valueOf(orderStatus) : OrderStatus.SAVED)
                .build();
    }

    public static List<OrderDto> fromOrders(List<Order> orders) {
        return orders.stream().map(OrderDto::fromOrder).collect(Collectors.toList());
    }
}
