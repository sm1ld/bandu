package com.sm1ld.mapper;

import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 根据用户ID查询订单
    List<Order> getOrderByUid(@Param("userId") int userId);

    // 插入订单
    void insertOrder(@Param("order") Order order);

    // 插入订单商品
    void insertOrderItems(@Param("orderItems") List<OrderItem> orderItems);

    // 根据订单ID查询订单详细信息（订单 + 商品）
    @Select("SELECT * FROM allorder WHERE orderId = #{orderId}")
    Order selectOrderById(@Param("orderId") Long orderId);

    // 根据订单ID删除订单
    @Delete("DELETE FROM allorder WHERE orderId = #{orderId}")
    void deleteOrderById(@Param("orderId") Long orderId);

    // 更新订单信息
    @Update("UPDATE allorder SET orderStatus = #{orderStatus}, payStatus = #{payStatus}, " +
            " total = #{total}, sendDate = #{sendDate}, sentDate = #{sentDate} WHERE orderId = #{orderId}")
    void updateOrder(@Param("order") Order order);

    // 查询所有订单
    @Select("SELECT * FROM allorder")
    List<Order> selectAllOrders();
}
