package cn.howtoplay.attendance.domain.eo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 教师
 *
 * @author xiaoqi on 2019/1/26
 */
@TableName("scau_teacher")
public class Teacher extends SuperEntity {

    /**
     * 教师工号，入职年份+4位随机数+4位序号
     * 登录账号
     */
    private String teacherCode;

    /**
     * 登录密码,默认生日,如950620
     */
    @JsonIgnore
    private String password;

    /**
     * 姓名
     */
    private String name;

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

    private String status;

    private Date lastLogin;

    private String openId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
