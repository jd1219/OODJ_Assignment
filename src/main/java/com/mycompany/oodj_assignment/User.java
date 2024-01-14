/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oodj_assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class User {
    private String username;
    private String password;
    private String access;
    
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }    
    public String verifyAccount() throws IOException{
        
        // Read file to verify account
        String filepath = ("Accounts.txt");
        File file = new File(filepath);
        
        //verication
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] row = line.split(",");
                if (username.equals(row[0]) && password.equals(row[1])) {
                    access = row[2];
                    return access;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
        
    }
    
    public String getName(){
        return username;
    } 
}
