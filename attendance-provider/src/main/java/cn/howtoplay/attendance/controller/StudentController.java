package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.StudentVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author xiaoqi on 2019/1/25
 */

@Path("/api/v1/students")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HttpServletResponse response;

    @Path("/")
    @POST
    public Payload add(Student students) {
        studentService.add(students);
        return new Payload<>("success");
    }

    @Path("/")
    @GET
    public PageInfo studentList(
            @QueryParam("token") String token,
            @QueryParam("name") String name,
            @QueryParam("studentCode") String studentCode,
            @QueryParam("page") @DefaultValue("1") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size)
    {
        //TODO 鉴权
        PageInfo<StudentVo> list = studentService.getStudentList(name, studentCode, page, size);
        return list;
    }

    @Path("/")
    @PUT
    public Payload update() {
        return new Payload<>("success");
    }

    @Path("/")
    @DELETE
    public Payload delete() {
        return new Payload<>("success");
    }

    /**
     * 生成验证码
     */
    @Path("/verification")
    @GET
    public Payload getVerification() {
        return new Payload<>(studentService.getVerification(null));
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Payload login(@FormParam("studentCode")  String studentCode,
                         @FormParam("password")  String password,
                         @FormParam("verificationId") String verificationId,
                         @FormParam("code") String code)
    {
//        //校验验证码
//        RMap<String, String> map = redissonClient.getMap("verifications");
//        if (CollectionUtil.isEmpty(map)) {
//            throw new ApplicationException(Response.Status.BAD_REQUEST, JSONObject.toJSONString(studentService.getVerification(verificationId)));
//        }
//        String redisCode = map.get(verificationId);
//        //如果验证码不匹配
//        if (!code.equals(redisCode)) {
//            //返回新的验证码
//            throw new ApplicationException(Response.Status.BAD_REQUEST, JSONObject.toJSONString(studentService.getVerification(verificationId)));
//        }
//        //清除验证码
//        map.remove(verificationId);
        Map<String, String> result = studentService.login(studentCode, password);
        String token = result.get("token");
        //token放cookie
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        return new Payload<>(result);
    }

    @Path("/tokens/{token}")
    @GET
    public Payload findByToken(@PathParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            return new Payload<>(null, "401", "token失效，请重新登录");
        }
        student.setPassword(null);
        return new Payload<>(student);
    }

    @NeedToken
    @PUT
    @Path("/updatePassword")
    public Payload updatePassword(@CookieParam("token") String token) {
        //TODO 拿到token
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        //密码一致性校验

        //更改密码
        studentService.updatePassword(student, "");
        return new Payload<>("success");
    }
}
