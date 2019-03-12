package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.AttendanceLogsVo;
import cn.howtoplay.attendance.domain.vo.CourseScoreVo;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/9
 */
public interface CourseService {

    List<CourseScoreVo> getScoreListWithStudent(Student student);

    List<CourseScoreVo> getLogs(Student student);

    List<AttendanceLogsVo> getLogsDetail(Student student, String id);
}
