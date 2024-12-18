package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;             // 评论ID
    private Integer itemId;         // 商品ID
    private String content;         // 评论内容
    private Integer userId;         // 评论者ID
    private LocalDateTime createdAt; // 评论时间
    private List<Reply> replies;    // 评论的多个回复

}


