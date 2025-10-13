package com.zosh.treading.service;

import com.zosh.treading.domain.OrderType;
import com.zosh.treading.model.Coin;
import com.zosh.treading.model.Order;
import com.zosh.treading.model.OrderItem;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepo orderRepo;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        return null;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return List.of();
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
