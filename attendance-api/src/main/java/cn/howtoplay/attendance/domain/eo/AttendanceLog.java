package cn.howtoplay.attendance.domain.eo;

/**
 * 考勤记录
 *
 * @author xiaoqi on 2019/3/7
 */
public class AttendanceLog extends SuperEntity {

    /**
     * 学生课程关联表id
     */
    private String studentCourseId;

    /**
     * 考勤类型，得搞个枚举
     */
    private String type;

    private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(String studentCourseId) {
        this.studentCourseId = studentCourseId;
    }
}
