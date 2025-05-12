package com.mycompany.customer_support;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmed
 */
import static java.lang.Math.random;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Ticket {

    public static int counter;

    // Static ArrayList to hold all tickets
    private static ArrayList<Ticket> allTickets = new ArrayList<>();

    // Private attributes
    private int ticketId;
    private Customer customer;
    private Technician technician;
    private ComputerPart devicePart;
    private TicketStatus status;
    private TicketPriority priority;
    private Feedback feedback;
    private LocalDate date;
    private int price;
    private String description;

    // Constructors
    public Ticket() {
    }

    public Ticket(int ticketId, Customer customer, Technician technician,
            ComputerPart devicePart, TicketStatus status,
            TicketPriority priority, Feedback feedback, LocalDate date, int price, String description) {
        this.ticketId = ticketId;
        this.customer = customer;
        this.technician = technician;
        this.devicePart = devicePart;
        this.status = status;
        this.priority = priority;  // inc
        this.feedback = feedback;
        this.date = date;
        this.price = price;
        this.description = description;

        // Add this ticket to the allTickets list
        allTickets.add(this);
//        DataBase.insertTicket(this);
    }

    public String getDescription() {
        return description;
    }

// ticketpriority enumeration    
    public enum TicketPriority {
        HIGH,
        MEDIUM,
        LOW
    }
// ticketStatus Enumeration

    public enum TicketStatus {
        FIXED,
        NOT_FIXED,
        IN_PROGRESS,
        CANCELLED
    }

    // Getter and Setter methods
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Technician getTechnician() {
        return this.technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public ComputerPart getDevicePart() {
        return devicePart;
    }

    public void setDevicePart(ComputerPart devicePart) {
        this.devicePart = devicePart;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Method to display ticket details 
    public void displayTicketDetails() {
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Technician: " + technician.getUsername());
        System.out.println("Device Part: " + devicePart.getName());
        System.out.println("Status: " + status);
        System.out.println("Priority: " + priority);
        System.out.println("Feedback: " + (feedback != null ? feedback.getContent() : "N/A"));
        System.out.println("Date: " + date);
        System.out.println("Price: " + price);
    }

    // Static method to get all tickets
    public static ArrayList<Ticket> getAllTickets() {
        return allTickets;
    }

    public int calcPrice(TicketPriority priority) {
        switch (priority) {
            case HIGH:
                price = 200;
                break;
            case MEDIUM:
                price = 150;
                break;
            case LOW:
                price = 100;
                break;
        }
        setPrice(price); // Set the price based on the priority
        return price;
    }

    // Method to submit ticketRandom random = new Random();
    public void submitTicket(TicketPriority priority, int partID, String description) {
        try {

            if (Technician.findAvailableTechnician() != null) {
                Random random = new Random();
                int randomNumber = 100 + random.nextInt(900);
                setTicketId(randomNumber);

                LocalDate currentDate = LocalDate.now();

                System.out.println("get random ticket id" + getTicketId());
                Ticket newTicket = new Ticket(getTicketId(), Customer.getAllCustomers().get(User.currentUser),
                        Technician.findAvailableTechnician(), ComputerPart.findComputerPart(partID),
                        Ticket.TicketStatus.IN_PROGRESS, priority, null, currentDate, calcPrice(priority), description);
                Customer currentCustomer = Customer.getAllCustomers().get(User.currentUser);
                Technician currentTechnician = Technician.getAllTechnicians().get(Technician.availableTechnicianIndex);

                List<Ticket> tickets = currentCustomer.getCustTickets();
                List<Ticket> techTickets = Technician.getAllTechnicians().get(Technician.availableTechnicianIndex).getTechTickets();

                tickets.add(newTicket);
                
                techTickets.add(newTicket);
                currentTechnician.setAvailability(false);

//                System.out.println("Ticket submited!" + " your ticket ID is: " + getCustomer().getCustTickets().get(getCustomer().getCustTickets().size() - 1).ticketId);
            } else {
                System.out.println("No technicians available at the moment");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ONLY CUSTOMER CAN CANCEL HIS TICKET
    public void cancelTicket(int ticketID) {

        Customer currentCustomer = Customer.getCustomerList().get(User.currentUser);
        Technician currentTechnician = Technician.getAllTechnicians().get(Technician.availableTechnicianIndex);

        List<Ticket> tickets = currentCustomer.getCustTickets();
        List<Ticket> techTickets = Technician.getAllTechnicians().get(Technician.availableTechnicianIndex).getTechTickets();

        for (int i = 0; i < Customer.getCustomerList().get(User.currentUser).getCustTickets().size(); i++) {
            Ticket ticket = Customer.getCustomerList().get(User.currentUser).getCustTickets().get(i);
            if (ticket.ticketId == ticketID) {
                tickets.remove(ticket);
                System.out.println("Ticket removed from customer list");
            }
        }
        for (int i = 0; i < Technician.getAllTechnicians().get(Technician.availableTechnicianIndex).getTechTickets().size(); i++) {
            Ticket ticket = Technician.getAllTechnicians().get(Technician.availableTechnicianIndex).getTechTickets().get(i);
            if (ticket.ticketId == ticketID) {
                System.out.println("Ticket removed from technician list");
                currentTechnician.setAvailability(true);
                techTickets.remove(ticket);
                Ticket.allTickets.remove(ticket);
            }
        }
    }
    //    // Method to validate ticket data throw and catch for submitting a ticekt
    //    public- boolean validateData(Ticket ticket) {
    //        // Implementation to validate ticket data
    //        return true; // Placeholder return value
    //    }
    // Method to view ticket creation form
    //    public void viewTicketCreationForm() { FOR GUI
    //        // Implementation to view ticket creation form
    //    }
    // Method to update ticket status technician only can update ticket status "complete or in progresss"

    public void updateTicketStatus(int ticketID, TicketStatus status) {
        for (int i = 0; i < Technician.getAllTechnicians().get(Technician.currentTechnicion).getTechTickets().size(); i++) {
            Ticket ticket = Technician.getAllTechnicians().get(Technician.currentTechnicion).getTechTickets().get(i);
            if (ticket.getTicketId() == ticketID) {
                ticket.setStatus(status);
            }
        }
    }

    // Method to edit ticket details customer 
    public void editTicketDetails(int ticketID, int partID) {
        for (int i = 0; i < Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().size(); i++) {
            Ticket ticket = Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().get(i);
            if (ticket.getTicketId() == ticketID) {
                ticket.setDevicePart(ComputerPart.findComputerPart(partID));
                System.out.println(ticket.getDevicePart().getName());
            }
        }
    }

    // Method to view ticket status only customer can, can make an another function for admin and technician but they will be separate
    public void customerViewTicketStatus(int ticketID) {
        for (int i = 0; i < Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().size(); i++) {
            Ticket ticket = Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().get(i);
            if (ticket.getTicketId() == ticketID) {
                System.out.println("Customer's ticket status is: " + ticket.getStatus());
            }
        }
    }

    public void submitTicketFeedBack(int ticketID, String content) {
        for (Ticket ticket : Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets()) {
            if (ticket.ticketId == ticketID) {
                // Initialize Feedback object if it's null
                if (ticket.getFeedback() == null) {
                    ticket.setFeedback(new Feedback());
                }
                // Set the content
                ticket.getFeedback().setContent(content);
            }
        }
    }

    public void viewCustTickets() {
        for (int i = 0; i < Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().size(); i++) {
            Ticket ticket = Customer.getAllCustomers().get(Customer.currentCustmer).getCustTickets().get(i);
            System.out.println("Customer name on ticket: " + ticket.getCustomer().getUsername());
            System.out.println("Technician fixing working on the ticket: " + ticket.getTechnician().getUsername());
            System.out.println("Device being fixed: " + ticket.getDevicePart().getName());
            System.out.println("Ticket status: " + ticket.getStatus());
            System.out.println("Ticket priority: " + ticket.getPriority());
            System.out.println("Feedback content: " + ticket.getFeedback().getContent());
            System.out.println("price of ticket: " + ticket.getPrice());
        }

    }

    public void viewTechTickets() {
        for (int i = 0; i < Technician.getAllTechnicians().get(Technician.currentTechnicion).getTechTickets().size(); i++) {
            Ticket ticket = Technician.getAllTechnicians().get(Technician.currentTechnicion).getTechTickets().get(i);
            System.out.println("Technician fixing working on the ticket: " + ticket.getTechnician().getUsername());
            System.out.println("Customer name on ticket: " + ticket.getCustomer().getUsername());
            System.out.println("Device being fixed: " + ticket.getDevicePart().getName());
            System.out.println("Ticket status: " + ticket.getStatus());
            System.out.println("Ticket priority: " + ticket.getPriority());
            System.out.println("Feedback content: " + ticket.getFeedback().getContent());
            System.out.println("price of ticket: " + ticket.getPrice());
        }
    }

}
