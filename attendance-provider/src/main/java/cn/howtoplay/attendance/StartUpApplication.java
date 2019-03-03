package cn.howtoplay.attendance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author xiaoqi
 */
@SpringBootApplication
public class StartUpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StartUpApplication.class, args);
    }

}
