package cn.howtoplay.attendance.domain.eo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 学生
 *
 * @author xiaoqi on 2019/1/25
 */
@TableName("scau_student")
public class Student extends SuperEntity{

    /**
     * 学号，入学年份+专业编号（暂时4位随机数）+班级+序号
     * 登录账号
     */
    private String studentCode;

    /**
     * 登录密码,身份证后6位,如950620
     */
    @JsonIgnore
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，男/女
     */
    private String gender;

    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 手机号，可选
     */
    private String mobile;

    /**
     * 身份证,md5加密存进去
     */
    private String idCard;

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
     * 最后登录时间,为null时第一次登录
     */
    private Date lastLogin;

    /**
     * 学院代码
     */
    private String code;

    /**
     * 班级
     */
    private Integer classNum;

    /**
     * 序号
     */
    private Integer number;

    /**
     * 记录状态，备用
     */
    private String status = "ACTIVE";

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
