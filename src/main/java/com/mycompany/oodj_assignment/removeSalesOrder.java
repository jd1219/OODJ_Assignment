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
public class removeSalesOrder extends javax.swing.JFrame {

    /**
     * Creates new form removeSalesOrder
     */
    public removeSalesOrder() {
        initComponents();
    }
    
    public static String[] row;
    public String personalID;
    
    public removeSalesOrder(String[] row){
        initComponents();
        removeSalesOrder.row = row;
        setLocationRelativeTo(null);
        System.out.println("Remove Sales Order: " + Arrays.toString(row));
        SalesPerson sp = new SalesPerson(row[0],row[1],row[3],row[6],row[5],row[4],row[2]);
        personalID = sp.getID();
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
        
        int rowCount = model.getRowCount();
        double[] rowTotals = new double[rowCount];
        double grandTotal = 0.00;
        for(int i = 0; i < rowCount; i++){
            double price = Double.parseDouble(model.getValueAt(i,2).toString());
            int quantity = Integer.parseInt(model.getValueAt(i, 3).toString());
            
            double rowTotal = price * quantity;
            rowTotals[i] = rowTotal;
            grandTotal += rowTotal;
        }
        String formattedTotal = String.format("%.2f",grandTotal);
        
        tfTotal.setText("RM " + String.valueOf(formattedTotal));
        
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
                if (fields.length > 0 && fields[0].startsWith(personalID)){
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
        cbSOID = new javax.swing.JComboBox<>();
        lbTitle = new javax.swing.JLabel();
        lbSalesOrder = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        btDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tfTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Remove Sales Order");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(710, 700));
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
        jPanel1.add(btBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 100, 40));

        cbSOID.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        cbSOID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSOIDActionPerformed(evt);
            }
        });
        jPanel1.add(cbSOID, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 180, 40));

        lbTitle.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Remove Sales Order");
        jPanel1.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 420, 40));

        lbSalesOrder.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lbSalesOrder.setText("Sales Order :");
        jPanel1.add(lbSalesOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 620, 370));

        btDelete.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btDelete.setText("Delete");
        btDelete.setFocusPainted(false);
        btDelete.setFocusable(false);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 630, 140, -1));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setText("Total :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 80, -1));

        tfTotal.setEditable(false);
        tfTotal.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        tfTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTotal.setFocusable(false);
        jPanel1.add(tfTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, 170, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        salesManageSales sms = new salesManageSales(removeSalesOrder.row);
        sms.setVisible(true);
    }//GEN-LAST:event_btBackActionPerformed

    private void cbSOIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSOIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSOIDActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        String SOID = (String)cbSOID.getSelectedItem();
        String filepath = "SalesOrder.txt";
        
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            List<String> fileLines = new ArrayList<>();
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(!lines[0].equals(SOID)){
                    fileLines.add(line);
                }
            }
//            for(String xxx : fileLines){
//                System.out.println(xxx);
//            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
                for(String updatedLine : fileLines){
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
            
            JOptionPane.showMessageDialog(null,"Sales Order Deleted Successfully");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(removeSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableInitialize();
    }//GEN-LAST:event_btDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(removeSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(removeSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(removeSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(removeSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new removeSalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JButton btDelete;
    private javax.swing.JComboBox<String> cbSOID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSalesOrder;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField tfTotal;
    // End of variables declaration//GEN-END:variables
}
