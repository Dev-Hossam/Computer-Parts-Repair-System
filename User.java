package com.mycompany.customer_support;

import static com.mycompany.customer_support.Customer_Support.auth;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
public class User {

    // Private attributes
    private static ArrayList<User> userList = new ArrayList<>();

    public static int currentUser;
    private int userID;
    private String username;
    private String email;
    private String password;
    private String userType;

    // Constructor
    public User() {
    }

    ;
    public User(int userID, String username, String email, String password, String usertype) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = usertype;
        userList.add(this);
    }

    // Getter and Setter methods
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to authenticate user (login/signup)
    public void authenticateUser() {
        // Implementation for authentication
    }

    // Method to manage user account
    public void manageAccount() {
        // Implementation for managing account
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public String getUserType() {
        return userType;
    }

    public int authenticateUser(String userName, String password) {

        for (int i = 0; i < User.getUserList().size(); i++) {
            User thisUser = User.getUserList().get(i);
            if (thisUser.getUsername().equals(userName) && thisUser.getPassword().equals(password)) {
                if (thisUser.getUserType().equals("Admin")) {
                    System.out.println("Welcome admin");
                    User.currentUser = i;
                    return 1;
                } else if (thisUser.getUserType().equals("Technician")) {
                    System.out.println("Welcome Tech");
                    User.currentUser = i;
                    return 2;
                } else if (thisUser.getUserType().equals("Customer")) {
                    System.out.println("Welcome customer");
                    User.currentUser = i;
                    return 3;
                }
            } else {
                auth = -1;
            }

        }

        if (auth != -1) {
            switch (authenticateUser(userName, password)) {
                case 1:
                    for (int i = 0; i < Admin.getAllAdmins().size(); i++) {
                        Admin thisAdmin = Admin.getAllAdmins().get(i);
                        if (thisAdmin.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
                            Admin.currentAdmin = i;
                        } else {
                            System.out.println("couldn't find for admin");
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < Technician.getAllTechnicians().size(); i++) {
                        Technician thisTechnician = Technician.getAllTechnicians().get(i);
                        if (thisTechnician.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
                            Technician.currentTechnicion = i;
                        } else {
                            System.out.println("couldn't find for technician");
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
                        Customer thisCustomer = Customer.getAllCustomers().get(i);
                        if (thisCustomer.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
                            Customer.currentCustmer = i;
                        } else {
                            System.out.println("couldn't find anything");
                        }
                    }
                    break;
            }
        }
        return -1;
    }

}
