package com.sm1ld.service.impl;

import com.sm1ld.mapper.AdminMapper;
import com.sm1ld.pojo.Admin;
import com.sm1ld.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void login(Admin admin) {
        adminMapper.GetByIdPw(admin);
    }
}
