package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.domain.enums.AttendanceTypeEnum;
import cn.howtoplay.attendance.domain.eo.AttendanceLog;
import cn.howtoplay.attendance.mapper.AttendancelogMapper;
import cn.howtoplay.attendance.mapper.StudentCourseMapper;
import cn.howtoplay.attendance.service.AttendanceLogService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoqi on 2019/3/14
 */

@Service
public class AttendanceLogServiceImpl implements AttendanceLogService {

    @Autowired
    private AttendancelogMapper attendancelogMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String start(String courseId) {
        List<String> ids = studentCourseMapper.findIdByCourcseId(courseId);
        String code = RandomUtil.randomNumbers(4);
        List<AttendanceLog> logs = new ArrayList<>(ids.size());
        ids.forEach(id->{
            AttendanceLog log = new AttendanceLog();
            log.setId(IdUtil.fastSimpleUUID());
            log.setStudentCourseId(id);
            log.setType(AttendanceTypeEnum.ABSENCE.name);
            log.setCreatedAt(new Date());
            log.setDr(0);
            log.setBatchCode(code);
            logs.add(log);
        });
        attendancelogMapper.bulkInsert(logs);
        return code;
    }
}
