package com.vii.chainnodedemo;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringUtil.class)
public class ChainNodeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChainNodeDemoApplication.class, args);
    }

}
