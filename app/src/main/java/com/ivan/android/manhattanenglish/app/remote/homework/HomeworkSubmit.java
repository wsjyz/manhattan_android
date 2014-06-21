package com.ivan.android.manhattanenglish.app.remote.homework;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-21
 * Time: PM12:39
 */
public class HomeworkSubmit {
    private String homeworkSubmitId;// 作业提交Id
    private String homeworkId;// 作业Id
    private String userId;// 学员ID
    private String submitFile; // 作业文件
    private Date submitTime;// 提交时间
    private String homeworkContent;

    public String getHomeworkSubmitId() {
        return homeworkSubmitId;
    }

    public void setHomeworkSubmitId(String homeworkSubmitId) {
        this.homeworkSubmitId = homeworkSubmitId;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubmitFile() {
        return submitFile;
    }

    public void setSubmitFile(String submitFile) {
        this.submitFile = submitFile;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }
}
