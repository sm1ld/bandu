package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Item {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity; // 新增字段，表示商品数量
    private Integer categoryId;
    private Integer sellerId;
    private String imageUrl;
    private Short status; // 使用枚举类型
    private LocalDateTime createdAt;// 数据库导入
    private LocalDateTime updatedAt;// 数据库导入
    private Boolean isDelete; // 新增字段，表示商品是否可见
    private Double subTotal;  // 小计，动态计算

}