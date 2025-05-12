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

// COMPUTER PART FUNCTIONALITIES: ComputerPart 
public class ComputerPart {

    // Static ArrayList to hold all computer parts
    private static ArrayList<ComputerPart> allComputerParts = new ArrayList<>();

    // Private attributes
    private int partID;
    private String name;
    private String type;
    private String description;

    // Constructor
    public ComputerPart() {
    }

    ;

    // PARAMETRISED CONSTRUCTOR
    public ComputerPart(int partID, String name, String type, String description) {
        this.partID = partID;
        this.name = name;
        this.type = type;
        this.description = description;

        // Add this computer part to the allComputerParts list
        allComputerParts.add(this);
       // DataBase.insertComputerPart(this);
    }

    // Getter and Setter methods
    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Method to display part details
    public void displayPartDetails() {
        System.out.println("Part ID: " + partID);
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Description: " + description);
    }

    // Method to return a whole Computer Part object using its ID, so then it would be used in submitTicket function
    public static ComputerPart findComputerPart(int partID) {
        for (int i = 0; i < allComputerParts.size(); i++) {
            ComputerPart comp = allComputerParts.get(i);
            if (comp.partID == partID) {
                return comp;
            }
        }
        return null;
    }

    // Static method to get all computer parts
    public static ArrayList<ComputerPart> getAllComputerParts() {
        return allComputerParts;
    }
}
