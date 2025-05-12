package com.mycompany.customer_support;

import java.util.ArrayList;
import java.util.List;

public class TicketController {

    private static Ticket ticketController = new Ticket();
    /*
    we have created a dummy object of ticet called tic
    just to use it for submitting the ticket without affecting tickets values
    */
    

    public TicketController(Ticket myticket) {
        this.ticketController = myticket;
    }
    

    public static void submitTicket(Ticket.TicketPriority priority, int partID, String description) {
        ticketController.submitTicket(priority, partID, description);
        
         System.out.println("SUbmitted ya negm");   // please remove 
    }

    public static void cancelTicket(int ticketID) {
        ticketController.cancelTicket(ticketID);
    }

    public static void submitTicketFeedBack(int ticketID, String content) {
        ticketController.submitTicketFeedBack(ticketID, content);
    }
}
