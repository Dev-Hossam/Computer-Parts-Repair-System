package com.mycompany.customer_support;

public class CustomerController {

    private static Customer customerModel = new Customer();
   
   
    public  CustomerController(){
         customerModel = Customer.getAllCustomers().get(Customer.currentCustmer);
    }

    public CustomerController(Customer customerModel) {
        this.customerModel = customerModel;
    }

    public static void setCustomerRefNum(int customerRefNum) {
        customerModel.setcustomerRefNum(customerRefNum);
    }

    public static void setAddress(String address) {
        customerModel.setAddress(address);
    }

    public static void setPhoneNumber(String phoneNumber) {
        customerModel.setPhoneNumber(phoneNumber);
    }

    public static void  modifyCustomerAccount(String address, String email, String userName, String password, String phoneNumber) {
        customerModel.modifyCustomerAccount(address, email, userName, password, phoneNumber);
    
    }

    public static void AddCustomer(String username, String email, String password , String address, String phoneNumber) {
        customerModel.AddCustomer(username, email, password, address, phoneNumber);
        
    }
    
}