package cn.howtoplay.attendance.domain.eo;

/**
 * 学生课程关联表
 *
 * @author xiaoqi on 2019/3/7
 */
public class StudentCourse extends SuperEntity {

    /**
     * 学生id
     */
    private String studentId;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 记录状态
     */
    private String status;

    /**
     * 课程成绩
     */
    private Integer score;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
