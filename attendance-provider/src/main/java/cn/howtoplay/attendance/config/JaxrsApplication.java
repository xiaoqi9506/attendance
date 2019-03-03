package cn.howtoplay.attendance.config;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/")
public class JaxrsApplication extends Application {

//    public JaxrsApplication() {
//        System.out.println("开始扫描包");
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion("1.0.0");
//        beanConfig.setSchemes(new String[]{"http"});
//        beanConfig.setResourcePackage("cn.howtoplay.attendance.controller");
//        beanConfig.setResourcePackage("cn.howtoplay.attendance.extension");
//        beanConfig.setResourcePackage("cn.howtoplay.attendance.filter");
//        beanConfig.setScan(true);
//    }
//
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet();
//        resources.add(ApiListingResource.class);
//        resources.add(SwaggerSerializers.class);
//        return resources;
//    }
}