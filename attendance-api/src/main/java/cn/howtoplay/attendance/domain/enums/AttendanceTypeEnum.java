package cn.howtoplay.attendance.domain.enums;

/**
 * @author xiaoqi on 2019/3/7
 */
public enum AttendanceTypeEnum {

    ONTIME("ONTIME", "准时签到"),
    LATE("LATE", "迟到"),
    ABSENCE("ABSENCE", "缺席"),
    LEAVE("LEAVE", "请假");

    String name;
    String desc;

    AttendanceTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
