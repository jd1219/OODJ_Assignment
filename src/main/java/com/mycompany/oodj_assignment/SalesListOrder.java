/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oodj_assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class SalesListOrder extends javax.swing.JFrame {

    /**
     * Creates new form SalesListOrder
     */
    public SalesListOrder() {
        initComponents();
    }
    
    public static String[] row;
    public String personalID;
    
    public SalesListOrder(String[] row) {
        initComponents();
        SalesListOrder.row = row;
        setLocationRelativeTo(null);
        System.out.println("List All Sales Order: "+ Arrays.toString(row));
        SalesPerson sp = new SalesPerson(row[0],row[1],row[3],row[6],row[5],row[4],row[2]);
        personalID = sp.getID();
        tableInitialize();
        updateTable(personalID);
    }
    
    private void tableInitialize(){
        soTable.getColumnModel().getColumn(0).setPreferredWidth(150); 
        soTable.getColumnModel().getColumn(1).setPreferredWidth(150);
    }
    
    private void updateTable(String personalID){
        String filepath = "SalesOrder.txt";
        DefaultTableModel model = (DefaultTableModel)soTable.getModel();
        model.setRowCount(0);
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            Set<String> SOinfo = new HashSet<>();
            
            String line;
            
            while((line = br.readLine()) != null){
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].startsWith(personalID)){
                    if(!SOinfo.contains(fields[0])){
                        // to prevent from repeated Sales Order ID is added
                        SOinfo.add(fields[0]);
                        SOinfo.add(fields[5]);
                        
                        model.addRow(new Object[]{fields[0],fields[5]});
                    }
                }
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        soTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("List All Order");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btBack.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btBack.setText("Back");
        btBack.setFocusPainted(false);
        btBack.setFocusable(false);
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });
        jPanel1.add(btBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 100, -1));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setText("All Sales Order and Status");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        soTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales Order ID", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        soTable.setFocusable(false);
        soTable.setRowHeight(30);
        jScrollPane1.setViewportView(soTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 530, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        SalesPanel sp = new SalesPanel(SalesListOrder.row);
        sp.setVisible(true);
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
            java.util.logging.Logger.getLogger(SalesListOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesListOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesListOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesListOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesListOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable soTable;
    // End of variables declaration//GEN-END:variables
}
