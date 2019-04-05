package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.common.AESUtil;
import cn.howtoplay.attendance.common.MD5Util;
import cn.howtoplay.attendance.domain.enums.AttendanceTypeEnum;
import cn.howtoplay.attendance.domain.eo.AttendanceLog;
import cn.howtoplay.attendance.domain.eo.Teacher;
import cn.howtoplay.attendance.domain.vo.AttendancelogsCount;
import cn.howtoplay.attendance.domain.vo.CourseVo;
import cn.howtoplay.attendance.mapper.AttendancelogMapper;
import cn.howtoplay.attendance.mapper.CourseMapper;
import cn.howtoplay.attendance.mapper.StudentCourseMapper;
import cn.howtoplay.attendance.mapper.TeacherMapper;
import cn.howtoplay.attendance.service.TeacherServide;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaoqi on 2019/3/8
 */

@Service
public class TeacherServiceImpl implements TeacherServide {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AttendancelogMapper attendancelogMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public Teacher findByToken(String token) {
        String decryStr = AESUtil.aesDecrypt(token);
        Map map1 = JSONObject.parseObject(decryStr, Map.class);
        String openid = (String) map1.get("openid");
        return teacherMapper.selectByOpenId(openid);
    }

    @Override
    @Transactional
    public void updatePassword(Teacher teacher, String newPassword) {
        teacherMapper.update(teacher, new UpdateWrapper<Teacher>().set("password", MD5Util.generate(newPassword)));
    }

    @Override
    public List<CourseVo> getCourses(Teacher teacher) {
        List<CourseVo> list = courseMapper.findListByTeacherId(teacher.getId());
        return list;
    }

    @Override
    public List<AttendancelogsCount> getLogsCount(Teacher teacher) {
        List<CourseVo> courses = courseMapper.findListByTeacherId(teacher.getId());
        List<AttendancelogsCount> countList = new ArrayList<>();
        courses.forEach(courseVo->{
            List<String> studentList = studentCourseMapper.findIdByCourseId(courseVo.getCourseId());
            List<String> batchCodes = attendancelogMapper.selectBatchCodeOfCourse(studentList);
            List<AttendancelogsCount> countList1 = new ArrayList<>();
            batchCodes.forEach(batchCode->{
                List<AttendanceLog> logList = attendancelogMapper.selectByBatchCode(batchCode);
                double onTimeStudent = logList.stream().filter(log -> AttendanceTypeEnum.ONTIME.name.equals(log.getType())).count();
                double lateTimeStudent = logList.stream().filter(log -> AttendanceTypeEnum.LATE.name.equals(log.getType())).count();
                AttendancelogsCount count = new AttendancelogsCount();
                count.setOnTimeRate(onTimeStudent/logList.size());
                count.setLateRate(lateTimeStudent/logList.size());
                count.setOnTimeStudents(onTimeStudent);
                countList1.add(count);
            });
            double onTimeRate = countList1.stream().mapToDouble(AttendancelogsCount::getOnTimeRate).sum();
            double lateRate = countList1.stream().mapToDouble(AttendancelogsCount::getLateRate).sum();
            double onTimeStudents = countList1.stream().mapToDouble(AttendancelogsCount::getOnTimeStudents).sum();
            AttendancelogsCount count = new AttendancelogsCount();
            count.setLogTimes(batchCodes.size());
            DecimalFormat df = new DecimalFormat("0.0000");
            count.setOnTimeRate(Double.valueOf(df.format(onTimeRate/countList1.size())));
            count.setLateRate(Double.valueOf(df.format(lateRate/countList1.size())));
            count.setOnTimeStudents(Double.valueOf(df.format(onTimeStudents/countList1.size())));
            count.setCourseId(courseVo.getCourseId());
            count.setCourseName(courseVo.getName());
            countList.add(count);
        });
        return countList;
    }
}
