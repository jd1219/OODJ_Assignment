/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oodj_assignment;

/**
 *
 * @author User
 */
// Inheritance
public class SalesPerson extends User{
    // Encapsulation
    private String username;
    private String password;
    private String email;
    private String contact;
    private String age;
    private String gender;
    private final String personalID;
    
    public SalesPerson(String username, String password, String email, String contact, String age, String gender, String personalID) {
        super(username, password);
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
        this.personalID = personalID;
    }
    
    @Override
    public String getName(){
        return super.getName();
    }
    
    public void setName(String newUsername){
        this.username = newUsername;
    }
    
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    
    public String getContact(){
        return contact;
    }
    
    public void setContact(String newContact){
        this.contact = newContact;
    }
    
    public String getAge(){
        return age;
    }
    
    public void setAge(String newAge){
        this.age = newAge;
    }
    
    public String getGender(){
        return gender;
    }
    
    public void setGender(String newGender){
        this.gender = newGender;
    }
    
    public String getID(){
        return personalID;
    }
    
}
