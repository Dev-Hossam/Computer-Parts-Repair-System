package com.mycompany.customer_support;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author Ahmed
 */
public class Customer_Support {

    public static int auth;
    public static Customer custy = new Customer();
    public static Technician techy = new Technician();
    public static Admin adminy = new Admin();
    public static DataBase db = new DataBase();
   
 
    

//    public static int authenticateUser(String userName, String password) {
//
//        for (int i = 0; i < User.getUserList().size(); i++) {
//            User thisUser = User.getUserList().get(i);
//            if (thisUser.getUsername().equals(userName) && thisUser.getPassword().equals(password)) {
//                if (thisUser.getUserType().equals("Admin")) {
//                    System.out.println("Welcome admin");
//                    User.currentUser = i;
//                    return 1;
//                } else if (thisUser.getUserType().equals("Technician")) {
//                    System.out.println("Welcome Tech");
//                    User.currentUser = i;
//                    return 2;
//                } else if (thisUser.getUserType().equals("Customer")) {
//                    System.out.println("Welcome customer");
//                    User.currentUser = i;
//                    return 3;
//                }
//            } else {
//                auth = -1;
//            }
//
//        }
//
//        if (auth != -1) {
//            switch (authenticateUser(userName, password)) {
//                case 1:
//                    for (int i = 0; i < Admin.getAllAdmins().size(); i++) {
//                        Admin thisAdmin = Admin.getAllAdmins().get(i);
//                        if (thisAdmin.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
//                            Admin.currentAdmin = i;
//                        } else {
//                            System.out.println("couldn't find for admin");
//                        }
//                    }
//                    break;
//                case 2:
//                    for (int i = 0; i < Technician.getAllTechnicians().size(); i++) {
//                        Technician thisTechnician = Technician.getAllTechnicians().get(i);
//                        if (thisTechnician.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
//                            Technician.currentTechnicion = i;
//                        } else {
//                            System.out.println("couldn't find for technician");
//                        }
//                    }
//                    break;
//                case 3:
//                    for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
//                        Customer thisCustomer = Customer.getAllCustomers().get(i);
//                        if (thisCustomer.getUsername().equals(User.getUserList().get(User.currentUser).getUsername())) {
//                            Customer.currentCustmer = i;
//                        } else {
//                            System.out.println("couldn't find anything");
//                        }
//                    }
//                    break;
//            }
//        }
//        return -1;
//    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
       
        db.connect();
        DataBase.loadCustomers(db);
        DataBase.loadTickets(db);
        DataBase.loadTechnicians(db);
        Login2 login = new Login2();
        login.setVisible(true);

//        // Example query
//        String query = "SELECT * FROM Customer";
//        ResultSet resultSet = db.executeQuery(query);
        // Process the result set... 
        //----------------------------CUSTOMER COLLECTIONS-----------------------------------//
        Customer basicCustomer = new Customer();
        Customer cust1 = new Customer(2, "omar", "john@example.com", "123", "Technician", 1001, "123 Main St", "555-1234");

        User basicUser = new User();

        //----------------------------TECHNICIAN COLLECTIONS-----------------------------------//
        Technician basicTechnician = new Technician();
       Technician technician1 = new Technician(1, "JohnDoe", "johndoe@example.com", "password1", "Technician", 123456, true, "Network");
       Technician technician2 = new Technician(2, "JaneSmith", "janesmith@example.com", "password2", "Technician", 789012, true, "Hardware");

        //----------------------------ADMIN COLLECTIONS-----------------------------------//
        Admin basicAdmin = new Admin();
        Admin admin = new Admin(1, "ahmed", "admin@example.com", "123", "Admin", 123456, "IT", "Full Access");
        Admin admin2 = new Admin(2, "admin_user2", "admin2@example.com", "admin_password2", "Admin", 654321, "HR", "Limited Access");

        //----------------------------COMPUTER PART COLLECTIONS-----------------------------------//
        ComputerPart basicComputerPart = new ComputerPart();
        ComputerPart part = new ComputerPart(1, "RAM", "Memory", "4GB DDR4 RAM module");
        ComputerPart part2 = new ComputerPart(2, "SSD", "Storage", "500GB SATA SSD");
        ComputerPart part3 = new ComputerPart(2, "GUI", "Graphics", "3060 Nvidia ");
        ComputerPart part4 = new ComputerPart(2, "Screen", "Display", "ASUS 3ms 144hz");
        ComputerPart part5 = new ComputerPart(2, "CPU", "Prcess", "3GHz Rizen ");

        //----------------------------FEEDBACK COLLECTIONS-----------------------------------//
        Feedback basicFeedback = new Feedback();
        Feedback feedback = new Feedback(1, "Excellent service!", 5, "2024-05-03");
        Feedback feedback2 = new Feedback(2, "Great job!", 4, "2024-05-04");

        //----------------------------TICKET COLLECTIONS-----------------------------------//
        //----------------------------PATMENT COLLECTIONS-----------------------------------//
        //----------------------------TESTING-----------------------------------//
        if (basicUser.authenticateUser("omar", "123") == 2) {
            System.out.println("welcome customer");
            System.out.println(basicCustomer.getAllCustomers().get(Customer.currentCustmer).getUsername());
        } else {
            System.out.println("not  working");
        }


       // System.out.println(Customer.getAllCustomers().get(1).getcustomerRefNum());
        //System.out.println(Customer.getAllCustomers().get(1).getUserID());
    }
}
