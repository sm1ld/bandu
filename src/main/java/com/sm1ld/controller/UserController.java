// UserController.java
package com.sm1ld.controller;

import com.sm1ld.pojo.Result;
import com.sm1ld.pojo.User;
import com.sm1ld.service.UserService;
import com.sm1ld.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user 用户注册信息
     * @return 用户注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        userService.register(user);
        log.info("User registered successfully");
        return Result.success("用户注册成功");
    }
//    另一个参数@RequestParam String captcha
//        if (!isValidCaptcha(captcha)) {
//            return Result.error("验证码不正确");
//        }
//    private boolean isValidCaptcha(String captcha) {
//        return false;
//    }

    /**
     * 用户登录
     *
     * @param user 用户登录信息
     * @return 用户登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("User:{} login", user);
        User u = userService.login(user);

        if (u == null) {
            throw new RuntimeException("用户名或密码不正确");
        } else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());

            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
    }

    /**
     * 用户查询
     *
     * @param id 用户查询id
     * @return 用户查询结果
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(id)) {
            return Result.error("无权访问该用户信息");
        }
        User user = userService.getUserById(id);
        log.info("Fetching user information for ID: {}", id);
        return Result.success(user);
    }

//    /**
//     * 用户查询及其售卖的商品
//     *
//     * @param id 用户ID
//     * @param request HttpServletRequest 用于验证登录的用户
//     * @return 返回用户及商品列表
//     */
//    @GetMapping("/{id}")
//    public Result getUserWithItems(@PathVariable("id") Integer id, HttpServletRequest request) {
//        // 验证用户身份
//        Integer currentUserId = JwtUtils.getCurrentUserId(request);
//        if (currentUserId == null || !currentUserId.equals(id)) {
//            return Result.error("无权访问该用户信息");
//        }
//        // 调用 Service 获取用户及其售卖的商品
//        User user = userService.getUserWithItems(id);
//        log.info("Fetching user-with-item information for ID: {}", id);
//        // 返回包含用户及商品信息的结果
//        return Result.success(user);
//    }

    /**
     * 用户修改
     *
     * @param id   当前用户id
     * @param user 用户修改信息
     * @return 用户修改结果
     */
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable int id, @RequestBody User user, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(id)) {
            return Result.error("无权修改该用户信息");
        }

        user.setId(id);
        userService.updateUser(user);
        log.info("User updated successfully for ID: {}", id);
        return Result.success("修改成功");
    }

    /**
     * 用户登出
     *
     * @return 用户登出结果
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
            userService.logout(token);
            return Result.success("登出成功");
        }
        return Result.error("未提供有效的令牌");
    }

    /**
     * 用户注销
     *
     * @param id 用户注销id
     * @return 用户注销结果
     */
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable int id, HttpServletRequest request) {
        Integer currentUserId = JwtUtils.getCurrentUserId(request);
        if (currentUserId == null || !currentUserId.equals(id)) {
            return Result.error("无权删除该用户信息");
        }
        userService.deleteUser(id);
        log.info("User deleted successfully for ID: {}", id);
        return Result.success("注销成功");
    }


}



