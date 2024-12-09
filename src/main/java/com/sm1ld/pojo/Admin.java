package com.sm1ld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Admin {
    private Long id;
    private String adminName;
    private String password;
}
