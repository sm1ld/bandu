package com.sm1ld.service;

import com.sm1ld.pojo.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    List<Cart> getCartByUid(Integer userId);

    void addCart( Integer userId,Integer itemId,Integer number);

    void delCart(Integer userId,Integer itemId );

    void updateCart( Integer userId,Integer itemId,Integer number);

    Integer getSellerIdByItemId(Integer itemId);

    Integer getCartItemNumber(Integer userId, Integer itemId);
}
