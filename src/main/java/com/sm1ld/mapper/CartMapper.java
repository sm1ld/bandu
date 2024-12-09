package com.sm1ld.mapper;

import com.sm1ld.pojo.Cart;
import com.sm1ld.pojo.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    // 根据用户ID查询购物车
    List<Cart> findByUid(@Param("userId") Integer userId);

    // 删除购物车中的商品
    @Delete("DELETE FROM cart WHERE userId = #{userId} AND itemId = #{itemId}")
    void delCart(@Param("userId") Integer userId, @Param("itemId") Integer itemId);

    // 更新购物车商品数量
    @Update("UPDATE cart SET number = #{number}, updatedAt = NOW() WHERE userId = #{userId} AND itemId = #{itemId}")
    void updateCart(@Param("userId") Integer userId,
                    @Param("itemId") Integer itemId,
                    @Param("number") Integer number);

    // 根据商品ID查询卖家ID
    Integer getSellerIdByItemId(@Param("itemId") Integer itemId);

    // 根据用户ID和商品ID查询购物车中的商品数量
    Integer getCartItemNumber(@Param("userId") Integer userId,
                              @Param("itemId") Integer itemId);

    // 插入新的购物车项
    void insertCart(@Param("userId") int userId,
                    @Param("itemId") int itemId,
                    @Param("number") int number);
}
