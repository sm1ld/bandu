package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;        // 评论ID
    private Integer itemId;    // 商品ID
    private Integer adminId;    // 卖家ID
    private Integer userId;    // 买家ID
    private String content;     // 评论内容
    private Integer rating;     // 评论星级
    private LocalDateTime createdAt;// 数据库导入

}
