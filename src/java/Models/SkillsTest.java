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
public class SkillsTest {

    private int id;
    private int recruitmentProcessId;
    private String testType;
    private String testName;
    private String testUrl;
    private Timestamp scheduledAt;
    private Timestamp deadline;
    private Timestamp completedAt;
    private String status;
    private int score;
    private String result;
    private String feedback;
    private String testInstructions;

    public SkillsTest() {
    }

    public SkillsTest(int id, int recruitmentProcessId, String testType, String testName, String testUrl, Timestamp scheduledAt, Timestamp deadline, Timestamp completedAt, String status, int score, String result, String feedback, String testInstructions) {
        this.id = id;
        this.recruitmentProcessId = recruitmentProcessId;
        this.testType = testType;
        this.testName = testName;
        this.testUrl = testUrl;
        this.scheduledAt = scheduledAt;
        this.deadline = deadline;
        this.completedAt = completedAt;
        this.status = status;
        this.score = score;
        this.result = result;
        this.feedback = feedback;
        this.testInstructions = testInstructions;
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

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public Timestamp getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Timestamp scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTestInstructions() {
        return testInstructions;
    }

    public void setTestInstructions(String testInstructions) {
        this.testInstructions = testInstructions;
    }

}
