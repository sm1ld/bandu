//UserServiceImpl.java
package com.sm1ld.service.impl;

import com.sm1ld.mapper.UserMapper;
import com.sm1ld.pojo.User;
import com.sm1ld.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param user 用户注册信息
     */
    @Override
    public void register(User user) {
        // 唯一性检查
        checkUnique(user);
        userMapper.insertUser(user);
//        sendActivationEmail(user);
    }
//    private void sendActivationEmail(User user) {
//    }
    /**
     * 用户注册检查：检查用户注册信息的唯一性约束
     * @param user 用户检查信息
     */
    private void checkUnique(User user) {
        // 使用 findByParams 方法简化唯一性检查
        if (user.getUsername() != null && userMapper.findByParams(user.getUsername(), null, null) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getPhoneNumber() != null && userMapper.findByParams(null, user.getPhoneNumber(), null) != null) {
            throw new RuntimeException("电话号码已存在");
        }
        if (user.getEmail() != null && userMapper.findByParams(null, null, user.getEmail()) != null) {
            throw new RuntimeException("邮箱已存在");
        }
    }

    /**
     * 用户登录
     * @param user 用户登录信息
     * @return 用户信息
     */
    @Override
    public User login(User user) {
        return userMapper.getByNmPw(user);
    }

    /**
     * 用户查询
     * @param id 用户查询id
     * @return 用户查询信息
     */
    @Override
    public User getUserById(int id) {
        return userMapper.findById(id);
    }

    // 查询用户信息及其商品
    @Override
    public User getUserWithItems(Integer userId) {
        // 通过联合查询获取用户和商品信息
        return userMapper.findUserWithItems(userId);
    }
    /**
     * 用户修改
     * @param user 用户修改信息
     */
    @Override
    public void updateUser(User user) {
        // 唯一性检查
        checkUniqueExId(user);
        // 执行更新操作
        userMapper.updateUser(user);
    }
    /**
     * 用户修改检查：检查用户修改信息的唯一性约束
     * @param user 用户修改信息
     */
    private void checkUniqueExId(User user) {
        // 使用 findByParamsExcludingId 方法简化唯一性检查
        if (user.getUsername() != null && userMapper.findByParamsExId(user.getUsername(), null, null, user.getId()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getPhoneNumber() != null && userMapper.findByParamsExId(null, user.getPhoneNumber(), null, user.getId()) != null) {
            throw new RuntimeException("电话号码已存在");
        }
        if (user.getEmail() != null && userMapper.findByParamsExId(null, null, user.getEmail(), user.getId()) != null) {
            throw new RuntimeException("邮箱已存在");
        }
    }

    /**
     * 用户登出
     * @param token 当前jwt
     */
    @Override
    public void logout(String token) {
        log.info("User logged out and token invalidated: {}", token);
        // 若有黑名单机制，可在这里添加 token 到黑名单
    }

    /**
     * 用户注销
     * @param id 用户注销id
     */
    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }




}




