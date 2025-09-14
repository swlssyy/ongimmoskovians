/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hellskitchen;

import java.util.ArrayList;


/**
 *
 * @author Students
 */
public class users {
    
    public static ArrayList<String> username = new ArrayList<>();
    public static ArrayList<String> password = new ArrayList<>();

    public static String adminPassword = "Confirm"; // You can change this

    static {
        // Sample default user
        username.add("frein");
        password.add("Confirm");
    }
    
}
