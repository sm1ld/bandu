package com.sm1ld.mapper;

import com.sm1ld.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    /**
     * 插入评论
     */
    @Insert("INSERT INTO comment(itemId, adminId, userId, content, rating, createdAt) " +
            "VALUES(#{itemId}, #{adminId}, #{userId}, #{content}, #{rating}, now())")
    void insertComment(Comment comment);

    /**
     * 根据商品ID获取评论
     */
    @Select("SELECT * FROM comment WHERE itemId = #{itemId}")
    Comment findByItemId(Integer itemId);

    /**
     * 根据用户ID获取评论
     */
    @Select("SELECT * FROM comment WHERE userId = #{userId}")
    Comment findByUserId(Integer userId);
}
