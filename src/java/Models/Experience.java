/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class Experience {

    private int id;
    private int jobSeekerId;
    private String position;
    private String companyName;
    private String companyLogo;
    private String location;
    private Date startDate;
    private Date endDate;
    private boolean isCurrent;
    private String description;
    private String achievements;
    private String skillsUsed;
    private Date createdAt;
    private Date updatedAt;

    public Experience() {
    }

    public Experience(int id, int jobSeekerId, String position, String companyName, String companyLogo, String location, Date startDate, Date endDate, boolean isCurrent, String description, String achievements, String skillsUsed, Date createdAt, Date updatedAt) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.position = position;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.description = description;
        this.achievements = achievements;
        this.skillsUsed = skillsUsed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getSkillsUsed() {
        return skillsUsed;
    }

    public void setSkillsUsed(String skillsUsed) {
        this.skillsUsed = skillsUsed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
