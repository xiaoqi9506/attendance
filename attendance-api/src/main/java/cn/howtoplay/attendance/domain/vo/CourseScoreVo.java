package cn.howtoplay.attendance.domain.vo;

/**
 * @author xiaoqi on 2019/3/10
 */
public class CourseScoreVo {

    private String id;
    private String courseId;
    private String courseName;
    private Double score;
    private String courseCode;
    private Integer ontimeTimes = 0;
    private Integer lateTimes = 0;
    private Integer absenceTimes = 0;
    private Integer leaveTimes = 0;
    private String type;
    private Integer logCount;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLogCount() {
        return logCount;
    }

    public void setLogCount(Integer logCount) {
        this.logCount = logCount;
    }
}
