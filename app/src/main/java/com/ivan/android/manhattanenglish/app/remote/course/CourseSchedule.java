package com.ivan.android.manhattanenglish.app.remote.course;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-8
 * Time: AM10:15
 */
public class CourseSchedule {

    @JSONField(name = "forenoon")
    private boolean atMorning;
    @JSONField(name = "afternoon")
    private boolean atAfternoon;
    @JSONField(name = "aftersix")
    private boolean atNight;

    public boolean isAtMorning() {
        return atMorning;
    }

    public void setAtMorning(boolean atMorning) {
        this.atMorning = atMorning;
    }

    public boolean isAtAfternoon() {
        return atAfternoon;
    }

    public void setAtAfternoon(boolean atAfternoon) {
        this.atAfternoon = atAfternoon;
    }

    public boolean isAtNight() {
        return atNight;
    }

    public void setAtNight(boolean atNight) {
        this.atNight = atNight;
    }
}
