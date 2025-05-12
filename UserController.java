/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customer_support;

public class UserController {

    private static User userTech = new User();

    public UserController(User userTech) {
        this.userTech = userTech;
    }

    public static int authenticateUser(String userName, String password) {
        return userTech.authenticateUser(userName, password);
    }
}
