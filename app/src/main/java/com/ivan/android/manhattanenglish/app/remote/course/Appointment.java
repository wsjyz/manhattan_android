package com.ivan.android.manhattanenglish.app.remote.course;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-23
 * Time: PM1:28
 */
public class Appointment {

    public static final String RESOURCE_TYPE_LISTEN_COURSE = "LISTEN_COURSE";
    public static final String RESOURCE_TYPE_APPOINTMENT_COURSE = "APPOINTMENT_COURSE";
    public static final String RESOURCE_TYPE_LISTEN_TEACHER = "LISTEN_TEACHER";
    public static final String RESOURCE_TYPE_APPOINTMENT_TEACHER = "APPOINTMENT_TEACHER";

    public static final String PAYMENT_ONLINE = "ONLINE";
    public static final String PAYMENT_ADMIN = "ADMIN";


    private String userId;

    private String userName;

    private String courseCategory;

    private String tutoringWay;
    /**
     * 授课地区
     */
    private String area;
    private String mobile;
    private String address;
    /**
     * 引用资源类型
     * 试听课程 LISTEN_COURSE
     * 预约课程APPOINTMENT_COURSE
     * 试听教师LISTEN_TEACHER
     * 预约教师APPOINTMENT_TEACHER
     */
    private String resourceType;

    private String resourceId;
    /**
     * 付款方式网上付款ONLINE；后台付款ADMIN
     */
    private String payment;

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

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getTutoringWay() {
        return tutoringWay;
    }

    public void setTutoringWay(String tutoringWay) {
        this.tutoringWay = tutoringWay;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
