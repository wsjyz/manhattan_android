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

    private String questionTitle;

    private String questionContent;

    private String questionPic;

    /**
     * 指定回答教师
     */
    private String assignTeacher;

    /**
     * 问题发起人
     */
    private String userId;

    /**
     * 问题回答人
     */
    private String replyUser;

    /**
     * 问题状态
     */
    private String status;

    /**
     * 问题答案
     */
    private String answer;

    private Date answerTime;

    private Date createTime;

    private String answerPic;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public String getAssignTeacher() {
        return assignTeacher;
    }

    public void setAssignTeacher(String assignTeacher) {
        this.assignTeacher = assignTeacher;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CharSequence getCreateTimeString() {
        if (createTime != null) {
            return formatDate(createTime);
        }
        return null;
    }

    private CharSequence formatDate(Date date) {
        return DateFormat.format("yyyy-MM-dd HH:mm", date);
    }

    private CharSequence getAnswerTimeString() {
        if (answerTime != null) {
            return formatDate(answerTime);
        }

        return null;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerPic() {
        return answerPic;
    }

    public void setAnswerPic(String answerPic) {
        this.answerPic = answerPic;
    }
}
