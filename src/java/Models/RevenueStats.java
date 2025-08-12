/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author DELL
 */
public class RevenueStats {

    private double monthlyRevenue;
    private double totalRevenue;
    private int transactionCount;

    public RevenueStats() {
    }

    public RevenueStats(double monthlyRevenue, double totalRevenue, int transactionCount) {
        this.monthlyRevenue = monthlyRevenue;
        this.totalRevenue = totalRevenue;
        this.transactionCount = transactionCount;
    }

    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

}
