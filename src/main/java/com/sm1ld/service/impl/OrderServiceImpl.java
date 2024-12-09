package com.sm1ld.service.impl;

import com.sm1ld.mapper.CartMapper;
import com.sm1ld.mapper.OrderItemMapper;
import com.sm1ld.mapper.OrderMapper;
import com.sm1ld.pojo.Cart;
import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.OrderItem;
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
    public void createOrder(Integer userId) {
        // 1. 获取购物车商品列表
        List<Cart> cartItems = cartMapper.findByUid(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空，无法创建订单");
        }

        // 2. 计算总金额
        double total = cartItems.stream().mapToDouble(Cart::getSubTotal).sum();

        // 3. 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(total);
        orderMapper.insertOrder(order);  // 插入订单并获取生成的 orderId

        // 4. 为每个购物车商品创建对应的订单项
        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setItemId(cartItem.getItemId());
            orderItem.setNumber(cartItem.getNumber());
            orderItem.setSubTotal(cartItem.getSubTotal());
            orderItemMapper.insertOrderItem(orderItem);
        }

        // 5. 删除购物车中的商品
        cartItems.forEach(cartItem -> cartMapper.delCartById(cartItem.getId()));
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