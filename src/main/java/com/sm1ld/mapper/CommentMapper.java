package com.sm1ld.mapper;

import com.sm1ld.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 插入评论
     */
    @Insert("INSERT INTO comment(itemId, content, userId, createdAt) " +
            "VALUES(#{itemId}, #{content}, #{userId}, now())")
    void insertComment(Comment comment);

    /**
     * 根据商品ID获取评论
     */
    List<Comment> findByItemId(Integer itemId);

    // 插入回复
    @Insert("INSERT INTO reply (commentId, sellerId, content, createdAt) " +
            "VALUES (#{commentId}, #{sellerId}, #{content}, now())")
    void insertReply(@Param("commentId") Integer commentId, @Param("sellerId") Integer sellerId, @Param("content") String content);


    @Delete("DELETE FROM comment WHERE id = #{commentId}")
    void deleteComment(Integer commentId);

    @Delete("DELETE FROM reply WHERE id = #{replyId}")
    void deleteReply(Integer replyId);
}
