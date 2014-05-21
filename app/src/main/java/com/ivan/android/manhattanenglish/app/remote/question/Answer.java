package com.ivan.android.manhattanenglish.app.remote.question;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-20
 * Time: PM5:40
 */
public class Answer {

    private String answerId;

    private String questionId;

    private String teacherName;

    private String teacherAvatar;

    private Date answerTime;

    private String answerContent;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherAvatar() {
        return teacherAvatar;
    }

    public void setTeacherAvatar(String teacherAvatar) {
        this.teacherAvatar = teacherAvatar;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public CharSequence getAnswerTimeString() {
        if (answerTime != null) {
            return DateFormat.format("yyyy-MM-dd HH:mm", answerTime);
        }
        return null;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
