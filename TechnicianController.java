package com.mycompany.customer_support;

import java.util.ArrayList;

public class TechnicianController {

    private static Technician technicianModel = new Technician();

    public TechnicianController(Technician technicianModel) {
        this.technicianModel = technicianModel;
    }

    public static void setAvailability(boolean availability) {
        technicianModel.setAvailability(availability);
    }

    public static void setExpertise(String expertise) {
        technicianModel.setExpertise(expertise);
    }

    public static void modifyTechnicianAccount(String email, String username, String password) {
        technicianModel.modifyTechnicianAccount(email, username, password);
    }

}
