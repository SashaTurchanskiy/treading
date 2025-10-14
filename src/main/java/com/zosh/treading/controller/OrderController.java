package com.zosh.treading.controller;

import com.zosh.treading.domain.OrderType;
import com.zosh.treading.model.Order;
import com.zosh.treading.model.User;
import com.zosh.treading.request.CreateOrderRequest;
import com.zosh.treading.service.CoinService;
import com.zosh.treading.service.OrderService;
import com.zosh.treading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CoinService coinService;
    //private final WalletTransactionalSerivce walletTransactionalSerivce;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req) throws Exception {

        var user = userService.findUserProfileByJwt(jwt);
        var coin = coinService.findById(req.getCoinId());

        var order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader ("Authorization") String jwt,
            @PathVariable Long orderId) throws Exception {

        var user = userService.findUserProfileByJwt(jwt);
        var order = orderService.getOrderById(orderId);

        if (order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        } else {
            throw new Exception("You are not authorized to view this order");
        }
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader ("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol) throws Exception {

        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrdersOfUser(userId, order_type, asset_symbol);
        return ResponseEntity.ok(userOrders);
    }
}
