package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Integer id;             // 回复ID
    private Integer commentId;      // 所属评论ID
    private Integer sellerId;       // 商家ID
    private String content;         // 回复内容
    private LocalDateTime createdAt; // 回复时间

}