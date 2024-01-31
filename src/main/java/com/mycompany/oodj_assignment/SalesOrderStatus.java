/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oodj_assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SalesOrderStatus extends javax.swing.JFrame {

    /**
     * Creates new form SalesOrderStatus
     */
    public SalesOrderStatus() {
        initComponents();
    }
    
    public static String[] row;
    
    public SalesOrderStatus(String[] row){
        initComponents();
        setLocationRelativeTo(null);
        SalesOrderStatus.row = row;
        System.out.println("Sales Order Status: " + Arrays.toString(row));
        updateSOIDOptions();
        
        cbSOID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatusField();
            }
        });
        
    }
    
    private void updateStatusField(){
        String filepath = "SalesOrder.txt";
        String SOID = (String)cbSOID.getSelectedItem();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(lines[0].equals(SOID)){
                    tfStatus.setText(lines[lines.length - 2]);
                    if(lines[lines.length - 2].equals("In Progress")){
                        btSet.setText("Set Work Done");
                    }else if(lines[lines.length - 2].equals("Work Done")){
                        btSet.setText("Set In Progress");
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesOrderStatus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalesOrderStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateSOIDOptions(){
        String filepath = "SalesOrder.txt";
        cbSOID.addItem("");
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            Set<String> soidOptions = new HashSet<>();
            
            String line;
            
            while((line = br.readLine()) != null){
                String[] fields = line.split(",");
                if (fields.length > 0){
                    soidOptions.add(fields[0]);
                }
            }
            
            for(String soid : soidOptions){
                cbSOID.addItem(soid);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btBack = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        cbSOID = new javax.swing.JComboBox<>();
        tfStatus = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btSet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sales Order Status");

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btBack.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btBack.setText("Back");
        btBack.setFocusPainted(false);
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });
        jPanel1.add(btBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 110, 40));

        lbTitle.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        lbTitle.setText("Sales Order Status");
        jPanel1.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        cbSOID.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jPanel1.add(cbSOID, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 170, 40));

        tfStatus.setEditable(false);
        tfStatus.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        tfStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, 250, 40));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel1.setText("Sales Order Status");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel2.setText("Sales Order ID");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        btSet.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btSet.setText("Set In Progress");
        btSet.setFocusPainted(false);
        btSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetActionPerformed(evt);
            }
        });
        jPanel1.add(btSet, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 240, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetActionPerformed
        // TODO add your handling code here:
        String filepath = "SalesOrder.txt";
        String SOID = (String)cbSOID.getSelectedItem();
            
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            StringBuilder modifiedStatusContent = new StringBuilder();
            
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(lines[0].equals(SOID)){
                    if(lines[lines.length - 2].equals("In Progress")){
                        line = line.replace(lines[lines.length - 2], "Work Done");
                    }else if(lines[lines.length - 2].equals("Work Done")){
                        line = line.replace(lines[lines.length - 2], "In Progress");
                    }
                }
                modifiedStatusContent.append(line).append(System.lineSeparator());
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
                bw.write(modifiedStatusContent.toString());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesOrderStatus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalesOrderStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btSetActionPerformed

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        OfficerPanel op = new OfficerPanel(SalesOrderStatus.row);
        op.setVisible(true);
    }//GEN-LAST:event_btBackActionPerformed

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
            java.util.logging.Logger.getLogger(SalesOrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesOrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesOrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesOrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesOrderStatus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JButton btSet;
    private javax.swing.JComboBox<String> cbSOID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextField tfStatus;
    // End of variables declaration//GEN-END:variables
}