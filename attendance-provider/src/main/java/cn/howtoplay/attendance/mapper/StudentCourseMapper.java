package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.vo.CourseScoreVo;
import cn.howtoplay.attendance.domain.vo.StudentAttendancelogsVo;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/7
 */
public interface StudentCourseMapper {

    List<StudentAttendancelogsVo> findLogs(String studentId);

    List<CourseScoreVo> findScoresByStudentId(String studentId);
}
