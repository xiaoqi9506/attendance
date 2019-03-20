package cn.howtoplay.attendance.mapper;

import cn.howtoplay.attendance.domain.eo.LeaveInfo;
import cn.howtoplay.attendance.domain.vo.LeaveInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoqi on 2019/3/19
 */
public interface LeaveInfoMapper extends BaseMapper<LeaveInfo> {

    List<LeaveInfoVo> listByStudentId(@Param("studentId") String studentId);
}
