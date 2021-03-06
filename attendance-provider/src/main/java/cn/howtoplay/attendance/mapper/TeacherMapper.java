package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.eo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaoqi on 2019/3/1
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Teacher selectByOpenId(@Param("openid") String openid);

    Teacher selectByTeacherCode(@Param("username") String username);
}
