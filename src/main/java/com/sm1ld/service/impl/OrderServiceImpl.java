package com.sm1ld.service.impl;

import com.sm1ld.mapper.OrderMapper;
import com.sm1ld.pojo.Item;
import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.OrderItem;
import com.sm1ld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void createOrder(Order order, List<Item> orderItem) {
        // 插入订单
        orderMapper.insertOrder(order);

        // 获取生成的 orderId
        Integer orderId = order.getOrderId();

        // 创建 OrderItem 列表
        List<OrderItem> orderItemList = new ArrayList<>();

        // 转换 Item 为 OrderItem，并设置 orderId
        for (Item item : orderItem) {
            OrderItem orderItem0 = new OrderItem();
            orderItem0.setOrderId(orderId);  // 设置 orderId
            orderItem0.setItemId(item.getId());  // 设置商品ID
            orderItem0.setQuantity(item.getQuantity());  // 设置商品数量
            orderItem0.setPrice(item.getPrice());  // 设置商品价格
            orderItem0.setSubTotal(item.getQuantity() * item.getPrice());  // 计算小计

            orderItemList.add(orderItem0);
        }

        // 插入订单商品
        orderMapper.insertOrderItems(orderItemList);
    }

    @Override
    public Order getOrderDetails(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderMapper.deleteOrderById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAllOrders();
    }


}
