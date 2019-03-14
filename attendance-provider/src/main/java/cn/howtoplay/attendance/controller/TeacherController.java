package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Teacher;
import cn.howtoplay.attendance.domain.vo.CourseVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.TeacherServide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author xiaoqi on 2019/3/9
 */

@Path("/api/v1/teachers")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TeacherController {

    @Autowired
    private TeacherServide teacherServide;

    @GET
    @Path("/detail")
    public Payload getDetail(@CookieParam("token") String token) {
        Teacher teacher = teacherServide.findByToken(token);
        if (null == teacher) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        return new Payload(teacher);
    }

    @GET
    @Path("/courses")
    @NeedToken
    public Payload getCourses(@CookieParam("token") String token) {
        Teacher teacher = teacherServide.findByToken(token);
        if (null == teacher) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        List<CourseVo> list = teacherServide.getCourses(teacher);
        return new Payload(list);
    }
}
