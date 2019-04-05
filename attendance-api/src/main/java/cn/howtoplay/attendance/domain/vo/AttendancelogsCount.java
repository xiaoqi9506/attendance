package cn.howtoplay.attendance.domain.vo;

/**
 * @author xiaoqi on 2019/4/5
 */
public class AttendancelogsCount {

    private String courseId;
    private String courseName;
    private Integer logTimes;
    private Double onTimeRate;
    private Double lateRate;
    private Double onTimeStudents;

    public Integer getLogTimes() {
        return logTimes;
    }

    public void setLogTimes(Integer logTimes) {
        this.logTimes = logTimes;
    }

    public Double getOnTimeRate() {
        return onTimeRate;
    }

    public void setOnTimeRate(Double onTimeRate) {
        this.onTimeRate = onTimeRate;
    }

    public Double getLateRate() {
        return lateRate;
    }

    public void setLateRate(Double lateRate) {
        this.lateRate = lateRate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getOnTimeStudents() {
        return onTimeStudents;
    }

    public void setOnTimeStudents(Double onTimeStudents) {
        this.onTimeStudents = onTimeStudents;
    }
}
