package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer orderId;  // 订单ID
    private Integer itemId;   // 商品ID
    private Integer quantity; // 商品数量
    private Double price;     // 商品价格
    private Double subTotal;  // 小计
}

