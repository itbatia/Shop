package com.itbatia.app.service;

import com.itbatia.app.exceptions.UserNotFoundException;
import com.itbatia.app.model.*;
import com.itbatia.app.repository.OrderRepository;
import com.itbatia.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final OrganizationService organizationService;

    @Value("${shop.commission.percentage}")
    private Integer commissionPercentage;

    @Transactional
    public Order createOrder(Order order) {
        order.setCreatedAt(new Date());
        order.setCommission(commissionPercentage);
        order.setOrderStatus(OrderStatus.SAVED);
        Order savedOrder = orderRepository.save(order);

        List<Item> items = savedOrder.getItems();
        updateAmountItemsAndOrganizationBalance(items, MathSignOfOperation.MINUS, MathSignOfOperation.PLUS);

        log.info("IN createOrder - Order with id={} successfully created!", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Parameters <p color="blue">mathSignOfOperationForItem</p>
     * Takes 2 values: If the item is purchased: 'MINUS', if the item is returned: 'PLUS'.
     * <p color="blue">mathSignOfOperationForOrganization</p>
     * Takes 2 values: If the item is purchased: 'PLUS', if the item is returned: 'MINUS'.
     */
    private void updateAmountItemsAndOrganizationBalance(List<Item> items,
                                                         MathSignOfOperation mathSignOfOperationForItem,
                                                         MathSignOfOperation mathSignOfOperationForOrganization) {
        items.forEach((item -> {
            itemService.updateAmountOfItem(item.getId(), item.getTotalAmount(), mathSignOfOperationForItem);
            organizationService.updateBalanceOfOrganization(item, mathSignOfOperationForOrganization);
        }));
    }

    public List<Order> findAllByUserId(Long id) {
        List<Order> userOrders = orderRepository.findAllByUserId(id);
        log.info("IN findAllByUserId - {} orders found by userId={}", userOrders.size(), id);
        return userOrders;
    }

    public List<Order> findAllByCurrentUser(Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });
        List<Order> userOrders = orderRepository.findAllByUserId(currentUser.getId());
        log.info("IN findAllByCurrentUser - {} orders found for user '{}'", userOrders.size(), currentUser.getUsername());
        return userOrders;
    }

    @Transactional
    public void cancelOrder(Long id, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });
        currentUser.getOrders().forEach(order -> {
            if (order.getId().equals(id)) {
                if (!cancellationDeadlineHasExpired(order.getCreatedAt())) {
                    Order orderToCancel = orderRepository.findById(id).orElseThrow();
                    orderToCancel.setOrderStatus(OrderStatus.CANCELED);
                    updateAmountItemsAndOrganizationBalance(order.getItems(), MathSignOfOperation.PLUS, MathSignOfOperation.MINUS);
                    log.info("IN cancelOrder - order with id={} deleted", id);
                }
            }
        });
    }

    private boolean cancellationDeadlineHasExpired(Date date) {
        Long currentTime = new Date().getTime();
        Long orderCreatingTime = date.getTime();
        return currentTime - orderCreatingTime > 86400000;
    }
}
