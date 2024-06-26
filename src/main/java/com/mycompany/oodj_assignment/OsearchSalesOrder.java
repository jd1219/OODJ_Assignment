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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class OsearchSalesOrder extends javax.swing.JFrame {

    /**
     * Creates new form OsearchSalesOrder
     */
    public OsearchSalesOrder() {
        initComponents();
    }
    
    public static String[] row;
    
    public OsearchSalesOrder(String[] row) {
        OsearchSalesOrder.row = row;
        initComponents();
        setLocationRelativeTo(null);
        System.out.println("Officer Search Sales Order: " + Arrays.toString(row));
        tableInitialize();
        updateSOIDOptions();
        
        cbSOID.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        
        });
    }
    
    private void tableInitialize(){
        cbSOID.setSelectedIndex(-1);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Product ID
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Product
        productTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
        productTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Quantity
    }
    
    private void updateTable(){
        String SOID = (String)cbSOID.getSelectedItem();
        System.out.println(SOID);
        String filepath = "SalesOrder.txt";
        DefaultTableModel model = (DefaultTableModel)productTable.getModel();
        model.setRowCount(0);

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String line;
            List<String[]> productsSOID = new ArrayList<>();
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(lines[0].equals(SOID)){
                    productsSOID.add(lines);
                    switch (lines[lines.length - 1]) {
                        case "Unapproved":
                            btSet.setText("Set Approved");
                            break;
                        case "Approved":
                            btSet.setText("Set Closed");
                            break;
                        case "Closed":
                            btSet.setText("Set Closed");
                            break;
                        default:
                            break;
                    }
                }
            }

            for(String[] product : productsSOID){
                model.addRow(new Object[]{ product[1], product[2], product[3], product[4]});
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        btSet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search Sales Order");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(btBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 100, 40));

        lbTitle.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        lbTitle.setText("Modify Sales Order");
        jPanel1.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        cbSOID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(cbSOID, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 190, 40));

        productTable.setFont(new java.awt.Font("Segoe UI Historic", 0, 12)); // NOI18N
        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 640, 360));

        btSet.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btSet.setText("Set Approve");
        btSet.setFocusPainted(false);
        btSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetActionPerformed(evt);
            }
        });
        jPanel1.add(btSet, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 540, 210, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        OfficerPanel pso = new OfficerPanel(OsearchSalesOrder.row);
        pso.setVisible(true);
    }//GEN-LAST:event_btBackActionPerformed

    private void btSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetActionPerformed
        // TODO add your handling code here:
        String filepath = "SalesOrder.txt";
        String SOID = (String)cbSOID.getSelectedItem();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            StringBuilder modifiedFileContent = new StringBuilder();

            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                
                if(lines[0].equals(SOID)){
                    if(btSet.getText().equals("Set Approved")){
                        line = line.replace(lines[lines.length - 1], "Approved");
                    }else if(btSet.getText().equals("Set Closed")){
                        line = line.replace(lines[lines.length - 1], "Closed");
                    }
                }
                modifiedFileContent.append(line).append(System.lineSeparator());
                    
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
                bw.write(modifiedFileContent.toString());
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OsearchSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OsearchSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(null,"Changes Saved!! Please Proceed");
    }//GEN-LAST:event_btSetActionPerformed

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
            java.util.logging.Logger.getLogger(OsearchSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OsearchSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OsearchSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OsearchSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OsearchSalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JButton btSet;
    private javax.swing.JComboBox<String> cbSOID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable productTable;
    // End of variables declaration//GEN-END:variables
}
