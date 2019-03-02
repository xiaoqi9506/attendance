package cn.howtoplay.attendance.common;

import cn.howtoplay.attendance.domain.eo.Student;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author xiaoqi on 2019/1/29
 */
public class CodeUtil {

    public static String studentCode(Student student) {
        int year = DateUtil.thisYear();
        return year + RandomUtil.randomNumbers(4)
                + intToString(student.getClassNum())
                + intToString(student.getNumber());
    }

    private static String intToString(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }
}
