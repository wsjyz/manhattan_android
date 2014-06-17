package com.ivan.android.manhattanenglish.app.remote.purchase;

import android.text.format.DateFormat;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: AM11:20
 */
public class PurchaseHistory {

    @JSONField(name = "walletId")
    private String historyId;

    @JSONField(name = "money")
    private BigDecimal cost;

    private String payStatus;

    @JSONField(name = "optTime")
    private Date consumeTime;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public CharSequence getConsumeTimeString() {
        if (consumeTime != null) {
            return DateFormat.format("yyyy-MM-dd HH:mm", consumeTime);
        }

        return null;
    }
}
