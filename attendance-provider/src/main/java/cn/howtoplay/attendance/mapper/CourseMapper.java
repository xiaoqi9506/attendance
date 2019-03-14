package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.eo.Course;
import cn.howtoplay.attendance.domain.vo.CourseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/14
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> findListByTeacherId(@Param("teacherId") String teacherId);
}
