/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customer_support;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hossa
 */
public class AdminController {

    private static Admin myAdmin = new Admin();

    public AdminController(Admin myAdmin) {
        this.myAdmin = myAdmin;
    }

    public static void addTechnician(Technician technician) {
        myAdmin.addTechnician(technician);
    }

    public static void removeTechnician(int technicianID) throws SQLException {
        myAdmin.removeTechnician(technicianID);
    }

    public static void addCustomer(Customer customer) {
        myAdmin.addCustomer(customer);
    }

    public static void removeCustomer(int CustomerID) {
       try {
            myAdmin.removeCustomer(CustomerID);

        } catch (SQLException ex) {
           Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);       }
    }

    public static void modifyUserAccount(int CustomerID, String address, String email, String userName,
            String password, String phoneNumber) {
        myAdmin.modifyUserAccount(CustomerID, address, email, userName, password, phoneNumber);

    }

    public static void modifyTechnicianAccount(int techID, boolean availablity, String email, String userName,
            String password, String expertise) {
        myAdmin.modifyTechnicianAccount(techID, availablity, email, userName, password, expertise);
    }

}
