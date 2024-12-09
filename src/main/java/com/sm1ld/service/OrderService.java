package com.sm1ld.service;

import com.sm1ld.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> getOrderByUid(int userId);

    // 创建订单
    void createOrder(Integer userId);

    // 查询订单详情
    Order getOrderDetails(Integer orderId);

    // 更新订单信息
    void updateOrder(Order order, Integer userId);

    void delOrderByOU(Integer orderId, Integer userId);
//
}
// 删除订单
//    void deleteOrder(Long orderId);
//
//    // 查询所有订单
//    List<Order> getAllOrders();