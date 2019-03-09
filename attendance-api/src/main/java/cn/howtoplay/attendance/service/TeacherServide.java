package cn.howtoplay.attendance.service;

import cn.howtoplay.attendance.domain.eo.Teacher;

/**
 * @author xiaoqi on 2019/3/8
 */
public interface TeacherServide {


    Teacher findByToken(String token);

    void updatePassword(Teacher teacher, String newPassword);
}
