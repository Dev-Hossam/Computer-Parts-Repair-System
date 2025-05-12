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
public class Technician extends User {

    // Private attributes for Technician
    public static int currentTechnicion;
    public static int availableTechnicianIndex;
    int TechRefNum;
    private boolean availability;
    private String expertise;

    // ArrayList to hold all technicians
    private static ArrayList<Technician> technicianList = new ArrayList<>();
    private  List<Ticket> techTickets = new ArrayList<>();   // arrayList to hold all the tickets that a Technician has taken

    public Technician() {
        super();
    }

    public Technician(int userID, String username, String email, String password, String userType, int TechRefNum, boolean availability, String expertise) {
        super(userID, username, email, password, userType);
        this.availability = availability;
        this.expertise = expertise;
        

        // Add this technician to the technician list
        technicianList.add(this);
        DataBase.insertTechnician(this);
    }

    // Getter and Setter methods specific to Technician
    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    // Method to get the list of all technicians
    public static ArrayList<Technician> getAllTechnicians() {
        return technicianList;
    }

    // function that finds any available technicians so that they would be assigned a ticket in the fucntion submit Ticket
    public static Technician findAvailableTechnician() {
        for (int i = 0; i < technicianList.size(); i++) {
            Technician tec = technicianList.get(i);
            if (tec.availability == true) {
                System.out.println("technician: " + tec.getUsername() + " is available");
                availableTechnicianIndex = i;
                return tec;
            }
        }
        return null;
    }

    // arraylist containing all technicians
    public static ArrayList<Technician> getTechnicianList() {
        return technicianList;
    }

    // list containing all the tickets the a technician works on
    public  List<Ticket> getTechTickets() {
        return techTickets;
    }

    // function to allow Technician to modifiy his personal data, by using the currentTechnician static variable as index to find location of 
    //the object to be changed
    public void modifyTechnicianAccount(String email, String userName, String password) {

        Technician tech = Technician.getAllTechnicians().get(Technician.currentTechnicion);
        
        if (!"".equals(userName)) {
            tech.setUsername(userName);
        }
        // if the password is not empty you pass it 
        if (!"".equals(password)) {
            tech.setPassword(password);
        }

        if (!"".equals(email)) {
            tech.setEmail(email);
        }
        // this is a test phrase if the values are actually changed 
        System.out.println("all data updated ");
        DataBase.updateTechInDB(tech);
    }

    public void setTechRefNum(int TechRefNum) {
        this.TechRefNum = TechRefNum;
    }

    public int getTechRefNum() {
        return TechRefNum;
    }

}
