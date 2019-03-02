package cn.howtoplay.attendance.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("cn.howtoplay.attendance.controller",
                "cn.howtoplay.attendance.filter",
                "cn.howtoplay.attendance.extension");
    }

}