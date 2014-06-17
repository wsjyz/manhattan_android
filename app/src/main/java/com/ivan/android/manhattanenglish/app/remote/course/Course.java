package com.ivan.android.manhattanenglish.app.remote.course;

import android.text.format.DateFormat;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM9:08
 */
public class Course implements Serializable{
    private String courseId;

    private String courseTitle;

    private String courseSubtitle;

    private String classNum;

    @JSONField(name = "coursePic")
    private String imageUrl;

    @JSONField(name = "expense")
    private BigDecimal moneyCost;

    /**
     * 开课地点
     */
    @JSONField(name = "place")
    private String location;

    private Date startTime;

    private Date endTime;

    private BigDecimal period;

    @JSONField(name = "courseIntro")
    private String description;

    /**
     * 课程分类
     */
    private String courseCategory;

    /**
     * 教师Id，用逗号分隔
     */
    private String teachers;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public BigDecimal getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(BigDecimal moneyCost) {
        this.moneyCost = moneyCost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseSubtitle() {
        return courseSubtitle;
    }

    public void setCourseSubtitle(String courseSubtitle) {
        this.courseSubtitle = courseSubtitle;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartTimeString() {
        return DateFormat.format("yyyy-MM-dd", startTime).toString();
    }

    public String getEndTimeString() {
        return DateFormat.format("yyyy-MM-dd", endTime).toString();
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
}
