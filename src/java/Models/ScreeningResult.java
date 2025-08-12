/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;

/**
 *
 * @author DELL
 */
public class ScreeningResult {

    private int id;
    private int recruitmentProcessId;
    private String screeningType;
    private String result;
    private int score;
    private String feedback;
    private String reviewerName;
    private Timestamp reviewedAt;
    private String criteria;

    public ScreeningResult() {
    }

    public ScreeningResult(int id, int recruitmentProcessId, String screeningType, String result, int score, String feedback, String reviewerName, Timestamp reviewedAt, String criteria) {
        this.id = id;
        this.recruitmentProcessId = recruitmentProcessId;
        this.screeningType = screeningType;
        this.result = result;
        this.score = score;
        this.feedback = feedback;
        this.reviewerName = reviewerName;
        this.reviewedAt = reviewedAt;
        this.criteria = criteria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecruitmentProcessId() {
        return recruitmentProcessId;
    }

    public void setRecruitmentProcessId(int recruitmentProcessId) {
        this.recruitmentProcessId = recruitmentProcessId;
    }

    public String getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(String screeningType) {
        this.screeningType = screeningType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Timestamp getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Timestamp reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

}
