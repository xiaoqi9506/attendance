package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.common.AESUtil;
import cn.howtoplay.attendance.common.MD5Util;
import cn.howtoplay.attendance.domain.eo.Teacher;
import cn.howtoplay.attendance.mapper.TeacherMapper;
import cn.howtoplay.attendance.service.TeacherServide;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xiaoqi on 2019/3/8
 */

@Service
public class TeacherServiceImpl implements TeacherServide {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher findByToken(String token) {
        String decryStr = AESUtil.aesDecrypt(token);
        Map map1 = JSONObject.parseObject(decryStr, Map.class);
        String openid = (String) map1.get("openid");
        return teacherMapper.selectByOpenId(openid);
    }

    @Override
    public void updatePassword(Teacher teacher, String newPassword) {
        teacherMapper.update(teacher, new UpdateWrapper<Teacher>().set("password", MD5Util.generate(newPassword)));
    }
}
