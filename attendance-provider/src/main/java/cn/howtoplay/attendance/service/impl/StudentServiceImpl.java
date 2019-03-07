package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.common.AESUtil;
import cn.howtoplay.attendance.common.CodeUtil;
import cn.howtoplay.attendance.common.MD5Util;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.StudentVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.mapper.StudentMapper;
import cn.howtoplay.attendance.service.StudentService;
import cn.howtoplay.attendance.service.WxService;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoqi on 2019/1/25
 */

@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private WxService wxService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Student student) {
        //数据校验？
        student.setId(IdUtil.fastSimpleUUID());
        String password = DateUtil.format(student.getBirthday(), "yyyyMMdd").substring(2, 8);
        student.setPassword(MD5Util.generate(password));
        student.setStudentCode(CodeUtil.studentCode(student));
        student.setIdCard(MD5Util.generate(student.getIdCard()));
        studentMapper.insert(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAll(List<Student> students) {
        //现在student里有firstName,lastName,gender,birthday,idCard,classNum,number
        for (Student student : students) {
            student.setId(IdUtil.fastSimpleUUID());
            String password = DateUtil.format(student.getBirthday(), "yyyyMMdd").substring(2, 8);
            student.setPassword(MD5Util.generate(password));
            student.setStudentCode(CodeUtil.studentCode(student));
            student.setIdCard(MD5Util.generate(student.getIdCard()));
            studentMapper.insert(student);
        }
//        studentMapper.bulkInsert(students);
    }

    @Override
    public PageInfo<StudentVo> getStudentList(String name, String studentCode, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<StudentVo> list = studentMapper.findList(name, studentCode);
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, String> getVerification(String verificationId) {
        //生成验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 37);
        String code = captcha.getImageBase64();
        logger.info("验证码：{}", captcha.getCode());
        //保存到redis
        if (null == verificationId) {
            verificationId = IdUtil.fastSimpleUUID();
        }
        logger.info("保存到redis缓存===>key；{}, value：{}", verificationId, captcha.getCode());
        RMap<String, String> map = redissonClient.getMap("verifications");
        map.put(verificationId, captcha.getCode());
        Map<String, String> resault = new HashMap<>();
        resault.put("verificationId", verificationId);
        resault.put("code", code);
        return resault;
    }

    @Override
    public Map<String, String> login(String studentCode, String password) {
        //根据学号获取学生信息
        Student student = studentMapper.selectOneByStudentCode(studentCode);
        if (null == student || !MD5Util.verify(password, student.getPassword())) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "学号或密码错误!");
        }
        //生成token
        String token = IdUtil.fastSimpleUUID();
        RMap<String, String> tokens = redissonClient.getMap("students:tokens");
        RMap<String, String> onlines = redissonClient.getMap("students:onlines");

        //判断是否已经登录,如果已经登录，删除redis缓存，重新保存token
        if (null != onlines && null != onlines.get(studentCode)) {
            tokens.remove(onlines.get(studentCode));
        }

        logger.info("token保存到缓存====>student_code：{}, token：{}", studentCode, token);
        tokens.put(token, student.getStudentCode());
        onlines.put(studentCode, token);

        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        //是否第一次登录
        boolean firstLogin = false;
        if (null == student.getLastLogin()) {
            firstLogin = true;
        }
        student.setLastLogin(new Date());
        studentMapper.updateById(student);

        result.put("firstLogin", String.valueOf(firstLogin));
        return result;
    }

    @Override
    public Student findByToken(String token) {
//        RMap<String, String> map = redissonClient.getMap("students:tokens");
//        if (null == map || null == map.get(token)) {
//            return null;
//        }
//        String redisStr = map.get(token);
//        if (StringUtils.isEmpty(redisStr)) {
//            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
//        }
        String decryStr = AESUtil.aesDecrypt(token);
        Map map1 = JSONObject.parseObject(decryStr, Map.class);
        String openid = (String) map1.get("openid");
        return studentMapper.selectByOpenId(openid);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Student student, String newPassword) {
        studentMapper.update(student, new UpdateWrapper<Student>().set("password", MD5Util.generate(newPassword)));
    }

    @Override
    public String wxAuthor(Student student, String code) {
        Map<String, Object> map = wxService.wxLogin(code);
        String openid = (String) map.get("openid");
        if (StringUtils.isEmpty(openid)) {
            throw new ApplicationException(Response.Status.INTERNAL_SERVER_ERROR, "获取授权失败");
        }
        studentMapper.update(student, new UpdateWrapper<Student>().set("open_id", MD5Util.generate(openid)));
        return "success";
    }

    @Override
    @Transactional
    public Map<String, Object> wxLogin(String code) {
        Map<String, Object> map = wxService.wxLogin(code);
        String openid = (String) map.get("openid");
        String sessionKey = (String) map.get("session_key");
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(sessionKey)) {
            throw new ApplicationException(Response.Status.INTERNAL_SERVER_ERROR, "获取授权失败");
        }
        //生成token
        logger.info(JSONObject.toJSONString(map));

        String token = AESUtil.aesEncrypt(JSONObject.toJSONString(map));
//        RMap<String, String> tokens = redissonClient.getMap("students:tokens");
//        RMap<String, String> onlines = redissonClient.getMap("students:onlines");
//
//        //判断是否已经登录,如果已经登录，删除redis缓存，重新保存token
//        if (null != onlines && null != onlines.get(openid)) {
//            tokens.remove(onlines.get(openid));
//        }
//
//        logger.info("token保存到缓存====>student_code：{}, token：{}", openid, token);
//        tokens.put(token, student.getStudentCode());
//        onlines.put(student.getStudentCode(), token);

        Student student = studentMapper.selectByOpenId(openid);
        boolean binded = true;
        if (null == student) {
            binded = false;
        }else {
            student.setLastLogin(new Date());
            studentMapper.updateById(student);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("binded", binded);
        return result;
    }

    @Override
    @Transactional
    public String bindOpenid(String studentCode, String password, String token) {
        //根据学号获取学生信息
        Student student = studentMapper.selectOneByStudentCode(studentCode);
        if (null == student || !MD5Util.verify(password, student.getPassword())) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "学号或密码错误!");
        }
//        RMap<String, String> map = redissonClient.getMap("students:tokens");
//        if (null == map || null == map.get(token)) {
//            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
//        }
//        String redisStr = map.get(token);
//        if (StringUtils.isEmpty(redisStr)) {
//            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效，请重新登录");
//        }
        String decryStr = AESUtil.aesDecrypt(token);
        Map map1 = JSONObject.parseObject(decryStr, Map.class);
        String openid = (String) map1.get("openid");
        student.setOpenId(openid);
        studentMapper.updateById(student);
        return "success";
    }
}
