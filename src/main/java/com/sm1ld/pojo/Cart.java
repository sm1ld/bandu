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

public class Cart {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private Integer sellerId;
    private Integer number;
    private Double subTotal;  // 商品小计
    private String itemTitle;  // 商品名称
    private Double itemPrice; // 商品单价
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Item> items= new ArrayList<>();


}
