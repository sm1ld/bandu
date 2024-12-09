package com.sm1ld.controller;

import com.sm1ld.pojo.Cart;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.CartService;
import com.sm1ld.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/view")
    public Result view(HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        List<Cart> cartlist = cartService.getCartByUid(userId);
        return Result.success(cartlist);
    }

    @PostMapping("/{itemId}")
    public Result add(@PathVariable Integer itemId, HttpServletRequest request,@RequestParam("number") Integer number) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        // 获取商品的卖家 ID
        Integer sellerId = cartService.getSellerIdByItemId(itemId);
        // 判断是否是自己的商品
        if (userId.equals(sellerId)) {
            return Result.error("不能将自己的商品加入购物车");
        }
        // 添加商品到购物车，处理重复商品数量加法
        cartService.addCart( userId, itemId,number);

        return Result.success("成功加入购物车");
    }

    @DeleteMapping("/{itemId}")
    public Result delete(@PathVariable Integer itemId, HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        cartService.delCart(userId,itemId);
        return Result.success("成功移出购物车");
    }

    @PutMapping("/{itemId}")
    public Result update(@PathVariable Integer itemId, HttpServletRequest request,@RequestParam("number") Integer number) {
        Integer userId = JwtUtils.getCurrentUserId(request);

        cartService.updateCart(userId,itemId,number);
        return Result.success("成功更新购物车");
    }
}
