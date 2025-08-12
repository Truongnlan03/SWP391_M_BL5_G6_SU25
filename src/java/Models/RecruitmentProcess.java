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
public class RecruitmentProcess {

    private int id;
    private int applicationId;
    private String currentStage;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String notes;
    private int assignedHrId;
    private int assignedRecruiterId;

    public RecruitmentProcess() {
    }

    public RecruitmentProcess(int id, int applicationId, String currentStage, String status, Timestamp createdAt, Timestamp updatedAt, String notes, int assignedHrId, int assignedRecruiterId) {
        this.id = id;
        this.applicationId = applicationId;
        this.currentStage = currentStage;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notes = notes;
        this.assignedHrId = assignedHrId;
        this.assignedRecruiterId = assignedRecruiterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAssignedHrId() {
        return assignedHrId;
    }

    public void setAssignedHrId(int assignedHrId) {
        this.assignedHrId = assignedHrId;
    }

    public int getAssignedRecruiterId() {
        return assignedRecruiterId;
    }

    public void setAssignedRecruiterId(int assignedRecruiterId) {
        this.assignedRecruiterId = assignedRecruiterId;
    }

}
