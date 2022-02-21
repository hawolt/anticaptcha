package com.hawolt.task;

import org.json.JSONObject;

public class CaptchaResult {

    public static final CaptchaResult FAILURE = new CaptchaResult();

    private double cost;
    private String solution;
    private long createdAt, solvedAt;
    private int solveCount, errorId;
    private String ip, status;
    private boolean isFailure;

    public CaptchaResult() {
        isFailure = true;
    }

    public CaptchaResult(JSONObject tmp) {
        this.solution = tmp.getJSONObject("solution").getString("gRecaptchaResponse");
        this.createdAt = tmp.getLong("createTime");
        this.solveCount = tmp.getInt("solveCount");
        this.solvedAt = tmp.getLong("endTime");
        this.status = tmp.getString("status");
        this.errorId = tmp.getInt("errorId");
        this.cost = tmp.getDouble("cost");
        this.ip = tmp.getString("ip");
    }

    public double getCost() {
        return cost;
    }

    public String getSolution() {
        return solution;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getSolvedAt() {
        return solvedAt;
    }

    public int getSolveCount() {
        return solveCount;
    }

    public int getErrorId() {
        return errorId;
    }

    public String getIp() {
        return ip;
    }

    public String getStatus() {
        return status;
    }

    public boolean isFailure() {
        return isFailure;
    }
}
