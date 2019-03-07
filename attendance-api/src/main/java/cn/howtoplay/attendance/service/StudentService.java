package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Student;
import cn.howtoplay.attendance.domain.vo.StudentVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 学生rpc接口
 *
 * @author xiaoqi on 2019/1/25
 */
public interface StudentService {

    void addAll(List<Student> students);

    PageInfo<StudentVo> getStudentList(String name, String studentCode, Integer page, Integer size);

    Map<String, String> getVerification(String verificationId);

    Map<String, String> login(String studentCode, String password);

    Student findByToken(String token);

    void add(Student students);

    void updatePassword(Student student, String newPassword);

    String wxAuthor(Student student, String code);

    Map<String, Object> wxLogin(String code);

    String bindOpenid(String studentCode, String password, String token);
}
