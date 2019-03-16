package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.AttendanceLogService;
import cn.howtoplay.attendance.service.StudentService;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author xiaoqi on 2019/3/7
 */

@Path("/api/v1/attendancelogs")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttendanceLogController {

    @Autowired
    private AttendanceLogService attendanceLogService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedissonClient redissonClient;

    @GET
    @Path("/")
    public Payload list() {
        return null;
    }

    @POST
    @Path("/")
    public Payload start(@QueryParam("courseId") String courseId) {
        String code = attendanceLogService.start(courseId);
        return new Payload(code);
    }

    @PUT
    @Path("/")
    @NeedToken
    public Payload update(@CookieParam("token") String token, @QueryParam("code") String code) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        RList<String> list = redissonClient.getList("start:code:" + code);
        if (CollectionUtil.isEmpty(list) || list.size() < 2) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "code无效，签到失败");
        }
        String courseId = list.get(0);
        String batchCode = list.get(1);
        if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(batchCode)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "code无效，签到失败");
        }
        attendanceLogService.updateStatus(student, courseId, batchCode);
        return new Payload(null);
    }
}
