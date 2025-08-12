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
public class Application {

    private int applicationId;
    private int postId;
    private int jobSeekerId;
    private int cvId;
    private Posts post;
    private JobSeeker jobseeker;
    private String status;
    private Timestamp createdAt;
    private String cvFile;
    private String coverLetter;

    public Application() {
    }

    public Application(int applicationId, int postId, int jobSeekerId, int cvId, Posts post, JobSeeker jobseeker, String status, Timestamp createdAt, String cvFile, String coverLetter) {
        this.applicationId = applicationId;
        this.postId = postId;
        this.jobSeekerId = jobSeekerId;
        this.cvId = cvId;
        this.post = post;
        this.jobseeker = jobseeker;
        this.status = status;
        this.createdAt = createdAt;
        this.cvFile = cvFile;
        this.coverLetter = coverLetter;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public JobSeeker getJobseeker() {
        return jobseeker;
    }

    public void setJobseeker(JobSeeker jobseeker) {
        this.jobseeker = jobseeker;
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

    public String getCvFile() {
        return cvFile;
    }

    public void setCvFile(String cvFile) {
        this.cvFile = cvFile;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

   
}
