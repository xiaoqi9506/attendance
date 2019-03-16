package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.domain.enums.AttendanceTypeEnum;
import cn.howtoplay.attendance.domain.eo.AttendanceLog;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.mapper.AttendancelogMapper;
import cn.howtoplay.attendance.mapper.StudentCourseMapper;
import cn.howtoplay.attendance.service.AttendanceLogService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String start(String courseId) {
        List<String> ids = studentCourseMapper.findIdByCourseId(courseId);
        String code = RandomUtil.randomNumbers(4);
        String pre = DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String batchCode = pre + RandomUtil.randomNumbers(6);
        List<AttendanceLog> logs = new ArrayList<>(ids.size());
        ids.forEach(id->{
            AttendanceLog log = new AttendanceLog();
            log.setId(IdUtil.fastSimpleUUID());
            log.setStudentCourseId(id);
            log.setType(AttendanceTypeEnum.ABSENCE.name);
            log.setCreatedAt(new Date());
            log.setDr(0);
            log.setBatchCode(batchCode);
            logs.add(log);
        });
        RList<String> list = redissonClient.getList("start:code:" + code);
        list.add(courseId);
        list.add(batchCode);
        attendancelogMapper.bulkInsert(logs);
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Student student, String courseId, String batchCode) {
        String id = studentCourseMapper.findIdByStudentIdAndCourseId(student.getId(), courseId);
        attendancelogMapper.updateTypeById(id, AttendanceTypeEnum.ONTIME.name, batchCode);
    }
}
