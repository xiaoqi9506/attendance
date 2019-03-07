package cn.howtoplay.attendance.domain.eo;

/**
 * 学生课程关联表
 *
 * @author xiaoqi on 2019/3/7
 */
public class StudentCource extends SuperEntity {

    /**
     * 学生id
     */
    private String studentId;

    /**
     * 课程id
     */
    private String courceId;

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

    public String getCourceId() {
        return courceId;
    }

    public void setCourceId(String courceId) {
        this.courceId = courceId;
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
}
