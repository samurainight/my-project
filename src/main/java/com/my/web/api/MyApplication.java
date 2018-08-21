package com.my.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.my.web.api")
@ImportResource(value = {"classpath:dubbo-consumer.xml"})
public class MyApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class, args);
    }
}
