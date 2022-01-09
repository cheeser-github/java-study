package com.pz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pz.test.dao")
public class JavaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaStudyApplication.class, args);
    }

}
