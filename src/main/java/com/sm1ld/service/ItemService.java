// ItemService.java
package com.sm1ld.service;


import com.sm1ld.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ItemService {
//    List<Item> listVisibleItems();

    List<Item> getItemsByPage(int page, int size);

    void delete(@Param("id") int id);

    void addItem(Item item);

    void updateItem(Item item);

    Item getItemById(Integer id);

    List<Item>  getItemByUid(Integer sellerId);
}