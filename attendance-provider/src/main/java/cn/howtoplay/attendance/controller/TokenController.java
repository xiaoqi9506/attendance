package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.eo.Teacher;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.StudentService;
import cn.howtoplay.attendance.service.TeacherServide;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author xiaoqi on 2019/3/8
 */

@Path("/api/v1/token")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TokenController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherServide teacherServide;

    @GET
    @Path("/")
    @NeedToken
    public Payload getLoginType(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null != student) {
            return new Payload("students");
        }
        Teacher teacher = teacherServide.findByToken(token);
        if (null != teacher) {
            return new Payload("teachers");
        }
        return null;
    }

    @NeedToken
    @PUT
    @Path("/updatePassword")
    public Payload updatePassword(@CookieParam("token") String token, Map<String, String> body) {
        Student student = studentService.findByToken(token);
        Teacher teacher = teacherServide.findByToken(token);
        if (null == student && null == teacher) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        //密码一致性校验
        String newPassword = body.get("newPassword");
        String confirmPassword = body.get("confirmPassword");
        if (StringUtils.isEmpty(confirmPassword) || StringUtils.isEmpty(newPassword)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "输入不能为空");
        }
        if (!confirmPassword.equals(newPassword)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "参数有误");
        }
        //更改密码
        if (null != student) {
            studentService.updatePassword(student, newPassword);
        }else {
            teacherServide.updatePassword(teacher, newPassword);
        }
        return new Payload<>("success");
    }
}
