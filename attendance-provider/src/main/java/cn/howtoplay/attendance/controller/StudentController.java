package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.common.Payload;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.LeaveInfoVo;
import cn.howtoplay.attendance.domain.vo.StudentAttendancelogsVo;
import cn.howtoplay.attendance.domain.vo.StudentVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.service.AttendanceLogService;
import cn.howtoplay.attendance.service.StudentService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.multipart.MultipartFormData;
import cn.hutool.extra.servlet.multipart.UploadFile;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.transfer.TransferManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoqi on 2019/1/25
 */

@Path("/api/v1/students")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private AttendanceLogService attendanceLogService;

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
            @QueryParam("size") @DefaultValue("10") Integer size) {
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
    public Payload login(@FormParam("studentCode") String studentCode,
                         @FormParam("password") String password,
                         @FormParam("verificationId") String verificationId,
                         @FormParam("code") String code) {
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
    public Payload updatePassword(@CookieParam("token") String token, Map<String, String> body) {
        //TODO 拿到token
        Student student = studentService.findByToken(token);
        if (null == student) {
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
        studentService.updatePassword(student, newPassword);
        return new Payload<>("success");
    }

    @GET
    @Path("/author")
    @NeedToken
    public Payload wxAuthor(@QueryParam("code") String code, @CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        return new Payload(studentService.wxAuthor(student, code));
    }

    @GET
    @Path("/wxLogin")
    public Payload wxLogin(@QueryParam("code") String code) {
        if (StringUtils.isEmpty(code)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "微信授权失败");
        }
        Map<String, Object> map = studentService.wxLogin(code);
        String token = (String) map.get("token");
        //token放cookie
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        return new Payload(map);
    }

    @Path("/openid")
    @PUT
    @NeedToken
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Payload bindOpenId(@FormParam("studentCode") String studentCode,
                              @FormParam("password") String password,
                              @CookieParam("token") String token) {
        if (StringUtils.isEmpty(studentCode) || StringUtils.isEmpty(password)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "参数有误");
        }
        return new Payload(studentService.bindOpenid(studentCode, password, token));
    }

    @GET
    @Path("/detail")
    @NeedToken
    public Payload getStudentDetail(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        return new Payload(student);
    }

    @GET
    @Path("/attendancelogs")
    @NeedToken
    public Payload listAttendancelogs(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        List<StudentAttendancelogsVo> list = studentService.listAttendancelogs(student);
        return new Payload(list);
    }

    @Autowired
    private TransferManager transferManager;

    @Value("${cos.bucketname}")
    private String bucketName;

    @Value("${cos.fileurl}")
    private String fileUrl;

    @POST
    @Path("/logs/qingjia")
    @NeedToken
    public Payload qingJia(@CookieParam("token") String token, LeaveInfoVo info) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        //数据校验
        if (StringUtils.isEmpty(info.getReason())
                || StringUtils.isEmpty(info.getGuardian())
                || StringUtils.isEmpty(info.getGuardianMobile())) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "参数有误");
        }
        //TODO 手机号校验
        attendanceLogService.qingjia(student, info);
        return new Payload("success");
    }

    @GET
    @Path("/logs/qingjia")
    @NeedToken
    public Payload getLogsQingjia(@CookieParam("token") String token) {
        Student student = studentService.findByToken(token);
        if (null == student) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
        }
        List<LeaveInfoVo> list = attendanceLogService.getLogs(student);
        return new Payload(list);
    }

    @POST
    @Path("/images")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Payload uploadImage(@Context HttpServletRequest request) {
        MultipartFormData formData = new MultipartFormData();
        FileOutputStream outputStream = null;
        String imageUrl;
        try {
            formData.parseRequest(request);
            UploadFile img = formData.getFile("img");
            File tempFile = File.createTempFile(img.getFileName(), "");
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(img.getFileContent());
            //保存到cos的图片的文件名，根据这个文件名可以找回图片
            String cosFileName = "img" + IdUtil.fastSimpleUUID();
            //上传图片到cos，多线程异步上传
            transferManager.upload(bucketName, cosFileName, tempFile);
            imageUrl = fileUrl + "/" + cosFileName;
        } catch (IOException e) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "请求解析失败！！！");
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.info("输出流关闭失败！！！！！");
                    throw new ApplicationException(Response.Status.INTERNAL_SERVER_ERROR, "输出流关闭失败！！！");
                }
            }
        }
        return new Payload(imageUrl);
    }
}
