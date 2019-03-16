package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.vo.CourseScoreVo;
import cn.howtoplay.attendance.domain.vo.StudentAttendancelogsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/7
 */
public interface StudentCourseMapper {

    List<StudentAttendancelogsVo> findLogs(String studentId);

    List<CourseScoreVo> findScoresByStudentId(String studentId);

    List<String> findIdByCourseId(String courseId);

    String findIdByStudentIdAndCourseId(@Param("studentId") String studentId, @Param("courseId") String courseId);
}
