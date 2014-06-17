package com.ivan.android.manhattanenglish.app.remote.info;

import android.text.format.DateFormat;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM4:03
 */
public class Infomation {

    @JSONField(name = "informationid")
    private String id;

    private String title;

    private String content;

    @JSONField(name = "postTime")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeString() {
        return DateFormat.format("yyyy-MM-dd HH:mm", createTime).toString();
    }
}
