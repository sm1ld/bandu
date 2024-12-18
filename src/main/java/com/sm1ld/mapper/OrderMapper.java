package com.sm1ld.mapper;

import com.sm1ld.pojo.Order;
import com.sm1ld.pojo.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 根据用户ID查询订单
    List<Order> getOrderByUid(@Param("userId") int userId);
    // 插入订单
    void insertOrder(Order order);

    // 插入订单详情
    void insertOrderItems(List<OrderItem> orderItems);

    Order getOrderDetails(Integer orderId);

    // 根据订单ID删除订单
    @Delete("DELETE FROM allorder WHERE orderId = #{orderId} AND userId=#{“userId}")
    void delOrderByOU(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    // 更新订单信息
    @Update("UPDATE allorder SET orderStatus = #{orderStatus}, payStatus = #{payStatus}, " +
            " total = #{total}, sendDate = #{sendDate}, sentDate = #{sentDate} WHERE orderId = #{orderId} AND userId=#{“userId}")
    void updateOrder(Order order, @Param("userId") Integer userId);

}
