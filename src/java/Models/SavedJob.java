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
public class SavedJob {

    private int id;
    private Integer jobSeekerId;
    private Integer recruiterId;
    private int postId;
    private Timestamp savedAt;

    public SavedJob() {
    }

    public SavedJob(int id, Integer jobSeekerId, Integer recruiterId, int postId, Timestamp savedAt) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.recruiterId = recruiterId;
        this.postId = postId;
        this.savedAt = savedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }

}
