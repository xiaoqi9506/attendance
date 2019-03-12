package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.domain.enums.AttendanceTypeEnum;
import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.AttendanceLogsVo;
import cn.howtoplay.attendance.domain.vo.CourseScoreVo;
import cn.howtoplay.attendance.mapper.AttendancelogMapper;
import cn.howtoplay.attendance.mapper.StudentCourseMapper;
import cn.howtoplay.attendance.service.CourseService;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoqi on 2019/3/9
 */

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private AttendancelogMapper attendancelogMapper;

    @Override
    public List<CourseScoreVo> getScoreListWithStudent(Student student) {
        List<CourseScoreVo> scores = studentCourseMapper.findScoresByStudentId(student.getId());
        List<CourseScoreVo> collect = scores.stream().filter(score -> score.getScore() != null).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect)) {
            List<String> ids = collect.stream().map(CourseScoreVo::getId).collect(Collectors.toList());
            countByIds(ids, collect);
        }
        return collect;
    }

    @Override
    public List<CourseScoreVo> getLogs(Student student) {
        List<CourseScoreVo> logs = studentCourseMapper.findScoresByStudentId(student.getId());
        List<String> ids = logs.stream().map(CourseScoreVo::getId).collect(Collectors.toList());
        countByIds(ids, logs);
        return logs;
    }

    @Override
    public List<AttendanceLogsVo> getLogsDetail(Student student, String id) {
        List<AttendanceLogsVo> list = attendancelogMapper.findByStudentIdAndCourseId(id);
        return list;
    }

    private void countByIds(List<String> ids, List<CourseScoreVo> source) {
        List<CourseScoreVo> courseScoreVos = attendancelogMapper.countByIds(ids);
        courseScoreVos.forEach(courseScoreVo -> {
            switch (AttendanceTypeEnum.valueOf(courseScoreVo.getType())) {
                case ONTIME :
                    source.stream().filter(score->score.getId().equals(courseScoreVo.getId()))
                            .forEach(score->score.setOntimeTimes(courseScoreVo.getLogCount()));
                    break;
                case LATE :
                    source.stream().filter(score->score.getId().equals(courseScoreVo.getId()))
                            .forEach(score->score.setLateTimes(courseScoreVo.getLogCount()));
                    break;
                case ABSENCE :
                    source.stream().filter(score->score.getId().equals(courseScoreVo.getId()))
                            .forEach(score->score.setAbsenceTimes(courseScoreVo.getLogCount()));
                    break;
                case LEAVE :
                    source.stream().filter(score->score.getId().equals(courseScoreVo.getId()))
                            .forEach(score->score.setLeaveTimes(courseScoreVo.getLogCount()));
                    break;
                default:
            }
        });
    }
}
