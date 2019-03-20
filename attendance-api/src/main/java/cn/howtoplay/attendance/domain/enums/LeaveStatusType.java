package cn.howtoplay.attendance.domain.enums;

/**
 * @author xiaoqi on 2019/3/20
 */
public enum LeaveStatusType {

    WAIT("WAIT", "等待审批"),
    APPROVED("APPROVED", "通过"),
    REJECT("REJECT", "驳回");

    String name;
    String desc;

    LeaveStatusType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
