package com.ivan.android.manhattanenglish.app.remote.purchase;

import android.text.format.DateFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: AM11:20
 */
public class PurchaseHistory {

    private String historyId;

    private BigDecimal cost;

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

    public CharSequence getConsumeTimeString() {
        if (consumeTime != null) {
            return DateFormat.format("yyyy-MM-dd HH:mm", consumeTime);
        }

        return null;
    }
}
