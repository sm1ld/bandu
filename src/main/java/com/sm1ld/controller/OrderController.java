package com.sm1ld.controller;

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
    @GetMapping("/view")
    public Result getOrderByUid(HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        List<Order> orders = orderService.getOrderByUid(userId);
        return Result.success(orders);
    }

    // 创建订单
    @PostMapping("/create")
    public Result createOrder(HttpServletRequest request, @RequestBody Order order) {
        // 获取当前用户的 ID
        Integer userId = JwtUtils.getCurrentUserId(request);
        order.setUserId(userId);
        try {
            System.out.println("----!!!!!!" + order);
            orderService.createOrder(order);

            return Result.success("订单创建成功");
        } catch (Exception e) {
            log.error("订单创建失败: {}", e.getMessage());
            return Result.error("订单创建失败");
        }
    }

    // 更新订单
    @PutMapping("/update")
    public Result updateOrder(@RequestBody Order order, HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        try {
            orderService.updateOrder(order, userId);
            return Result.success("订单更新成功");
        } catch (Exception e) {
            log.error("订单更新失败: {}", e.getMessage());
            return Result.error("订单更新失败");
        }
    }

    // 删除订单
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Integer orderId, HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        try {
            orderService.delOrderByOU(orderId, userId);
            return Result.success("订单删除成功");
        } catch (Exception e) {
            log.error("订单删除失败: {}", e.getMessage());
            return Result.error("订单删除失败");
        }
    }
}



