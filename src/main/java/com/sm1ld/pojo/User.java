package com.sm1ld.pojo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码至少需要8个字符")
    private String password;
    private String address;

    private String phoneNumber;
    @Email(message = "邮箱格式不正确")
    private String email;
    private Integer role = 0;  // 默认0为买家，1为卖家
    private List<Item> items= new ArrayList<>();
    private LocalDateTime createdAt;// 数据库导入
    private LocalDateTime updatedAt;// 数据库导入

}
