package com.ydhlw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ydhlw.pojo.dao.mapper")
public class YdhlwApplication {

    public static void main(String[] args) {
        SpringApplication.run(YdhlwApplication.class, args);
    }

}
