// ItemController.java
package com.sm1ld.controller;

import com.sm1ld.pojo.Item;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.ItemService;
import com.sm1ld.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
//    /**
//     * 查询可视的全部商品
//     * @return 包含所有可视商品的列表
//     */
//    @GetMapping
//    public Result getAllItems() {
//        List<Item> itemList = itemService.listVisibleItems();
//        log.info("Fetching all visible items");
//        return Result.success(itemList);
//    }

    /**
     * 商品查询(分页)
     * @param page 页码，默认为第 1 页
     * @param size 每页大小，默认为 20
     * @return 商品查询结果
     */
    @GetMapping("/list")
    public Result getPaginatedItems(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        List<Item> itemList = itemService.getItemsByPage(page, size);
        return Result.success(itemList);
    }

    /**
     * 获取商品详细信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/{id}")
    public Result getItemById(@PathVariable("id") Integer id) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return Result.error("商品不存在");
        }
        log.info("Fetching item information for ID: {}", id);
        return Result.success(item);
    }

    /**
     * 获取商品详细信息
     *
     * @return 商品信息
     */
    @GetMapping("/user/{sellerId}")
    public Result getItemByUid(@PathVariable("sellerId")Integer sellerId) {
        List<Item> items = itemService.getItemByUid(sellerId);

        // 判断商品列表是否为空
        if (items == null || items.isEmpty()) {
            return Result.error("商品不存在");
        }
        // 记录日志，显示获取的商品信息
        log.info("Fetching item information for sellerId: {}", sellerId);
        // 返回成功的结果，包含商品信息
        return Result.success(items);
    }

    /**
     * 商品添加
     * @param item 商品添加信息
     * @return Result 商品添加结果
     */
    @PostMapping("/add")
    public Result addItem(@RequestBody Item item, HttpServletRequest request) {
        // 获取当前用户的ID
        Integer sellerId = JwtUtils.getCurrentUserId(request);
        // 设置商品的卖家ID
        item.setSellerId(sellerId);
        log.info("Adding new item: {}", item.getTitle());
        // 调用服务层进行商品添加操作
        itemService.addItem(item);
        return Result.success("商品添加成功");
    }

    /**
     * 商品修改
     * @param id 商品修改id
     * @param item 商品修改信息
     * @return Result 商品修改结果
     */
    @PutMapping("/{id}")
    public Result updateItem(@PathVariable int id, @RequestBody Item item) {
        log.info("Updating item with id {}: {}", id, item.getTitle());
        item.setId(id); // 确保设置要更新的商品ID
        itemService.updateItem(item); // 调用服务层更新方法
        return Result.success("商品修改成功");
    }

    /**
     * 商品删除
     * @param id 商品删除id
     * @return Result 商品删除结果
     */
    @DeleteMapping("/{id}")
    public Result deleteItem(@PathVariable int id) {
        log.info("Hiding item with id {}", id);
        itemService.delete(id);
        return Result.success("商品删除成功");
    }
}




















//    @GetMapping("/items")
////    跨源访问@CrossOrigin
//    public Result getItems() {
//        List<Item>itemList = itemService.list();
//        log.info("Fetching all items");
//        return Result.success(itemList);
//    }

//    @DeleteMapping("/{id}")
//    public Result deleteItem(@PathVariable int id) {
//        log.info("Deleting item with id {}", id);
//        itemService.delete(id);
//        return Result.success();
//    }