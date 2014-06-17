package com.ivan.android.manhattanenglish.app.remote.course;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-17
 * Time: AM11:28
 */
public class QueryParam {
    String courseCategory;
    String place;
    String sex;
    Date appointmentTime;
    String tutoringWay;

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTutoringWay() {
        return tutoringWay;
    }

    public void setTutoringWay(String tutoringWay) {
        this.tutoringWay = tutoringWay;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

}
