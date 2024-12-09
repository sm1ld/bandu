package com.sm1ld.service.impl;

import com.sm1ld.mapper.CartMapper;
import com.sm1ld.pojo.Cart;
import com.sm1ld.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> getCartByUid(Integer userId) {
        return cartMapper.findByUid(userId);
    }

    @Override
    public void delCart( Integer userId,Integer itemId) {
        cartMapper.delCartByUI(userId, itemId);
    }

    @Override
    public void updateCart( Integer userId,Integer itemId, Integer number) {
        cartMapper.updateCart(userId,itemId,number);
    }

    @Override
    public Integer getSellerIdByItemId(Integer itemId) {
        return cartMapper.getSellerIdByItemId(itemId);
    }

    @Override
    // 通过用户ID和商品ID查询购物车中该商品的数量
    public Integer getCartItemNumber(Integer userId, Integer itemId) {
        return cartMapper.getCartItemNumber(userId, itemId);
    }

    // 添加商品到购物车
    @Override
    public void addCart(Integer userId, Integer itemId,Integer number0) {
        try {
            // 先检查购物车中是否已有该商品
            Integer existingQuantity = cartMapper.getCartItemNumber(userId, itemId);
            if (existingQuantity != null) {
                // 更新数量
                Integer number = existingQuantity + number0;
                cartMapper.updateCart(userId, itemId,number);
            } else {
                // 插入新记录
                cartMapper.insertCart( userId, itemId,number0);
            }
        } catch (Exception e) {
            // 记录错误信息
            System.out.println("添加商品到购物车时出错"+ e);
            throw new RuntimeException("添加商品到购物车时出错");
        }
    }
}
