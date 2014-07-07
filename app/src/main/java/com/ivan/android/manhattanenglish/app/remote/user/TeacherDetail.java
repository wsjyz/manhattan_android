package com.ivan.android.manhattanenglish.app.remote.user;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.ivan.android.manhattanenglish.app.R;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-8
 * Time: AM9:52
 */
public class TeacherDetail {

    public static final String WAY_STUDENT_VISIT = "STUDENT_VISIT";
    public static final String WAY_TEACHER_VISIT = "TEACHER_VISIT";

    public static final String LEVEL_JUNIOR = "JUNIOR";
    public static final String LEVEL_MIDDLE = "INTERMEDIATE";
    public static final String LEVEL_SENIOR = "SENIOR";

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
     * STUDENT_VISIT
     * TEACHER_VISIT
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

    /**
     * 学员程度
     * 初级JUNIOR 中级INTERMEDIATE 高级SENIOR
     */
    @JSONField(name = "studentLevel")
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

    private Map<String, Integer> extMap;

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

    public String[] getSubjectArray() {
        if (TextUtils.isEmpty(mainSubject)) return null;
        return mainSubject.split(",");
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

    public String[] getLocations() {
        if (TextUtils.isEmpty(availableLocation)) return null;
        return availableLocation.split(",");
    }

    public Set<String> getLocationsForSet() {
        String[] locations = getLocations();
        if (locations == null) return null;
        Set<String> result = new HashSet<String>();
        for (String location : locations) {
            result.add(location);
        }
        return result;
    }

    public String getTeachWay() {
        return teachWay;
    }

    public void setTeachWay(String teachWay) {
        this.teachWay = teachWay;
    }

    public String[] getTeachWayArray() {
        if (TextUtils.isEmpty(teachWay)) return null;
        return teachWay.split(",");
    }



    public int getFocusCount() {
        if (extMap != null && extMap.containsKey("followCount")) {
            focusCount = extMap.get("followCount");
        }
        return focusCount;
    }

    public void setFocusCount(int focusCount) {
        this.focusCount = focusCount;
    }

    public int getCommentCount() {
        if (extMap != null && extMap.containsKey("commentCount")) {
            commentCount = extMap.get("commentCount");
        }
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCollectCount() {
        if (extMap != null && extMap.containsKey("collectCount")) {
            collectCount = extMap.get("collectCount");
        }
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
        return user != null ? user.getSexDrawableResource() : R.drawable.male;
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

    public Map<String, Integer> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Integer> extMap) {
        this.extMap = extMap;
    }
}
