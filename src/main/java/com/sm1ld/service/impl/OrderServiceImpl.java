package com.sm1ld.service.impl;

import com.sm1ld.mapper.CartMapper;
import com.sm1ld.mapper.OrderItemMapper;
import com.sm1ld.mapper.OrderMapper;
import com.sm1ld.pojo.Order;
import com.sm1ld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<Order> getOrderByUid(int userId) {
        return orderMapper.getOrderByUid(userId);
    }


    @Override
    public void createOrder(Order order) {
        orderMapper.insertOrder(order);
    }


    @Override
    public Order getOrderDetails(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public void updateOrder(Order order, Integer userId) {
        orderMapper.updateOrder(order, userId);
    }

    @Override
    public void delOrderByOU(Integer orderId, Integer userId) {
        orderMapper.delOrderByOU(orderId, userId);
    }

}
//    @Override
//    public List<Order> getAllOrders() {
//        return orderMapper.selectAllOrders();
//    }