package com.mycompany.customer_support;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
public class Feedback {

    // Private attributes
    private int feedbackID;
    private String content;
    private int rating;
    private String date;

    // Constructor
    public Feedback() {
    }

    ;
    public Feedback(int feedbackID, String content, int rating, String date) {
        this.feedbackID = feedbackID;
        this.content = content;
        this.rating = rating;
        this.date = date;
    }

    // Getter and Setter methods
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Method to display feedback details
    public void displayFeedbackDetails() {
        System.out.println("Feedback ID: " + feedbackID);
        System.out.println("Content: " + content);
        System.out.println("Rating: " + rating);
        System.out.println("Date: " + date);
    }

}
