package com.mycompany.customer_support;

import com.mycompany.customer_support.Ticket.TicketPriority;
import com.mycompany.customer_support.Ticket.TicketStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataBase {

    // Database connection details
    private static final String URL = "jdbc:derby:SystemDB8";
    private static final String USERNAME = "SystemDB6";
    private static final String PASSWORD = "SystemDB6";
    private static Connection connection;

    // Constructor
    public DataBase() {
        try {
            // Load the Derby JDBC driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Connect to the database
    public void connect() {
        try {
            // Establish the database connection
            connection = DriverManager.getConnection(URL);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            // If database doesn't exist, attempt to create it
            if (e.getSQLState().equals("XJ004")) {
                createDatabase();
            } else {
                e.printStackTrace();
            }
        }
    }

    // Disconnect from the database
    public void disconnect() {
        if (connection != null) {
            try {
                // Close the database connection
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Create the database in case it is not found
    private void createDatabase() {
        try {
            // Establish connection to create the database
            connection = DriverManager.getConnection("jdbc:derby:SystemDB8;create=true");
            System.out.println("Database created.");
            createTables(); // After creating the database, create tables
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Create tables in the database and called only from the create Database
    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            // Create UserAccount table
            statement.executeUpdate("CREATE TABLE UserAccount ("
                    + "    userID INT PRIMARY KEY,"
                    + "    username VARCHAR(255) NOT NULL,"
                    + "    email VARCHAR(255) UNIQUE NOT NULL,"
                    + "    password VARCHAR(255) NOT NULL,"
                    + "    userType VARCHAR(255) NOT NULL"
                    + ")");

            // Create Customer table
            statement.executeUpdate("CREATE TABLE Customer ("
                    + "    customerID INT PRIMARY KEY,"
                    + "    address VARCHAR(255) NOT NULL,"
                    + "    phoneNumber VARCHAR(20) NOT NULL,"
                    + "    userID INT UNIQUE,"
                    + "    FOREIGN KEY (userID) REFERENCES UserAccount(userID) ON DELETE CASCADE"
                    + ")");

            // Create AdminAccount table
            statement.executeUpdate("CREATE TABLE AdminAccount ("
                    + "    adminID INT PRIMARY KEY,"
                    + "    department VARCHAR(255) NOT NULL,"
                    + "    accessLevel VARCHAR(255) NOT NULL,"
                    + "    userID INT UNIQUE,"
                    + "    FOREIGN KEY (userID) REFERENCES UserAccount(userID) ON DELETE CASCADE"
                    + ")");

            // Create Technician table
            statement.executeUpdate("CREATE TABLE Technician ("
                    + "    TechID INT PRIMARY KEY,"
                    + "    userID INT UNIQUE,"
                    + "    availability BOOLEAN NOT NULL,"
                    + "    expertise VARCHAR(255) NOT NULL,"
                    + "    FOREIGN KEY (userID) REFERENCES UserAccount(userID) ON DELETE CASCADE"
                    + ")");

            // Create ComputerPart table
            statement.executeUpdate("CREATE TABLE ComputerPart ("
                    + "    partID INT PRIMARY KEY,"
                    + "    name VARCHAR(255) NOT NULL,"
                    + "    type VARCHAR(255) NOT NULL,"
                    + "    description VARCHAR(255) NOT NULL"
                    + ")");

            // Create Feedback table
            statement.executeUpdate("CREATE TABLE Feedback ("
                    + "    feedbackID INT PRIMARY KEY,"
                    + "    content VARCHAR(255) NOT NULL,"
                    + "    rating INT CHECK (rating >= 0 AND rating <= 10) NOT NULL,"
                    + "    date DATE NOT NULL"
                    + ")");

            // Create Ticket table
            statement.executeUpdate("CREATE TABLE Ticket ("
                    + "    ticketID INT PRIMARY KEY,"
                    + "    customerID INT NOT NULL,"
                    + "    technicianID INT NOT NULL,"
                    + "    devicePartID INT NOT NULL,"
                    + "    status VARCHAR(255) NOT NULL,"
                    + "    priority VARCHAR(255) NOT NULL,"
                    + "    feedbackID INT,"
                    + "    date DATE NOT NULL,"
                    + "    price INT NOT NULL,"
                    + "    DESCRIPTION VARCHAR(225),"
                    + "    FOREIGN KEY (customerID) REFERENCES Customer(customerID) ON DELETE CASCADE,"
                    + "    FOREIGN KEY (technicianID) REFERENCES Technician(TechID) ON DELETE CASCADE,"
                    + "    FOREIGN KEY (devicePartID) REFERENCES ComputerPart(partID) ON DELETE CASCADE,"
                    + "    FOREIGN KEY (feedbackID) REFERENCES Feedback(feedbackID) ON DELETE CASCADE "
                    + ")");

            // Create Payment table
            statement.executeUpdate("CREATE TABLE Payment ("
                    + "    paymentID INT PRIMARY KEY,"
                    + "    ticketID INT NOT NULL,"
                    + "    amount INT NOT NULL,"
                    + "    paymentStatus BOOLEAN NOT NULL,"
                    + "    FOREIGN KEY (ticketID) REFERENCES Ticket(ticketID)"
                    + ")");

            System.out.println("Tables created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Execute a SELECT query and return the result set
    // this is a base class that almost all classes call to excute 
    // think of different implementation
    public static ResultSet executeQuery(String sqlQuery) {
        ResultSet resultSet = null;
        try {
            // Create a statement object
            Statement statement = connection.createStatement();
            // Execute the SQL query
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Execute an SQL update statement (INSERT, UPDATE, DELETE)
    // this is a base class that almost all classes call to excute 
    private static void executeUpdate(String sqlUpdate) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlUpdate);
    }

    //--------------------------------------------------------deletion / remove and cancel-----------------------------------
    public static void deleteCustomer(int cxId) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Drop the existing foreign key constraint (if it exists)

            // Add the updated foreign key constraint with ON DELETE CASCADE
            // Retrieve the userID associated with the customerID
            ResultSet resultSet = statement.executeQuery("SELECT userID FROM Customer WHERE USERID = " + cxId);

            if (resultSet.next()) { // Move the cursor to the first row
                int userID = resultSet.getInt("userID");

                // Delete the entry from Customer table
                String sqlDeleteCustomer = "DELETE FROM Customer WHERE customerID = " + cxId;
                statement.executeUpdate(sqlDeleteCustomer);

                sqlDeleteCustomer = "DELETE FROM UserAccount WHERE USERID = " + cxId;
                statement.executeUpdate(sqlDeleteCustomer);

                System.out.println("Customer deleted successfully from the database.");
            } else {
                System.out.println("Customer with ID " + cxId + " not found.");
                return; // Exit the method if customer is not found
            }

            // No need to delete from UserAccount table as it will be automatically handled by ON DELETE CASCADE
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTechnician(int techID) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Retrieve the userID associated with the technician
            ResultSet resultSet = statement.executeQuery("SELECT userID FROM Technician WHERE USERID = " + techID);

            if (resultSet.next()) { // Move the cursor to the first row
                int userID = resultSet.getInt("userID");

                // Delete the entry from Technician table
                String sqlDeleteTechnician = "DELETE FROM Technician WHERE USERID = " + techID;
                statement.executeUpdate(sqlDeleteTechnician);

                // Delete the entry from UserAccount table
                String sqlDeleteUser = "DELETE FROM UserAccount WHERE userID = " + userID;
                statement.executeUpdate(sqlDeleteUser);

                System.out.println("Technician deleted successfully from the database.");
            } else {
                System.out.println("Technician with ID " + techID + " not found.");
                return; // Exit the method if technician is not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------- Anything below is to be revised ---------------------------------------------------------
    // review reasons: the tables when names derby makes them all capitals
    // ensure the right attributes are translated into the right columns with the rigbht relations
    // -------------------------------------------- sys insertions to db--------------------
    // missing insertions
    /*
Inserting a ticket
Inserting a customer “signup” -- I think that is already created with add customer - recheck
Inserting a tech - idk where is this helpful tbh

//     */
    // needed for inserting  a ticket
//public static void insertComputerPart(ComputerPart part) {
//    String sqlInsertPart = "INSERT INTO ComputerPart(partID, name, type, description) VALUES ('"
//            + part.getPartID() + "', '"
//            + part.getName() + "', '"
//            + part.getType() + "', '"
//            + part.getDescription() + "')";
//
//    try (Statement statement = connection.createStatement()) {
//        // Execute the insert statement
//        statement.executeUpdate(sqlInsertPart);
//        System.out.println("ComputerPart inserted successfully into the database.");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//
//
//    //  inserting a ticket  -- done I think  --- to be completed later
//    public static void insertTicket(Ticket ticket) {
//        String sqlInsertTicket = "INSERT INTO Ticket (ticketID, customerID, technicianID, devicePartID, status, priority, feedbackID, date, price, DESCRIPTION) VALUES ("
//                + ticket.getTicketId() + ", "
//                + ticket.getCustomer().getUserID() + ", "
//                + ticket.getTechnician().getUserID() + ", "
//                + ticket.getDevicePart().getPartID() + ", " // first create an insertion call for the ticket part that is being sent
//                + "'" + ticket.getStatus() + "', "
//                + "'" + ticket.getPriority() + "', "
//                + (ticket.getFeedback() != null ? ticket.getFeedback().getFeedbackID() : null) + ", "
//                + "'" + ticket.getDate() + "', "
//                + ticket.getPrice() + ", "
//                + "'" + ticket.getDescription() + "')";
//
//        try (Statement statement = connection.createStatement()) {
//            // Execute the insert statements
//            statement.executeUpdate(sqlInsertTicket);
//            System.out.println("Customer inserted successfully into the database.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    // inserting a new custoemr (signup)
    public static void insertCustomer(Customer customer) {
        String sqlInsertUser = "INSERT INTO UserAccount (userID, username, email, password, userType) VALUES ("
                + customer.getUserID() + ", "
                + "'" + customer.getUsername() + "', "
                + "'" + customer.getEmail() + "', "
                + "'" + customer.getPassword() + "', "
                + "'" + customer.getUserType() + "')";

        String sqlInsertCustomer = "INSERT INTO Customer (customerID, address, phoneNumber, userID) VALUES ("
                + customer.getcustomerRefNum() + ", "
                + "'" + customer.getAddress() + "', "
                + "'" + customer.getPhoneNumber() + "', "
                + customer.getUserID() + ")";

        try (Statement statement = connection.createStatement()) {
            // Execute the insert statements
            statement.executeUpdate(sqlInsertUser);
            statement.executeUpdate(sqlInsertCustomer);
            System.out.println("Customer inserted successfully into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // insert tech
    public static void insertTechnician(Technician technician) {
        String sqlInsertUser = "INSERT INTO UserAccount (userID, username, email, password, userType) VALUES ("
                + technician.getUserID() + ", "
                + "'" + technician.getUsername() + "', "
                + "'" + technician.getEmail() + "', "
                + "'" + technician.getPassword() + "', "
                + "'" + technician.getUserType() + "')";

        String sqlInsertTechnician = "INSERT INTO Technician (TechID, userID, availability, expertise) VALUES ("
                + technician.getTechRefNum() + ", "
                + technician.getUserID() + ", "
                + "'" + technician.getAvailability() + "', "
                + "'" + technician.getExpertise() + "')";

        try (Statement statement = connection.createStatement()) {
            // Execute the insert statements
            statement.executeUpdate(sqlInsertUser);
            statement.executeUpdate(sqlInsertTechnician);
            System.out.println("Technician inserted successfully into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // admin insert is wrong
    // insert admin to the db after the program termination incase any is added on the run
    public static void insertAdmins(ArrayList<Admin> admins) {
        try {
            for (Admin admin : admins) {
                String sqlInsert = "INSERT INTO AdminAccount (userID, adminID, department, accessLevel) VALUES ("
                        + admin.getUserID() + ", "
                        + admin.getadminRefNum() + ", "
                        + "'" + admin.getDepartment() + "', "
                        + "'" + admin.getAccessLevel() + "')";
                executeUpdate(sqlInsert);

            }

            System.out.println("Admins inserted successfully into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------All updates-----------------------
    // missing updates
    /*
    Drop (basically remove stuff) - this remove stuff from the db once removed in the system “ preferably during the disconnect”
Drop cx (admin can remove cx)
    - Drop tech (same)
    - Drop ticket (if cx removed their ticket)
        - Must be removed from all arraylists - make sure it is in the actual code to avoid issues
Update values   - this updates the table with the new values
Update info
    - Cx
    - Tech
    - Admin
- Update ticke
    
     */
    // Method to update customer information in the database
  public static void updateCustomerInDB(Customer cust) {
    try (Statement statement = connection.createStatement()) {
        // Construct the SQL UPDATE statement for the Customer table
        String sqlUpdateCustomer = "UPDATE CUSTOMER SET "
                + "phoneNumber = '" + cust.getPhoneNumber() + "', "
                + "address = '" + cust.getAddress() + "' "
                + "WHERE customerID = " + cust.getcustomerRefNum();
        
        // Construct the SQL UPDATE statement for the UserAccount table
        String sqlUpdateUserAccount = "UPDATE UserAccount SET "
                + "username = '" + cust.getUsername() + "', "
                + "email = '" + cust.getEmail() + "', "
                + "password = '" + cust.getPassword() + "' "
                + "WHERE userID = " + cust.getUserID();

        // Execute the SQL UPDATE statements
        statement.executeUpdate(sqlUpdateCustomer);
        statement.executeUpdate(sqlUpdateUserAccount);

        System.out.println("Customer information updated in the database.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
  
    public static void updateTechInDB(Technician Tech) {
    try (Statement statement = connection.createStatement()) {
        
        // Construct the SQL UPDATE statement for the UserAccount table
        String sqlUpdateUserAccount = "UPDATE UserAccount SET "
                + "username = '" + Tech.getUsername() + "', "
                + "email = '" + Tech.getEmail() + "', "
                + "password = '" + Tech.getPassword() + "' "
                + "WHERE userID = " + Tech.getUserID();

        // Execute the SQL UPDATE statements
        statement.executeUpdate(sqlUpdateUserAccount);

        System.out.println("Customer information updated in the database.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    //-------------------------------------All loads / select from db to arraylist---------------
    // loading the all data from the database upon initiating the system
    // this is to load the arraylists for the runtime handle
    public static void LoadFromDataBase() {
        DataBase db = new DataBase();
        db.connect();

        // Load data into ArrayLists
        loadAdmins(db);
        loadCustomers(db);
        loadTechnicians(db);
        loadTickets(db);

    }
    // loadAmdins data from db to arraylists

    private static void loadAdmins(DataBase db) {
        ResultSet resultSet = db.executeQuery("SELECT * FROM AdminAccount");
        try {
            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                // Retrieving additional attributes from UserAccount table
                ResultSet userResultSet = db.executeQuery("SELECT * FROM UserAccount WHERE userID = " + userID);
                userResultSet.next(); // Assuming there's always one result
                String email = userResultSet.getString("email");
                String username = userResultSet.getString("username");
                String password = userResultSet.getString("password");
                String userType = userResultSet.getString("userType");
                // Continuing with loading Admin attributes
                int adminRefNum = resultSet.getInt("adminID");
                String department = resultSet.getString("department");
                String accessLevel = resultSet.getString("accessLevel");
                Admin admin = new Admin(userID, username, email, password, userType, adminRefNum, department, accessLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // load customers data from db to arraylists

    // make it private later
    public static void loadCustomers(DataBase db) {
        ResultSet resultSet = db.executeQuery("SELECT * FROM Customer");
        try {
            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                // Retrieving additional attributes from UserAccount table
                ResultSet userResultSet = db.executeQuery("SELECT * FROM UserAccount WHERE userID = " + userID);
                userResultSet.next(); // Assuming there's always one result
                String email = userResultSet.getString("email");
                String username = userResultSet.getString("username");
                String password = userResultSet.getString("password");
                String userType = userResultSet.getString("userType");
                // Continuing with loading Customer attributes
                int customerRefNum = resultSet.getInt("customerID");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                Customer customer = new Customer(userID, username, email, password, userType, customerRefNum, address, phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // load technicians data from db to arraylists

    public static void loadTechnicians(DataBase db) {
        ResultSet resultSet = db.executeQuery("SELECT * FROM Technician");
        try {
            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                // Retrieving additional attributes from UserAccount table
                ResultSet userResultSet = db.executeQuery("SELECT * FROM UserAccount WHERE userID = " + userID);
                userResultSet.next(); // Assuming there's always one result
                String email = userResultSet.getString("email");
                String username = userResultSet.getString("username");
                String password = userResultSet.getString("password");
                String userType = userResultSet.getString("userType");
                // Continuing with loading Technician attributes
                int techRefNum = resultSet.getInt("techID");
                boolean availability = resultSet.getBoolean("availability");
                String expertise = resultSet.getString("expertise");
                Technician technician = new Technician(userID, username, email, password, userType, techRefNum, availability, expertise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Loading Device parts
    private static void loadDeviceParts(DataBase db) {
        String query = "SELECT * FROM ComputerPart";
        ResultSet resultSet = db.executeQuery(query);
        try {
            while (resultSet.next()) {
                int partId = resultSet.getInt("partID");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                ComputerPart part = new ComputerPart(partId, name, type, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // current customer tickets retrival
    public static void loadTickets(DataBase db) {
        String query = "SELECT * FROM Ticket";
        ResultSet resultSet = db.executeQuery(query);
        try {
            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticketID");
                int customerId = resultSet.getInt("customerID");
                int technicianId = resultSet.getInt("technicianID");
                int devicePartId = resultSet.getInt("devicePartID");
                String statusStr = resultSet.getString("status");
                TicketStatus status = TicketStatus.valueOf(statusStr);
                String priorityStr = resultSet.getString("priority");
                TicketPriority priority = TicketPriority.valueOf(priorityStr);
                int feedbackId = resultSet.getInt("feedbackID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int price = resultSet.getInt("price");
                String description = resultSet.getString("DESCRIPTION");                         /// REMEMBER TO ADD IT IN THE TABLE STRUCTURE

                Customer customer = getCustomerById(customerId);
                Technician technician = getTechnicianById(technicianId);
                ComputerPart devicePart = getComputerPartById(devicePartId);
                Feedback feedback = getFeedbackById(feedbackId);

                // Handle null values
                if (customer == null) {
                    customer = new Customer(0, "", "", "", "", customerId, "", "");
                }
                if (technician == null) {
                    technician = new Technician();
                }
                if (devicePart == null) {
                    devicePart = new ComputerPart(0, "", "", "");
                }
                if (feedback == null) {
                    feedback = new Feedback(0, "", 0, "");
                }

                Ticket ticket = new Ticket(ticketId, customer, technician, devicePart, status, priority, feedback, date, price, description);

                // Add ticket to appropriate customer's list
                addTicketToCustomerList(ticket);

                // Add ticket to appropriate technician's list
                addTicketToTechnicianList(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------supporting functions--------------------
    // Missing loads
    // Loading payment “if needed” – ignore this for now- might be deleted
    /*what follows are all the functions that handles all the retrival of the tickets
    this include retriving the ticket data to the alltickets arraylist, retriving customer 
    tickets, tech tickets, and some conditions to ensure if there is a null value that it won't 
    return an error or cause a fatal error as well as exception handling for the sql
     */
    // Add ticket to appropriate customer's list
    private static void addTicketToCustomerList(Ticket ticket) {
        int customerId = ticket.getCustomer().getUserID();
        for (Customer customer : Customer.getAllCustomers()) {
            if (customer.getUserID() == customerId) {
                customer.getCustTickets().add(ticket);
                break;
            }
        }
    }

    // Add ticket to appropriate technician's list
    private static void addTicketToTechnicianList(Ticket ticket) {
        int technicianId = ticket.getTechnician().getUserID();
        for (Technician technician : Technician.getAllTechnicians()) {
            if (technician.getUserID() == technicianId) {
                technician.getTechTickets().add(ticket);
                break;
            }
        }
    }

    // Method to retrieve a customer by ID
    private static Customer getCustomerById(int customerId) {
        String query = "SELECT * FROM Customer WHERE customerID = " + customerId;
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet.next()) {
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                int userId = resultSet.getInt("userID");
                return new Customer(userId, null, null, null, null, customerId, address, phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve a technician by ID
    private static Technician getTechnicianById(int technicianId) {
        String query = "SELECT * FROM Technician WHERE TechID = " + technicianId;
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet.next()) {
                int userId = resultSet.getInt("userID");
                boolean availability = resultSet.getBoolean("availability");
                String expertise = resultSet.getString("expertise");
                int TechRef = resultSet.getInt("TECHID");
                String techname = null;
                String mail = null;
                String password = null;
                String userType = null;
                String query2 = "SELECT * FROM USER WHERE USERID = " + userId;
                ResultSet resultSet2 = executeQuery(query2);
                if (resultSet2.next()) {
                    techname = resultSet2.getString("USERNAME");
                    mail = resultSet2.getString("EMAIL");
                    password = resultSet2.getString("PASSWORD");
                    userType = resultSet2.getString("USERTYPE");
                }
                return new Technician(userId, techname, mail, password, userType, TechRef, availability, expertise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }  // FIX THIS BY LOOP THROUGH THE DB AND GETTING THE USER VALUES FOR THE TECHAND THEN PASS THEM

    // Method to retrieve a computer part by ID
    private static ComputerPart getComputerPartById(int partId) {
        String query = "SELECT * FROM ComputerPart WHERE partID = " + partId;
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                return new ComputerPart(partId, name, type, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve a feedback by ID so it would be sent to ticket arraylist
    private static Feedback getFeedbackById(int feedbackId) {
        String query = "SELECT * FROM Feedback WHERE feedbackID = " + feedbackId;
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet.next()) {
                String content = resultSet.getString("content");
                int rating = resultSet.getInt("rating");
                String date = resultSet.getString("date");
                return new Feedback(feedbackId, content, rating, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
