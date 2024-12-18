package com.sm1ld.service.impl;

import com.sm1ld.mapper.OrderMapper;
import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.OrderItem;
import com.sm1ld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public List<Order> getOrderByUid(int userId) {
        return orderMapper.getOrderByUid(userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)  // 保证整个方法在同一个事务中
    public void createOrder(Order order) {
        // 插入订单
        orderMapper.insertOrder(order);

        // 获取订单ID并插入订单商品
        Integer orderId = order.getOrderId();
        if (orderId == null) {
            throw new RuntimeException("订单ID生成失败");
        }

        List<OrderItem> orderItems = order.getOrderItem();
        if (orderItems == null || orderItems.isEmpty()) {
            throw new RuntimeException("订单商品为空，无法插入");
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId);  // 设置订单ID
        }

        try {
            // 插入订单商品
            orderMapper.insertOrderItems(orderItems);
        } catch (Exception e) {
            // 打印详细异常信息
            e.printStackTrace();
            throw new RuntimeException("插入订单商品失败", e);
        }
    }
    @Override
    public Order getOrderDetails(Integer orderId) {

        try {
            // 插入订单商品
            return orderMapper.getOrderDetails(orderId);
        } catch (Exception e) {
            // 打印详细异常信息
            e.printStackTrace();
            throw new RuntimeException("查询订单商品失败", e);
        }

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