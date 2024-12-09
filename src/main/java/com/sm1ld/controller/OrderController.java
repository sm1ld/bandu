package com.sm1ld.controller;

import com.sm1ld.pojo.Item;
import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.OrderService;
import com.sm1ld.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 获取当前用户的订单
    @GetMapping("/{userId}")
    public Result getOrderByUserId(@PathVariable int userId, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return Result.error("无权查看该用户的订单");
        }

        List<Order> orders = orderService.getOrderByUid(userId);
        return Result.success(orders);
    }

    // 创建订单
    @PostMapping("/{userId}")
    public Result createOrder(@RequestBody Order order,@RequestBody List<Item> orderItem, @PathVariable Integer userId, HttpServletRequest request) {
        // 获取当前用户的 ID
        Integer currentUserId = JwtUtils.getCurrentUserId(request);

        // 确认当前用户是否为请求中的用户
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return Result.error("无权创建该用户订单");
        }

        try {
            // 设置订单的买家ID为当前用户ID
            order.setUserId(currentUserId);
            orderService.createOrder(order,orderItem);
            return Result.success("订单创建成功");
        } catch (Exception e) {
            log.error("订单创建失败: {}", e.getMessage());
            return Result.error("订单创建失败");
        }
    }



    // 更新订单
    @PutMapping("/{userId}/{orderId}")
    public Result updateOrder(@PathVariable Integer userId, @PathVariable Integer orderId, @RequestBody Order order, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return Result.error("无权更新该用户订单");
        }

        order.setOrderId(orderId);
        try {
            orderService.updateOrder(order);
            return Result.success("订单更新成功");
        } catch (Exception e) {
            log.error("订单更新失败: {}", e.getMessage());
            return Result.error("订单更新失败");
        }
    }

    // 删除订单
    @DeleteMapping("/{userId}/{orderId}")
    public Result deleteOrder(@PathVariable Integer userId, @PathVariable Long orderId, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return Result.error("无权删除该用户订单");
        }

        try {
            orderService.deleteOrder(orderId);
            return Result.success("订单删除成功");
        } catch (Exception e) {
            log.error("订单删除失败: {}", e.getMessage());
            return Result.error("订单删除失败");
        }
    }
}


//    // 获取所有订单
//    @GetMapping
//    public Result getAllOrders() {
//        List<Order> orders = orderService.getAllOrders();
//        return Result.success(orders);
//    }

//    // 获取订单详情
//    @GetMapping("/{orderId}")
//    public Result getOrderDetails(@PathVariable Long orderId) {
//        Order order = orderService.getOrderDetails(orderId);
//        if (order != null) {
//            return Result.success(order);
//        } else {
//            return Result.error(null);
//        }
//    }


