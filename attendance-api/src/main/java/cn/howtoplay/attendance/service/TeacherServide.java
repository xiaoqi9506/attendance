package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Teacher;
import cn.howtoplay.attendance.domain.vo.AttendancelogsCount;
import cn.howtoplay.attendance.domain.vo.CourseVo;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/8
 */
public interface TeacherServide {


    Teacher findByToken(String token);

    void updatePassword(Teacher teacher, String newPassword);

    List<CourseVo> getCourses(Teacher teacher);

    List<AttendancelogsCount> getLogsCount(Teacher teacher);
}
