package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.AttendanceLogsVo;
import cn.howtoplay.attendance.domain.vo.CourseScoreVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.CourseService;
import cn.howtoplay.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author xiaoqi on 2019/3/9
 */

@Path("/api/v1/courses")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CourseController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @POST
    @Path("/")
    @NeedToken
    public Payload addCourse(@CookieParam("token") String token) {
        return new Payload(null);
    }

    /**
     * 学生查看成绩
     * @param token
     * @return
     */
    @GET
    @Path("/score")
    @NeedToken
    public Payload getScore(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        List<CourseScoreVo> scoreVos = courseService.getScoreListWithStudent(student);
        return new Payload(scoreVos);
    }

    @GET
    @Path("/logs")
    @NeedToken
    public Payload getLogs(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        return new Payload(courseService.getLogs(student));
    }

    @GET
    @Path("/{id: [a-zA-Z0-9]+}/logs/detail")
    @NeedToken
    public Payload getLogsDetail(@CookieParam("token") String token, @PathParam("id") String id) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        List<AttendanceLogsVo> logsDetail = courseService.getLogsDetail(student, id);
        return new Payload(logsDetail);
    }
}
