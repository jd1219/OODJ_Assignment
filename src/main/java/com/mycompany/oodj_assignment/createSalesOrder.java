/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oodj_assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class createSalesOrder extends javax.swing.JFrame {

    /**
     * Creates new form createSalesOrder
     */
    public createSalesOrder() {
        initComponents();
    }
    
    private static String[] row;
    
    private List<String[]> allLines = new ArrayList<>();
    public createSalesOrder(String[] row) {
        initComponents();
        createSalesOrder.row = row;
        System.out.println("Create Sales Order: "+ Arrays.toString(row));
        setLocationRelativeTo(null);
        tableInitialize();

        String productFile = "products.csv";

        try {
            FileReader fr = new FileReader(productFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] productInfo = line.split(",");
                allLines.add(productInfo);
            }

            updateComboBox(allLines);
            
            cbProduct.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedProduct;
                    selectedProduct = (String)cbProduct.getSelectedItem();
                    
                    if(selectedProduct != null){
                        updateProductDetails(selectedProduct);
                    }
                }
                
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(createSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(createSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void updateComboBox(List<String[]> allLines){
        // because the first line is header
        boolean skipFirstLine = true;
        for (String[] productInfo : allLines) {
            if(skipFirstLine){
                skipFirstLine = false;
                cbProduct.addItem("");
                continue;
            }
            cbProduct.addItem(productInfo[1] + " " + productInfo[2]); 
        }
    }   
    
    private void updateProductDetails(String selectedProduct){
        System.out.println("Selected Product " + selectedProduct);
        
        String[] parts;
        String productID;
        String productName;
        
        try{
            parts = selectedProduct.split(" ",2);
            productID = parts[0];
            productName = parts[1];
        }catch(ArrayIndexOutOfBoundsException e){
            productID = "";
            productName = "";
        }
        String[] productDetails = null;
        
        for(String[] lines : allLines){
            if(productID.equals(lines[1]) && productName.equals(lines[2])){
                productDetails = lines;
                break;
            }
        }
        
        if(productDetails != null){
            String price = productDetails[4];
            tfProductID.setText(productID);
            tfPrice.setText(price);
        }else if(cbProduct.getSelectedIndex() == 0){
            selectionInitialize();
        }else{
            selectionInitialize();
        }
        
    }
    
    private void selectionInitialize(){
        tfProductID.setText("");
        tfPrice.setText("");
        cbProduct.setSelectedIndex(-1);
        spinQuantity.setValue(1);
        
    }
    
    private void tableInitialize(){
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(30); 
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Product
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(100); // ID
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Price
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Quantity

    }
    
     private static int countRows(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String knp;
            int count = 0;
            while ((knp = br.readLine()) != null) {
                System.out.println(knp);
                count++;
            }
            return count;
        }
    }
     
    private static String generateNextSOID(String salesPersonID) throws IOException{
        String salesOrderIDFormat = salesPersonID + "%04d";
        String salesOrderFilePath = "SalesOrder.txt";

        int nextSalesOrderNumber = 1;
        int rowCount = countRows("SalesOrder.txt");
        System.out.println("Row count: " + rowCount);
        File salesOrderFile = new File(salesOrderFilePath);

        if (salesOrderFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(salesOrderFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String regex = Pattern.quote(salesPersonID) + "(\\d+)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line);

                    System.out.println("salesPersonID: " + salesPersonID);
                    System.out.println("salesOrderIDFormat: " + salesOrderIDFormat);

                    if (matcher.find()) {
                        int currentSalesOrderNumber = Integer.parseInt(matcher.group(1));
                        System.out.println("Matched line: " + line);
                        System.out.println("Current Sales Order Number: " + currentSalesOrderNumber);
                        nextSalesOrderNumber = Math.max(nextSalesOrderNumber, currentSalesOrderNumber + 1);
                    }
                }
            }catch (IOException e){
            }
            
        }

        return String.format(salesOrderIDFormat, nextSalesOrderNumber);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        ProductPanel = new javax.swing.JPanel();
        lbProduct = new javax.swing.JLabel();
        cbProduct = new javax.swing.JComboBox<>();
        NamePanel = new javax.swing.JPanel();
        lbProductID = new javax.swing.JLabel();
        tfProductID = new javax.swing.JTextField();
        PricePanel = new javax.swing.JPanel();
        lbPrice = new javax.swing.JLabel();
        tfPrice = new javax.swing.JTextField();
        QuantityPanel = new javax.swing.JPanel();
        lbQuantity = new javax.swing.JLabel();
        spinQuantity = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        btCreate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Sales Order");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Product", "Product ID", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.setRowHeight(40);
        orderTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(orderTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 620, 370));

        jPanel2.setLayout(new java.awt.GridLayout(4, 1));

        ProductPanel.setBackground(new java.awt.Color(204, 255, 255));
        ProductPanel.setLayout(new java.awt.GridLayout(1, 3));

        lbProduct.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProduct.setText("Product:");
        ProductPanel.add(lbProduct);

        cbProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProductActionPerformed(evt);
            }
        });
        ProductPanel.add(cbProduct);

        jPanel2.add(ProductPanel);

        NamePanel.setBackground(new java.awt.Color(204, 255, 255));
        NamePanel.setLayout(new java.awt.GridLayout(1, 0));

        lbProductID.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbProductID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProductID.setText("Product ID:");
        NamePanel.add(lbProductID);

        tfProductID.setEditable(false);
        tfProductID.setBackground(new java.awt.Color(255, 255, 255));
        tfProductID.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        tfProductID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NamePanel.add(tfProductID);

        jPanel2.add(NamePanel);

        PricePanel.setBackground(new java.awt.Color(204, 255, 255));
        PricePanel.setLayout(new java.awt.GridLayout(1, 0));

        lbPrice.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPrice.setText("Price:");
        PricePanel.add(lbPrice);

        tfPrice.setEditable(false);
        tfPrice.setBackground(new java.awt.Color(255, 255, 255));
        tfPrice.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        tfPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PricePanel.add(tfPrice);

        jPanel2.add(PricePanel);

        QuantityPanel.setBackground(new java.awt.Color(204, 255, 255));
        QuantityPanel.setLayout(new java.awt.GridLayout(1, 0));

        lbQuantity.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQuantity.setText("Quantity:");
        QuantityPanel.add(lbQuantity);

        spinQuantity.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        spinQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        QuantityPanel.add(spinQuantity);

        jPanel2.add(QuantityPanel);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 460, 270));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        jLabel1.setText("Create Sales Order");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));

        jButton1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jButton1.setText("Back");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 100, -1));

        btAdd.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btAdd.setText("Add");
        btAdd.setFocusPainted(false);
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });
        jPanel1.add(btAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 120, 50));

        btCreate.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btCreate.setText("Create");
        btCreate.setFocusPainted(false);
        btCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCreateActionPerformed(evt);
            }
        });
        jPanel1.add(btCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 120, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1230, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        salesManageSales sms = new salesManageSales(createSalesOrder.row);
        sms.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbProductActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:
        
        String productSelected = (String)cbProduct.getSelectedItem();
        String addingProduct = productSelected.split(" ",2)[1];
        String addingID = tfProductID.getText();
        String addingPrice = tfPrice.getText();
        String addingQuantity = spinQuantity.getValue().toString();
        
        DefaultTableModel model = (DefaultTableModel)orderTable.getModel();
        
        if((addingProduct != null) && (addingID != null) && (addingPrice != null)  && (addingQuantity != null)){
            // get the current table row count and get numbering for next row
            int rowCount = model.getRowCount();
            int nextRowNo = rowCount + 1;
            
            model.addRow(new Object[]{nextRowNo, addingProduct, addingID, addingPrice, addingQuantity});
        }else {
            JOptionPane.showMessageDialog(null,"Something went wrong");
        }
        
        selectionInitialize();     
    }//GEN-LAST:event_btAddActionPerformed

    private void btCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCreateActionPerformed
        // TODO add your handling code here:
        System.out.println(Arrays.toString(row));
        SalesPerson sp = new SalesPerson(row[0],row[1],row[3],row[6],row[5],row[4],row[2]);
        String personalID = sp.getID();
        
        DefaultTableModel model = (DefaultTableModel)orderTable.getModel();
        String filepath = "SalesOrder.txt";
        Object[] rowData;
        String nextSOID = null;
        // loop through the whole table to get the rowData
        if(model.getRowCount() > 0){
            try {
                File file = new File(filepath);
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                
                for(int i = 0; i < model.getRowCount(); i++){
                    rowData = new Object[model.getColumnCount() - 1];
                    for(int j = 0; j < model.getColumnCount() - 1; j++){
                        rowData[j] = model.getValueAt(i,j + 1);
                    }
            
                    try {
                        nextSOID = generateNextSOID(personalID);
                        System.out.println("Sales ID:" + nextSOID);

                        bw.write(nextSOID);

                        for(Object data : rowData){
                            bw.write("," + data);
                        }
                        
                        bw.write(",In Progress,Unapproved");
                        bw.newLine();
                    }catch (IOException ex){
                        Logger.getLogger(createSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(createSalesOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null,"Sales Order ID "+ nextSOID +" has been created!!");
        }else{
            JOptionPane.showMessageDialog(null,"Please Add Products before create Sales Order!");
        }
    }//GEN-LAST:event_btCreateActionPerformed

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
            java.util.logging.Logger.getLogger(createSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(createSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(createSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(createSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new createSalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NamePanel;
    private javax.swing.JPanel PricePanel;
    private javax.swing.JPanel ProductPanel;
    private javax.swing.JPanel QuantityPanel;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btCreate;
    private javax.swing.JComboBox<String> cbProduct;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbPrice;
    private javax.swing.JLabel lbProduct;
    private javax.swing.JLabel lbProductID;
    private javax.swing.JLabel lbQuantity;
    private javax.swing.JTable orderTable;
    private javax.swing.JSpinner spinQuantity;
    private javax.swing.JTextField tfPrice;
    private javax.swing.JTextField tfProductID;
    // End of variables declaration//GEN-END:variables
}
