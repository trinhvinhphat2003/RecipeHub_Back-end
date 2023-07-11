package com.example.RecipeHub.dtos.request;

import java.util.Date;

public class MealPlannerDayRangeRequest {
    private Date afterDate;
    private Date beforeDate;
    private long userId;

    public MealPlannerDayRangeRequest() {
    }

    public MealPlannerDayRangeRequest(Date afterDate, Date beforeDate, long userId) {
        this.afterDate = afterDate;
        this.beforeDate = beforeDate;
        this.userId = userId;
    }

    public Date getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    public Date getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(Date beforeDate) {
        this.beforeDate = beforeDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
