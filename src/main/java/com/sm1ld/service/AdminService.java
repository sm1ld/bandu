// AdminService.java
package com.sm1ld.service;

import com.sm1ld.pojo.Admin;
import org.springframework.stereotype.Service;


@Service
public interface AdminService {

    void login(Admin admin);
}
