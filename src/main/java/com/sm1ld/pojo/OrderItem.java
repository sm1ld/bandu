package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;  // 订单ID
    private Integer itemId;   // 商品ID
    private Integer number; // 商品数量
    private Double subTotal;  // 小计
    private LocalDateTime createdDate;  // 实际发货时间
    private LocalDateTime updatedDate;  // 实际发货时间
}

