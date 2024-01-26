/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oodj_assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ManageWorkerProfile extends javax.swing.JFrame {

    private static String[] newRow;

    /**
     * Creates new form ManageWorkerProfile
     */
    public ManageWorkerProfile() {
        initComponents();
    }
    
    public static String[] row;
    public String personalID;
    
    public ManageWorkerProfile(String[] row) {
        initComponents();
        setLocationRelativeTo(null);
        ManageWorkerProfile.row = row;
        System.out.println("Manage Worker Profile: " + Arrays.toString(row));
        Admin ad = new Admin(row[0],row[1],row[3],row[6],row[5],row[4],row[2]);
        personalID = ad.getID();
        updateWorkerID(personalID);
        fieldInitialize();
        setFieldEditable(false);
        
        cbSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDetails();
            }
        });
    }
    
    
    // to update the combo box to select the worker ID
    private void updateWorkerID(String personalID){
        String filepath = "Accounts.txt";
        List<String> workerIDs = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                
                if(!lines[lines.length - 1].equals("a") && !lines[2].equals(personalID)){
                    workerIDs.add(lines[2]);
                    System.out.println(Arrays.toString(lines));
                }
            }
            
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbSelect.addItem("");
        for(String workerID : workerIDs){
            cbSelect.addItem(workerID);
        }
    }
    
    
    // this is to set the field to be editable by the user after clicked the Edit Button
    private void setFieldEditable(boolean editable){
        tfUsername.setEditable(editable);
        tfUsername.setFocusable(editable);
        tfPassword.setEditable(editable);
        tfPassword.setFocusable(editable);
        tfEmail.setEditable(editable);
        tfEmail.setFocusable(editable);
        tfGender.setEditable(editable);
        tfGender.setFocusable(editable);
        tfAge.setEditable(editable);
        tfAge.setFocusable(editable);
        tfContact.setEditable(editable);
        tfContact.setFocusable(editable);
        tfPID.setEditable(editable);
        tfPID.setFocusable(editable);
        tfLevel.setEditable(editable);
        tfLevel.setFocusable(editable);
    }
    
    // this method is to update the JTextField with the details of the worker
    private void updateDetails(){
        String workerID = (String)cbSelect.getSelectedItem();
        String filepath = "Accounts.txt";
        fieldInitialize();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(lines[2].equals(workerID)){
                    String accessLevel;
                    switch (lines[lines.length - 1]) {
                        case "s":
                            accessLevel = "Sales Person";
                            break;
                        case "o":
                            accessLevel = "Officer";
                            break;
                        default:
                            accessLevel = "Unknown";
                            break;
                    }
                    
                    tfPID.setText(lines[2]);
                    tfUsername.setText(lines[0]);
                    tfEmail.setText(lines[3]);
                    tfContact.setText(lines[6]);
                    tfAge.setText(lines[4]);
                    tfGender.setText(lines[5]);
                    tfPassword.setText(lines[1]);
                    tfLevel.setText(accessLevel);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void fieldInitialize(){
        tfPID.setText("");
        tfUsername.setText("");
        tfEmail.setText("");
        tfContact.setText("");
        tfAge.setText("");
        tfGender.setText("");
        tfPassword.setText("");
        tfLevel.setText("");
    }
    
    private void updateAccountDetails(String[] newRow) throws IOException{
        ManageWorkerProfile.newRow = newRow;
        System.out.println(Arrays.toString(newRow)); // to see what data is really taking
        
        // Read file
        String filepath = ("Accounts.txt");
        File file = new File(filepath);
        List<String> lines = new ArrayList<>();
        
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0;i < lines.size(); i++){
            String[] elements = lines.get(i).split(",");
            if(elements.length > 2 && elements[2].equals((String)cbSelect.getSelectedItem())){
                lines.set(i, String.join(",",newRow));
                break;
            }
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for(String line : lines){
                bw.write(line);
                bw.newLine();
            }
        }
        
        updateSalesDetails(newRow[2],(String)cbSelect.getSelectedItem());
    }
    
    private void updateSalesDetails(String newpersonalID,String oldpersonalID){
        System.out.println("Old Personal ID: " + oldpersonalID);
        System.out.println("New Personal ID: " + newpersonalID);
        
        String filepath = "SalesOrder.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            StringBuilder modifiedFileContent = new StringBuilder();
            
            String line;
            while((line = br.readLine()) != null){
                if(line.contains(oldpersonalID)){
                    System.out.println("XXX " + line);
                    line = line.replace(oldpersonalID, newpersonalID);
                }
                
                modifiedFileContent.append(line).append(System.lineSeparator());
            }
            
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
                bw.write(modifiedFileContent.toString());
            }
            
            JOptionPane.showMessageDialog(null, "Changes Saved");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static boolean verifyAvailability(String pID){
        String filepath = "SalesOrder.txt";
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            
            while((line = br.readLine()) != null){
                if(!line.contains(pID)){
                    return true;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btBack = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        lbPersonalID = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        lbGender = new javax.swing.JLabel();
        lbAge = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lbSelect = new javax.swing.JLabel();
        cbSelect = new javax.swing.JComboBox<>();
        btEdit = new javax.swing.JButton();
        tfLevel = new javax.swing.JTextField();
        tfPassword = new javax.swing.JTextField();
        tfAge = new javax.swing.JTextField();
        tfGender = new javax.swing.JTextField();
        tfContact = new javax.swing.JTextField();
        tfPID = new javax.swing.JTextField();
        tfUsername = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manage Worker Profile");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setMinimumSize(new java.awt.Dimension(520, 70));
        jPanel5.setPreferredSize(new java.awt.Dimension(740, 70));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btBack.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btBack.setText("Back");
        btBack.setFocusPainted(false);
        btBack.setFocusable(false);
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });
        jPanel5.add(btBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, 40));

        lbTitle.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Manage Worker Profile");
        jPanel5.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 320, 40));

        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(428, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(740, 80));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.LINE_START);

        mainPanel.setBackground(new java.awt.Color(204, 255, 255));
        mainPanel.setMinimumSize(new java.awt.Dimension(1638, 40));
        mainPanel.setPreferredSize(new java.awt.Dimension(300, 0));
        mainPanel.setLayout(new java.awt.GridBagLayout());

        lbPersonalID.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbPersonalID.setText("Personal ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(lbPersonalID, gridBagConstraints);

        lbEmail.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbEmail.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(lbEmail, gridBagConstraints);

        lbGender.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbGender.setText("Gender:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(lbGender, gridBagConstraints);

        lbAge.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbAge.setText("Age:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(lbAge, gridBagConstraints);

        lbPassword.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbPassword.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(lbPassword, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel7.setText("Contact:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel8.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(jLabel8, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel1.setText("Level:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(jLabel1, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(204, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(370, 50));
        jPanel6.setPreferredSize(new java.awt.Dimension(464, 50));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbSelect.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        lbSelect.setText("Please Select :");
        jPanel6.add(lbSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        cbSelect.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        cbSelect.setMinimumSize(new java.awt.Dimension(100, 35));
        cbSelect.setPreferredSize(new java.awt.Dimension(120, 30));
        cbSelect.setRequestFocusEnabled(false);
        jPanel6.add(cbSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        mainPanel.add(jPanel6, gridBagConstraints);

        btEdit.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btEdit.setText("Edit");
        btEdit.setFocusPainted(false);
        btEdit.setFocusable(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        mainPanel.add(btEdit, gridBagConstraints);

        tfLevel.setEditable(false);
        tfLevel.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfLevel.setFocusable(false);
        tfLevel.setMinimumSize(new java.awt.Dimension(100, 30));
        tfLevel.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfLevel, gridBagConstraints);

        tfPassword.setEditable(false);
        tfPassword.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfPassword.setFocusable(false);
        tfPassword.setMinimumSize(new java.awt.Dimension(100, 30));
        tfPassword.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfPassword, gridBagConstraints);

        tfAge.setEditable(false);
        tfAge.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfAge.setFocusable(false);
        tfAge.setMinimumSize(new java.awt.Dimension(100, 30));
        tfAge.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfAge, gridBagConstraints);

        tfGender.setEditable(false);
        tfGender.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfGender.setFocusable(false);
        tfGender.setMinimumSize(new java.awt.Dimension(100, 30));
        tfGender.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfGender, gridBagConstraints);

        tfContact.setEditable(false);
        tfContact.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfContact.setFocusable(false);
        tfContact.setMinimumSize(new java.awt.Dimension(100, 30));
        tfContact.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfContact, gridBagConstraints);

        tfPID.setEditable(false);
        tfPID.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfPID.setFocusable(false);
        tfPID.setMinimumSize(new java.awt.Dimension(100, 30));
        tfPID.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfPID, gridBagConstraints);

        tfUsername.setEditable(false);
        tfUsername.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        tfUsername.setFocusable(false);
        tfUsername.setMinimumSize(new java.awt.Dimension(100, 30));
        tfUsername.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfUsername, gridBagConstraints);

        tfEmail.setEditable(false);
        tfEmail.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        tfEmail.setFocusable(false);
        tfEmail.setMinimumSize(new java.awt.Dimension(100, 30));
        tfEmail.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        mainPanel.add(tfEmail, gridBagConstraints);

        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        AdminPanel ap = new AdminPanel(ManageWorkerProfile.row);
        ap.setVisible(true);
    }//GEN-LAST:event_btBackActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        boolean editable = !tfUsername.isEditable();
        setFieldEditable(editable);
        if(btEdit.getText().equals("Save")){
            
            // get all the new details except for Level and Personal ID
            String newPassword = tfPassword.getText();
            String newUsername = tfUsername.getText();
            String newPersonalID = tfPID.getText();
            String newEmail = tfEmail.getText();
            String newAge = tfAge.getText();
            String newGender = tfGender.getText();
            String newContact = tfContact.getText();
            String accesslevel = tfLevel.getText();
            String accessLevel = (accesslevel.equals("Sales Person")) ? "s" : (accesslevel.equals("Admin")) ? "a" : "o";
            
            // to prevent the new detail is empty
            if(!newPassword.isEmpty() && !newUsername.isEmpty() && !newEmail.isEmpty() && !newAge.isEmpty() && !newGender.isEmpty() && !newContact.isEmpty()){
                String [] newRows = {newUsername,newPassword,newPersonalID,newEmail,newAge,newGender,newContact,accessLevel};
                for(String info : newRows){
                    System.out.println(info);
                }
                
                boolean isAvailable = verifyAvailability(newPersonalID);
                
                if(isAvailable){
                    JOptionPane.showMessageDialog(null,"The Personal ID is available");
                    
                }else{
                    JOptionPane.showMessageDialog(null,"The Personal ID is used, please re-input");
                }
                
                try {
                    updateAccountDetails(newRows);
                } catch (IOException ex) {
                    Logger.getLogger(ManageWorkerProfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Please do not leave new password blank", newPassword, HEIGHT);
            }
            
        }
        if(editable == true){
            JOptionPane.showMessageDialog(null,"The access level will only be Sales Person and Admin (case sensitve)");
            btEdit.setText("Save");
        }else{
            btEdit.setText("Edit");
        }
    }//GEN-LAST:event_btEditActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageWorkerProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageWorkerProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageWorkerProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageWorkerProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageWorkerProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JButton btEdit;
    private javax.swing.JComboBox<String> cbSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lbAge;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbPersonalID;
    private javax.swing.JLabel lbSelect;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField tfAge;
    private javax.swing.JTextField tfContact;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfGender;
    private javax.swing.JTextField tfLevel;
    private javax.swing.JTextField tfPID;
    private javax.swing.JTextField tfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
