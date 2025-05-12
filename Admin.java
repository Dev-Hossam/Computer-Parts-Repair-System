package com.mycompany.customer_support;

import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
// admin can remove customer, and add customer and remove technician
public class Admin extends User {

    // Private attributes for Admin
    public static int currentAdmin;
    private int adminRefNum;
    private String department;
    private String accessLevel;

    // ArrayList to hold all admins
    private static ArrayList<Admin> adminList = new ArrayList<>();

    // Constructor
    public Admin() {
        super();
    }

    public Admin(int userID, String username, String email, String password, String usertype, int adminRefNum, String department, String accessLevel) {
        super(userID, username, email, password, usertype);
        this.adminRefNum = adminRefNum;
        this.department = department;
        this.accessLevel = accessLevel;

        // Add this admin to the admin list
        adminList.add(this);
    }

    // Getter and Setter methods specific to Admin
    public int getadminRefNum() {
        return adminRefNum;
    }

    public void setAdminID(int adminRefNum) {
        this.adminRefNum = adminRefNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Method to add a technician to the system
    public void addTechnician(Technician technician) {
        Technician.getAllTechnicians().add(technician);
    }

    // Method to remove a technician from the system by Technician ID
    public void removeTechnician(int technicianID) throws SQLException {
        ArrayList<Technician> technicians = Technician.getAllTechnicians();
        for (Technician technician : technicians) {
            if (technician.getUserID() == technicianID) {
                DataBase.deleteTechnician(technician.getUserID() );
                technicians.remove(technician);
                User.getUserList().remove(technician);
                break;
            }
        }
    }

    public void addCustomer(Customer customer) {
        Customer.getAllCustomers().add(customer);
    }

public void removeCustomer(int CustomerID) throws SQLException {
        for (int i = 0; i < User.getUserList().size(); i++) {
            User user = User.getUserList().get(i);
            if (user.getUserID() == CustomerID) {
                DataBase.deleteCustomer(user.getUserID());
                Customer.getAllCustomers().remove(user);
                User.getUserList().remove(user);
            }
        }
    }

    // Method to modify a user account
    public void modifyUserAccount(int CustomerID, String address, String email, String userName,
            String password, String phoneNumber) {
        for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
             Customer cust = Customer.getAllCustomers().get(i);
            if (cust.getUserID() == CustomerID) {
                cust.setUsername(userName);
                cust.setPassword(password);
                cust.setEmail(email);
                cust.setAddress(address);
                cust.setPhoneNumber(phoneNumber);
                DataBase.updateCustomerInDB(cust);
            }
        }
        
    }
    

    public void modifyTechnicianAccount(int techID, boolean availablity, String email, String userName,
            String password, String expertise) {
        for (int i = 0; i < Technician.getAllTechnicians().size(); i++) {
            Technician tech = Technician.getAllTechnicians().get(i);
            if (tech.getUserID() == techID) {
                tech.setUsername(userName);
                tech.setPassword(password);
                tech.setEmail(email);
                tech.setAvailability(availablity);
                tech.setExpertise(expertise);
                DataBase.updateTechInDB(tech);
            }
           
        }
        
    }

    // Other methods specific to Admin can be added here
    // Method to get the list of all admins
    public static ArrayList<Admin> getAllAdmins() {
        return adminList;
    }

    public void viewAllTickets() {
        ArrayList<Ticket> allTickets = Ticket.getAllTickets();

        if (allTickets.isEmpty()) {
            System.out.println("No tickets available.");
            return;
        }

        System.out.println("All Tickets:");
        for (Ticket ticket : allTickets) {
            System.out.println("-------------------------");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Customer:");
            displayCustomerDetails(ticket.getCustomer());
            System.out.println("Technician:");
            displayTechnicianDetails(ticket.getTechnician());
            System.out.println("Device Part:");
            displayComputerPartDetails(ticket.getDevicePart());
            System.out.println("Status: " + ticket.getStatus());
            System.out.println("Priority: " + ticket.getPriority());
            System.out.println("Feedback:");
            displayFeedbackDetails(ticket.getFeedback());
            System.out.println("Date: " + ticket.getDate());
            System.out.println("Price: " + ticket.getPrice());
            System.out.println("-------------------------");
        }
    }

    // Method to display customer details
    private void displayCustomerDetails(Customer customer) {
        System.out.println("Customer ID: " + customer.getcustomerRefNum());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Phone Number: " + customer.getPhoneNumber());
    }

    // Method to display technician details
    private void displayTechnicianDetails(Technician technician) {
        System.out.println("Technician ID: " + technician.getUserID());
        System.out.println("Username: " + technician.getUsername());
        System.out.println("Email: " + technician.getEmail());
        System.out.println("Availability: " + technician.getAvailability());
        System.out.println("Expertise: " + technician.getExpertise());
    }

    // Method to display computer part details
    private void displayComputerPartDetails(ComputerPart computerPart) {
        System.out.println("Part ID: " + computerPart.getPartID());
        System.out.println("Name: " + computerPart.getName());
        System.out.println("Type: " + computerPart.getType());
        System.out.println("Description: " + computerPart.getDescription());
    }

    // Method to display feedback details
    private void displayFeedbackDetails(Feedback feedback) {
        if (feedback != null) {
            System.out.println("Feedback ID: " + feedback.getFeedbackID());
            System.out.println("Content: " + feedback.getContent());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Date: " + feedback.getDate());
        } else {
            System.out.println("No feedback available.");
        }
    }

}