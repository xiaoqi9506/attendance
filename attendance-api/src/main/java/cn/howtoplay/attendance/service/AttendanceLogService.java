package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Student;

import java.util.Date;

/**
 * @author xiaoqi on 2019/3/14
 */
public interface AttendanceLogService {

    String start(String courseId);

    void updateStatus(Student student, String courseId, String batchCode);

    void qingjia(Student student, Date startTime, Date endTime, String reason, String guardian, String guardianMobile, String url);
}
