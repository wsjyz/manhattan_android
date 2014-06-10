package com.ivan.android.manhattanenglish.app.remote.user;

import com.ivan.android.manhattanenglish.app.R;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM2:37
 */
public class User {

    public final static String USER_TYPE_TEACHER = "TEACHER";
    public final static String USER_TYPE_STUDENT = "STUDENT";
    public final static String USER_TYPE_VIP_STUDENT = "VIPSTUDENT";

    public final static String SEX_FEMALE = "FEMALE";
    public final static String SEX_MALE = "MALE";

    private String userId;

    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 人员类型
     */
    private String type;

    private String sex;

    private String mobile;

    private String email;

    private String address;

    private Integer credits;

    /**
     * 钱包余额
     */
    private Integer wallet;

    private Boolean vip;

    private Date vipExpiredTime;

    /**
     * 评分
     */
    private int rating;

    /**
     * 评价
     */
    private String evaluation;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getVipExpiredTime() {
        return vipExpiredTime;
    }

    public void setVipExpiredTime(Date vipExpiredTime) {
        this.vipExpiredTime = vipExpiredTime;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getSexDrawableResource() {
        return SEX_FEMALE.equals(sex) ? R.drawable.female : R.drawable.male;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }
}
