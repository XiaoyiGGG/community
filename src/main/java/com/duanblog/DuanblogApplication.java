package com.duanblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.duanblog.mapper")
@SpringBootApplication
public class DuanblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuanblogApplication.class, args);
    }
}
