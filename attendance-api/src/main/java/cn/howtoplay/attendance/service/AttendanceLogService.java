package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.LeaveInfoVo;

import java.util.Date;

/**
 * @author xiaoqi on 2019/3/14
 */
public interface AttendanceLogService {

    String start(String courseId);

    void updateStatus(Student student, String courseId, String batchCode);

    void qingjia(Student student, LeaveInfoVo info);
}
