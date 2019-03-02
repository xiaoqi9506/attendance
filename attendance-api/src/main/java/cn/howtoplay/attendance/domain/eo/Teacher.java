package cn.howtoplay.attendance.domain.eo;

import java.util.Date;

/**
 * 教师
 *
 * @author xiaoqi on 2019/1/26
 */
public class Teacher extends SuperEntity {

    /**
     * 教师工号，入职年份+4位随机数+4位序号
     * 登录账号
     */
    private String teacherCode;

    /**
     * 登录密码,默认生日,如950620
     */
    private String password;

    /**
     * 姓
     */
    private String firstName;

    /**
     * 名
     */
    private String lastName;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别，男/女
     */
    private String gender;

    /**
     * 手机号，可选
     */
    private String mobile;

    /**
     * 归属地，省
     */
    private String province;

    /**
     * 归属地，市
     */
    private String city;

    /**
     * 归属地，区，县
     */
    private String district;

    /**
     * 教师职称
     */
    private String grade;

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
