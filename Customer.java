package com.mycompany.customer_support;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
public class Customer extends User {

    // Private attributes for Customer
    public static int currentCustmer;
    private int customerRefNum;
    private String address;
    private String phoneNumber;
    private  List<Ticket> custTickets = new ArrayList<>();    

    // ArrayList to hold all customers
    private static ArrayList<Customer> customerList = new ArrayList<>();

    // Constructor
    public Customer() {
    }

    ;
    public Customer(int userID, String username, String email, String password, String userType, int customerRefNum, String address, String phoneNumber) {
        super(userID, username, email, password, userType);
        this.customerRefNum = customerRefNum;
        this.address = address;
        this.phoneNumber = phoneNumber;

        // Add this customer to the customer list
        customerList.add(this);
        
    }

    public Customer AddCustomer(String username, String email, String password, String address, String phoneNumber) {

        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);
        int randomNumber2 = 100 + random.nextInt(900);

        setUserID(randomNumber);
        setcustomerRefNum(randomNumber2);

        Customer cust = new Customer(getUserID(), username, email, password, "Customer", getcustomerRefNum(), address, phoneNumber);
        customerList.add(cust);
        // inserting the cx to db
        DataBase.insertCustomer(cust);
        System.out.println("custoemr added");
        return null;
    }

    // Getter and Setter methods specific to Customer
    public int getcustomerRefNum() {
        return customerRefNum;
    }

    public void setcustomerRefNum(int customerRefNum) {
        this.customerRefNum = customerRefNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Method to get the list of all customers
    public static ArrayList<Customer> getAllCustomers() {
        return customerList;
    }

    public  List<Ticket> getCustTickets() {
        return custTickets;
    }

    public static ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void modifyCustomerAccount(String address, String email, String userName, String password, String phoneNumber) {

        Customer cust = Customer.getAllCustomers().get(User.currentUser);
        if(!"".equals(userName))
        cust.setUsername(userName);
        // if the password is not empty you pass it 
        if(!"".equals(password))
        cust.setPassword(password);
        
        if(!"".equals(phoneNumber))
        cust.setPhoneNumber(phoneNumber);
        
        if(!"".equals(email))
        cust.setEmail(email);
        
        if(!"".equals(address))
        cust.setAddress(address);
 // this is a test phrase if the values are actually changed 
        System.out.println("all data updated ");
        DataBase.updateCustomerInDB(cust);
    }
   
    
}
