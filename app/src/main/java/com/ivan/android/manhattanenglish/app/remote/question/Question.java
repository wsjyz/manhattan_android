package com.ivan.android.manhattanenglish.app.remote.question;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-15
 * Time: PM3:18
 */
public class Question {
    private String questionId;

    private String title;

    private String content;

    private String imageUrl;

    private String specifyTeacherId;

    /**
     * 问题发起人
     */
    private String createBy;

    private Date createTime;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpecifyTeacherId() {
        return specifyTeacherId;
    }

    public void setSpecifyTeacherId(String specifyTeacherId) {
        this.specifyTeacherId = specifyTeacherId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CharSequence getCreateTimeString() {
        if (createTime != null) {
            return DateFormat.format("yyyy-MM-dd HH:mm", createTime);
        }
        return null;
    }
}
