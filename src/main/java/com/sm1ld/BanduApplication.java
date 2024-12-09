package com.sm1ld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
@MapperScan("com.sm1ld.mapper")
public class BanduApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanduApplication.class, args);
    }

}
