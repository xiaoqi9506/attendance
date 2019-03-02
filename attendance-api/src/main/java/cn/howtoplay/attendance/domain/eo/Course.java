package cn.howtoplay.attendance.domain.eo;

/**
 * 课程实体
 *
 * @author xiaoqi on 2019/1/26
 */
public class Course extends SuperEntity {

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 类型
     */
    private String courseType;

    /**
     * 名称
     */
    private String name;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 课时
     */
    private Integer lesson;

    /**
     * 开课教师
     */
    private String teacherId;

    /**
     * 上课时间，星期几，有个枚举
     */
    private String classTime;

    /**
     * 第几节,如1,2节，逗号分隔
     */
    private String section;

    /**
     * 开始周
     */
    private Integer startWeek;

    /**
     * 结束周
     */
    private Integer endWeek;

    /**
     * 是否有实验课
     */
    private Boolean hasExperCourse;

    /**
     * 实验课代码
     */
    private String experCourseId;

    /**
     * 课程状态
     */
    private String status;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Boolean getHasExperCourse() {
        return hasExperCourse;
    }

    public void setHasExperCourse(Boolean hasExperCourse) {
        this.hasExperCourse = hasExperCourse;
    }

    public String getExperCourseId() {
        return experCourseId;
    }

    public void setExperCourseId(String experCourseId) {
        this.experCourseId = experCourseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
