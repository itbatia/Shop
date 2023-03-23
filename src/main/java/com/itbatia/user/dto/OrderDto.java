package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Item;
import com.itbatia.user.model.Order;
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
public class OrderDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("items")
    private List<ItemDto> items;
    @JsonProperty("user")
    private UserDto userDto;
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
    @JsonProperty("commission")
    private Integer commission; //percent

    public static OrderDto fromOrder(Order order) {

        List<ItemDto> itemDtoList =
                order.getItems().stream().map(ItemDto::fromItem).collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .items(itemDtoList)
                .userDto(UserDto.fromUser(order.getUser()))
                .totalAmount(order.getTotalAmount())
                .commission(order.getCommission())
                .build();
    }

    public Order toOrder() {

        List<Item> orderItems =
                items.stream().map(ItemDto::toItem).collect(Collectors.toList());

        Order order = new Order();
        order.setId(id);
        order.setCreatedAt(createdAt);
        order.setItems(orderItems);
        order.setUser(userDto.toUser());
        order.setTotalAmount(totalAmount);
        order.setCommission(commission);

        return order;
    }

    public static List<OrderDto> fromOrders(List<Order> orders) {
        return orders.stream().map(OrderDto::fromOrder).collect(Collectors.toList());
    }
}
