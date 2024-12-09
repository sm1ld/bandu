package com.sm1ld.service.impl;


import com.sm1ld.mapper.CommentMapper;
import com.sm1ld.pojo.Comment;
import com.sm1ld.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void createComment(Comment comment) {
        // 执行评论插入操作
        commentMapper.insertComment(comment);
    }

    @Override
    public Comment getCommentsByItem(Integer itemId) {
        return commentMapper.findByItemId(itemId);
    }

    @Override
    public Comment getCommentsByUser(Integer userId) {
        return commentMapper.findByUserId(userId);
    }
}
