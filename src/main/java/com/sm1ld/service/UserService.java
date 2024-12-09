// UserService.java
package com.sm1ld.service;



import com.sm1ld.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void register(User user);

    User login(User user);

    void logout(String token);

    void deleteUser(int id);

    User getUserById(int id);

    void updateUser(User user);

    User getUserWithItems(Integer userId);


}


