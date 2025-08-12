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
public class FinancialTransaction {

    private int id;
    private int recruiterId;
    private String type;
    private String transactionType;
    private double amount;
    private String description;
    private String status;
    private Timestamp transactionDate;
    private String transactionCode;

    public FinancialTransaction() {
    }

    public FinancialTransaction(int id, int recruiterId, String type, String transactionType, double amount, String description, String status, Timestamp transactionDate, String transactionCode) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.type = type;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.transactionDate = transactionDate;
        this.transactionCode = transactionCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

}
