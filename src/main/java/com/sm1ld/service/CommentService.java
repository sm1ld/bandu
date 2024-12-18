package com.sm1ld.service;

import com.sm1ld.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    // 创建评论
    void createComment(Comment comment);

    void InsertReply(Integer commentId, Integer sellerId, String content);

    // 获取商品的所有评论
    List<Comment> getCommentsByItem(Integer itemId);

    // 删除评论
    void deleteComment(Integer commentId);

    void deleteReply(Integer replyId);
}
