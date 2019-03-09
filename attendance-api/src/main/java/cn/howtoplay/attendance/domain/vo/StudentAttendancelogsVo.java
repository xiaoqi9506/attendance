package cn.howtoplay.attendance.domain.vo;

/**
 * @author xiaoqi on 2019/3/7
 */
public class StudentAttendancelogsVo {

    private String courseId;
    private Integer ontimeTimes;
    private Integer lateTimes;
    private Integer absenceTimes;
    private Integer leaveTimes;

    public Integer getOntimeTimes() {
        return ontimeTimes;
    }

    public void setOntimeTimes(Integer ontimeTimes) {
        this.ontimeTimes = ontimeTimes;
    }

    public Integer getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(Integer lateTimes) {
        this.lateTimes = lateTimes;
    }

    public Integer getAbsenceTimes() {
        return absenceTimes;
    }

    public void setAbsenceTimes(Integer absenceTimes) {
        this.absenceTimes = absenceTimes;
    }

    public Integer getLeaveTimes() {
        return leaveTimes;
    }

    public void setLeaveTimes(Integer leaveTimes) {
        this.leaveTimes = leaveTimes;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
