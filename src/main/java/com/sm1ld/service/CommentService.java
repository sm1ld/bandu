package com.sm1ld.service;

import com.sm1ld.pojo.Comment;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CommentService {
    void createComment(Comment comment);

    Comment getCommentsByItem(Integer itemId);

    Comment getCommentsByUser(Integer userId);
}
