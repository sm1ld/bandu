package com.sm1ld.controller;

import com.sm1ld.pojo.Comment;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.CommentService;
import com.sm1ld.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 创建评论
     */
    @PostMapping("/create")
    public Result createComment(@RequestBody Comment comment, HttpServletRequest request) {
        Integer userId = JwtUtils.getCurrentUserId(request);
        comment.setUserId(userId);
        System.out.println(comment);
        commentService.createComment(comment);
        return Result.success("评论发布成功");
    }

    /**
     * 获取商品的评论列表，包括评论和回复
     */
    @GetMapping("/item/{itemId}")
    public Result getCommentsByItem(@PathVariable Integer itemId) {
        List<Comment> comments = commentService.getCommentsByItem(itemId);
        return Result.success(comments);
    }

    /**
     * 回复评论
     * 通过评论ID回复评论，商家可以回复评论
     */
    @PostMapping("/reply/{commentId}")
    public Result replyComment(@PathVariable Integer commentId, @RequestParam String content, HttpServletRequest request) {
        Integer sellerId = JwtUtils.getCurrentUserId(request);
        commentService.InsertReply(commentId, sellerId, content);

        return Result.success("回复成功");
    }

    /**
     * 删除评论及其回复
     * 用户可以删除自己的评论，商家可以删除自己的回复
     */
    @DeleteMapping("/{commentId}")
    public Result deleteComment(@PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(commentId);
        return Result.success("删除成功");
    }

    // 删除某条回复
    @DeleteMapping("/reply/{replyId}")
    public Result deleteReply(@PathVariable("replyId") Integer replyId, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request); // 获取当前用户ID


        // 删除回复
        commentService.deleteReply(replyId);

        return Result.success("回复删除成功");
    }
}

