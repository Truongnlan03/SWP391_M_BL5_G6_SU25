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
public class JobAlert {

    private int id;
    private int jobSeekerId;
    private String alertName;
    private String keyword;
    private String location;
    private String industry;
    private String jobType;
    private Double minSalary;
    private Double maxSalary;
    private String frequency;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp lastSent;

    public JobAlert() {
    }

    public JobAlert(int id, int jobSeekerId, String alertName, String keyword, String location, String industry, String jobType, Double minSalary, Double maxSalary, String frequency, Boolean isActive, Timestamp createdAt, Timestamp lastSent) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.alertName = alertName;
        this.keyword = keyword;
        this.location = location;
        this.industry = industry;
        this.jobType = jobType;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.frequency = frequency;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.lastSent = lastSent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastSent() {
        return lastSent;
    }

    public void setLastSent(Timestamp lastSent) {
        this.lastSent = lastSent;
    }

}
