package com.sm1ld.controller;

import com.sm1ld.pojo.Comment;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.CommentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 创建评论
     */
    @PostMapping
    public Result createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return Result.success("评论发布成功");
    }

    /**
     * 获取商品的评论列表
     */
    @GetMapping("/item/{itemId}")
    public Result getCommentsByItem(@PathVariable Integer itemId) {
        Comment comment = commentService.getCommentsByItem(itemId);
        return Result.success("获取评论成功"+comment);
    }

    /**
     * 获取用户的所有评论
     */
    @GetMapping("/user/{userId}")
    public Result getCommentsByUser(@PathVariable Integer userId) {
        Comment comment = commentService.getCommentsByUser(userId);
        return Result.success("获取评论成功"+comment);
    }
}
