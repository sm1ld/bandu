package com.sm1ld.controller;

import com.sm1ld.pojo.Admin;
import com.sm1ld.pojo.Result;
import com.sm1ld.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * @param admin 管理员登录信息
     * @return 管理员登录结果
     */
    @PostMapping("/login")
    public Result login(Admin admin) {
        adminService.login(admin);
        log.info("Admin:{} login success", admin);
        return Result.success("登录成功");
    }


}
