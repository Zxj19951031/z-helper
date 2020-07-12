package org.zipper.demo.zdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
        "org.zipper.demo.zdemo",
        "org.zipper.helper.swagger"
})
public class ZDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZDemoApplication.class, args);
    }

}
