package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.domain.enums.AttendanceTypeEnum;
import cn.howtoplay.attendance.domain.enums.LeaveStatusType;
import cn.howtoplay.attendance.domain.eo.AttendanceLog;
import cn.howtoplay.attendance.domain.eo.LeaveInfo;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.LeaveInfoVo;
import cn.howtoplay.attendance.extension.ApplicationException;
import cn.howtoplay.attendance.mapper.AttendancelogMapper;
import cn.howtoplay.attendance.mapper.LeaveInfoMapper;
import cn.howtoplay.attendance.mapper.StudentCourseMapper;
import cn.howtoplay.attendance.service.AttendanceLogService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Autowired
    private LeaveInfoMapper leaveInfoMapper;

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
        String id1 = attendancelogMapper.findByIdAndTypeAndBatchCode(id, AttendanceTypeEnum.ABSENCE, batchCode);
        if (StringUtils.isEmpty(id1)) {
            throw new ApplicationException(Response.Status.BAD_REQUEST, "你已成功签到");
        }
        attendancelogMapper.updateTypeById(id, AttendanceTypeEnum.ONTIME.name, batchCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void qingjia(Student student, LeaveInfoVo info) {
        LeaveInfo leaveInfo = new LeaveInfo();
        leaveInfo.setEndTime(info.getEndTime());
        leaveInfo.setGuardian(info.getGuardian());
        leaveInfo.setGuardianMobile(info.getGuardianMobile());
        leaveInfo.setImgUrl(ArrayUtil.join(ArrayUtil.toArray(info.getImgUrls(), String.class), ","));
        leaveInfo.setStartTime(info.getStartTime());
        leaveInfo.setStudentId(student.getId());
        leaveInfo.setLeaveReason(info.getReason());
        leaveInfo.setId(IdUtil.fastSimpleUUID());
        leaveInfo.setStatus(LeaveStatusType.WAIT.name());
        leaveInfo.setCreatedAt(new Date());
        //TODO 审批人
        leaveInfoMapper.insert(leaveInfo);
        //TODO 发短信
    }

    @Override
    public List<LeaveInfoVo> getLogs(Student student) {
        List<LeaveInfoVo> infoVos = leaveInfoMapper.listByStudentId(student.getId());
        return infoVos;
    }
}
