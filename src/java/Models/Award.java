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
public class Award {

    private int id;
    private int jobSeekerId;
    private String awardName;
    private String issuingOrganization;
    private Date dateReceived;
    private String description;
    private String imagePath;
    private String certificateUrl;
    private Date createdAt;
    private Date updatedAt;

    public Award() {
    }

    public Award(int id, int jobSeekerId, String awardName, String issuingOrganization,
            Date dateReceived, String description, String imagePath, String certificateUrl,
            Date createdAt, Date updatedAt) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.awardName = awardName;
        this.issuingOrganization = issuingOrganization;
        this.dateReceived = dateReceived;
        this.description = description;
        this.imagePath = imagePath;
        this.certificateUrl = certificateUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Award(int jobSeekerId, String awardName, String issuingOrganization,
            Date dateReceived, String description, String imagePath, String certificateUrl) {
        this.jobSeekerId = jobSeekerId;
        this.awardName = awardName;
        this.issuingOrganization = issuingOrganization;
        this.dateReceived = dateReceived;
        this.description = description;
        this.imagePath = imagePath;
        this.certificateUrl = certificateUrl;
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

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
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
