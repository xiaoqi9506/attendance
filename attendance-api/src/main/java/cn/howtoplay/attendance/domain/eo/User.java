package cn.howtoplay.attendance.domain.eo;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author xiaoqi on 2019/3/24
 */

@TableName("scau_user")
public class User extends SuperEntity{

    private String userCode;

    private String password;

    private String type;

    private Integer classNum;

    private Integer academyNum;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public Integer getAcademyNum() {
        return academyNum;
    }

    public void setAcademyNum(Integer academyNum) {
        this.academyNum = academyNum;
    }
}
