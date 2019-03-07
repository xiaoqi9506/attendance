package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.StudentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoqi on 2019/1/25
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 批量插入学生
     * @param student
     */
    void bulkInsert(@Param("list") List<Student> student);

    List<StudentVo> findList(@Param("name") String name, @Param("studentCode") String studentCode);

    Student selectOneByStudentCode(@Param("studentCode") String studentCode);

    Student selectByOpenId(@Param("openid") String openid);
}
