package com.ivan.android.manhattanenglish.app.remote.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.ivan.android.manhattanenglish.app.remote.user.User;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-8
 * Time: AM9:52
 */
public class TeacherDetail {

    @JSONField(name = "userId")
    private String teacherId;

    @JSONField(name = "finalGraduateSchool")
    private String college;

    @JSONField(name = "specialty")
    private String mainSubject;

    /**
     * 评分
     */
    private int rating;

    @JSONField(name = "teachingArea")
    private String availableLocation;

    /**
     * 辅导方式
     */
    @JSONField(name = "tutoringWay")
    private String teachWay;

    private int focusCount;

    private int commentCount;

    private int collectCount;

    /**
     * 自我介绍
     */
    @JSONField(name = "selfIntroduction")
    private String introduction;

    private String requiredLevel;

    /**
     * classFees
     */
    @JSONField(name = "classFees")
    private String cost;

    /**
     * 授课时间
     */
    private String teachingTime;

    /**
     * 教师的用户基本信息
     */
    private User user;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return user == null ? null : user.getUserName();
    }

    public String getSex() {
        return user == null ? User.SEX_MALE : user.getSex();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMainSubject() {
        return mainSubject;
    }

    public void setMainSubject(String mainSubject) {
        this.mainSubject = mainSubject;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEvaluation() {
        return user == null ? null : user.getEvaluation();
    }

    public String getAvailableLocation() {
        return availableLocation;
    }

    public void setAvailableLocation(String availableLocation) {
        this.availableLocation = availableLocation;
    }

    public String getTeachWay() {
        return teachWay;
    }

    public void setTeachWay(String teachWay) {
        this.teachWay = teachWay;
    }

    public int getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(int focusCount) {
        this.focusCount = focusCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(String requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAvatarUrl() {
        return user == null ? null : user.getAvatar();
    }

    public int getSexDrawableResource() {
        return user.getSexDrawableResource();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTeachingTime() {
        return teachingTime;
    }

    public void setTeachingTime(String teachingTime) {
        this.teachingTime = teachingTime;
    }
}
