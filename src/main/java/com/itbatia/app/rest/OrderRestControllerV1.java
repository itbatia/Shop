package com.itbatia.app.rest;

import com.itbatia.app.dto.OrderDto;
import com.itbatia.app.model.Order;
import com.itbatia.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.itbatia.app.dto.OrderDto.*;

@RestController
@RequestMapping(value = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestControllerV1 {

    private final OrderService orderService;

    /**
     * Each user's purchase is stored in the order history
     */
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> crete(@RequestBody OrderDto orderDto) {
        Order orderToSave = orderDto.toOrder();
        Order savedOrder = orderService.createOrder(orderToSave);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    /**
     * The user can view his order history
     */
    @Secured("ROLE_USER")
    @GetMapping("/by_user")
    public ResponseEntity<?> getAllByCurrentUser(Principal principal) {
        List<Order> orders = orderService.findAllByCurrentUser(principal);
        return ResponseEntity.ok().body(fromOrders(orders));
    }

    /**
     * The admin can view the order history of any user
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/by_user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable("id") Long id) {
        List<Order> orders = orderService.findAllByUserId(id);
        return ResponseEntity.ok().body(fromOrders(orders));
    }

    /**
     * The user can return the purchased item during 24 hours
     */
    @Secured("ROLE_USER")
    @PostMapping("/{id}")
    public ResponseEntity<?> cancelOfOrder(@PathVariable("id") Long id, Principal principal) {
        orderService.cancelOrder(id, principal);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
