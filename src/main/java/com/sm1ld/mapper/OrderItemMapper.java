package com.sm1ld.mapper;

import com.sm1ld.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO orderitem (orderId, itemId, number, subTotal,createdAt,updatedAt) " +
            "VALUES (#{orderId}, #{itemId}, #{number}, #{subTotal},now(),now())")
    void insertOrderItem(OrderItem orderItem);

    @Select("SELECT * FROM orderitem WHERE orderId = #{orderId}")
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
}
