//ItemMapper.java
package com.sm1ld.mapper;

import com.sm1ld.pojo.Item;
import java.util.List;
import org.apache.ibatis.annotations.*;


@Mapper
public interface ItemMapper {
//    /**
//     * 查询可显示的数据
//     */
//    @Select("SELECT * FROM item WHERE is_delete = 0")
//    List<Item> listVisibleItems();
    /**
     * 商品查询：分页显示商品信息
     * @param offset 查询的起始位置（偏移量）
     * @param limit 查询的数量（每页显示的数量）
     * @return 商品列表
     */
    @Select("SELECT * FROM item WHERE isDelete = 0 LIMIT #{limit} OFFSET #{offset}")
    List<Item> listItems(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 商品查询：根据id查询商品
     * @param id 商品ID
     * @return 商品查找信息
     */
    @Select("SELECT * FROM item WHERE id = #{id}")
    Item findById(int id);


    List<Item>  findByUid(Integer sellerId);
    /**
     * 商品添加：将商品添加信息插入数据库
     * @param item 商品添加信息
     */
    @Insert("INSERT INTO item (title, description, price, quantity, categoryId, sellerId, imageUrl, createdAt, updatedAt) " +
            "VALUES (#{title}, #{description}, #{price},#{quantity}, #{categoryId}, #{sellerId}, #{imageUrl}, now(), now())")
    void insertItem(Item item);

    /**
     * 商品修改：根据id将商品修改信息更新到数据库
     * @param item 商品更新信息
     */
    @Update("UPDATE item SET title = #{title}, description = #{description}, price = #{price},quantity = #{quantity},, " +
            "categoryId = #{categoryId}, sellerId = #{sellerId}, imageUrl = #{imageUrl}, " +
            "status = #{status}, updatedAt = NOW() ,isDelete = #{isDelete} WHERE id = #{id}")
    void update(Item item);


}

//    // 批量删除商品数据
//    public void deleteByIds(List<Integer> ids);

//    // 动态修改商品数据
//    @Update("update products set" +
//           "title=#{title},description=#{description},price=#{price},category_id=#{categoryId},seller_id=#{sellerId}," +
//           "image_url=#{imageUrl},condition#{condition},update_at=#{updateAt}) where id = #{id}" )
//    public void update(Item item);
