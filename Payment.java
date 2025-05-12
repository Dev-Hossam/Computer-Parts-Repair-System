package com.mycompany.customer_support;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
import java.util.ArrayList;

public class Payment {

    // Static ArrayList to hold all payments
    private static ArrayList<Payment> allPayments = new ArrayList<>();

    // Private attributes
    private int paymentID;
    private Ticket ticket;
    private int amount;
    private boolean paymentStatus;

    // Constructor
    public Payment() {
    }

    public Payment(int paymentID, Ticket ticket, int amount, boolean paymentStatus) {
        this.paymentID = paymentID;
        this.ticket = ticket;
        this.amount = amount;
        this.paymentStatus = paymentStatus;

        // Add this payment to the allPayments list
        allPayments.add(this);
    }

    // Getter and setter methods
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Static method to get all payments
    public static ArrayList<Payment> getAllPayments() {
        return allPayments;
    }
}
