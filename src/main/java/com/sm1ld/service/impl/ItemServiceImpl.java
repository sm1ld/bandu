//ItemServiceImpl.java
package com.sm1ld.service.impl;

import com.sm1ld.mapper.ItemMapper;
import com.sm1ld.pojo.Item;
import com.sm1ld.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

//    /**
//     * 查询可见商品
//     * @return 可见商品列表
//     */
//    @Override
//    public List<Item> listVisibleItems() {
//        return itemMapper.listVisibleItems();
//    }


    /**
     * 商品查询：分页查询商品
     * @param page 页码
     * @param size 每页大小
     * @return 当前页的商品列表
     */
    public List<Item> getItemsByPage(int page, int size) {
        // 计算偏移量
        int offset = (page - 1) * size;
        return itemMapper.listItems(offset, size);
    }

    /**
     * 商品添加
     * @param item 商品添加信息
     */
    @Override
        public void addItem(Item item ) {
        itemMapper.insertItem(item);
    }

    /**
     * 商品修改
     * @param item 商品修改信息
     */
    @Override
    public void updateItem(Item item) {
        itemMapper.update(item);
    }

    @Override
    public Item getItemById(Integer id) {
        return itemMapper.findById(id);
    }

    @Override
    public List<Item> getItemByUid(Integer sellerId) {
        return itemMapper.findByUid(sellerId);
    }

    /**
     * 商品删除：根据id隐藏商品
     * @param id 商品删除id
     */
    @Override
    public void delete(int id) {
        Item item = itemMapper.findById(id);
        if (item != null) {
            item.setIsDelete(true);
            itemMapper.update(item);
        }
    }
}




















