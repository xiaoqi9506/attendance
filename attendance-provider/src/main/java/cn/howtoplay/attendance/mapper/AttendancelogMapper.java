package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.eo.AttendanceLog;
import cn.howtoplay.attendance.domain.vo.AttendanceLogsVo;
import cn.howtoplay.attendance.domain.vo.CourseScoreVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/11
 */
public interface AttendancelogMapper extends BaseMapper<AttendanceLog> {

    List<CourseScoreVo> countByIds(@Param("ids") List<String> ids);

    List<AttendanceLogsVo> findByStudentIdAndCourseId(@Param("courseId") String courseId);

    void bulkInsert(@Param("logs") List<AttendanceLog> logs);

    void updateTypeById(@Param("id") String id, @Param("type") String type, @Param("batchCode") String batchCode);
}
