/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oodj_assignment;

/**
 *
 * @author User
 */
public class SalesOrder {
    private double total;
    private String date;
    
    public SalesOrder(double total, String date){
        this.total = total;
        this.date = date;
    }
    
    public double getTotal(){
        return total;
    }
    
    public String getDate(){
        return date;
    }
}
