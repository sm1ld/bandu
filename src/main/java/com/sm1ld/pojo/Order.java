package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer orderId;  // 订单ID
    private Integer userId;  // 买家ID，外键
    private Double total;  // 总金额
    private Integer orderStatus;  // 订单状态
    private Integer payStatus;  // 支付状态
    private LocalDateTime sendDate;  // 预计发货时间
    private LocalDateTime sentDate;  // 实际发货时间
    private LocalDateTime createdAt;  // 实际发货时间
    private LocalDateTime updatedAt;  // 实际发货时间
    private List<Item> orderItem= new ArrayList<>();

}

