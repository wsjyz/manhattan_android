package com.ivan.android.manhattanenglish.app.remote.course;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM9:08
 */
public class Course implements Serializable{
    private String courseId;

    private String classNum;

    private String imageUrl;

    private int moneyCost;

    private String location;

    private Date startTime;

    private Date endTime;

    private int peroid;

    private String description;

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

    public int getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(int moneyCost) {
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

    public int getPeroid() {
        return peroid;
    }

    public void setPeroid(int peroid) {
        this.peroid = peroid;
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
}
