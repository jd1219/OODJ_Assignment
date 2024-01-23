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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class modifySalesOrder extends javax.swing.JFrame {

    /**
     * Creates new form modifySalesOrder
     */
    public modifySalesOrder() {
        initComponents();
        tableInitialize();
    }
    public static String[] row;
    public String personalID;
    private List<String[]> allLines = new ArrayList<>();
    
    
    public modifySalesOrder(String[] row){
        initComponents();
        modifySalesOrder.row = row;
        System.out.println("Modify Sales Order: "+ Arrays.toString(row));
        SalesPerson sp = new SalesPerson(row[0],row[1],row[3],row[6],row[5],row[4],row[2]);
        personalID = sp.getID();
        setLocationRelativeTo(null);
        tableInitialize();
        updateSOIDbox(personalID);
        
        cbSOID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedSOID = (String)cbSOID.getSelectedItem();
                    if(selectedSOID != null && !selectedSOID.isEmpty()){
                        updateTable(selectedSOID);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(modifySalesOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        orderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRowIndex = orderTable.getSelectedRow();

                    if (selectedRowIndex != -1) {
                        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();

                        Object productID = model.getValueAt(selectedRowIndex, 0); 
                        Object productName = model.getValueAt(selectedRowIndex, 1); 
                        Object productPrice = model.getValueAt(selectedRowIndex, 2); 
                        Object productQuantity = model.getValueAt(selectedRowIndex, 3); 
                        
                        cbProduct.setSelectedItem(productID.toString() + " " + productName.toString());
                        tfPrice.setText(productPrice.toString());
                        spinQuantity.setValue(Integer.valueOf(productQuantity.toString()));
                        System.out.println("Data from selected row: " + productID + ", " + productName + ", " + productPrice + ", " + productQuantity);
                    } else {
                        // No row is selected
                        System.out.println("No row selected.");
                    }
                }
            }
        });
        
        
        
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
        spinQuantity.setValue(1);
        
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
            tfPrice.setText(price);
        }else if(cbProduct.getSelectedIndex() == 0){
            selectionInitialize();
        }else{
            selectionInitialize();
        }
        
    }
    
    private void selectionInitialize(){
        tfPrice.setText("");
        cbProduct.setSelectedIndex(-1);
        spinQuantity.setValue(1);
        
    }

    
    private void tableInitialize(){
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Product ID
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Product
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Quantity
    }
    
    private void updateSOIDbox(String personalID){
        Set<String> uniqueSOIDs = new HashSet<>();
        
        try{
            FileReader fr = new FileReader("SalesOrder.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String regex = Pattern.quote(personalID) + "(\\d+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String currentSalesOrderID = matcher.group(0);
                    uniqueSOIDs.add(currentSalesOrderID);
                }
            }
        } catch (IOException ex) {

        }
        cbSOID.addItem("");
        for (String uniqueSOID : uniqueSOIDs) {
            cbSOID.addItem(uniqueSOID);
        }
    }
    
    private void updateTable(String selectedSOID) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader("SalesOrder.txt");
        BufferedReader br = new BufferedReader(fr);
        DefaultTableModel model = (DefaultTableModel)orderTable.getModel();
        model.setRowCount(0); // clearing all the rows
        String line;
        
        // adding the matched lines in txt file into table
        while((line = br.readLine()) != null){
            if(line.contains(selectedSOID)){
                System.out.println("Line:"+line);
                String[] lines = line.split(",");
                model.addRow(new Object[]{lines[1],lines[2],lines[3],lines[4]});
            }
            
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
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbSOID = new javax.swing.JLabel();
        cbSOID = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lbProduct = new javax.swing.JLabel();
        cbProduct = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lbPrice = new javax.swing.JLabel();
        tfPrice = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lbQuantity = new javax.swing.JLabel();
        spinQuantity = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        lbTitle = new javax.swing.JLabel();
        btAddNew = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Modify Sales Order");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jButton1.setText("Back");
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 40));

        jPanel2.setLayout(new java.awt.GridLayout(4, 0));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        lbSOID.setBackground(new java.awt.Color(204, 255, 255));
        lbSOID.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbSOID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSOID.setText("Sales Order ID:");
        jPanel3.add(lbSOID);

        cbSOID.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jPanel3.add(cbSOID);

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        lbProduct.setBackground(new java.awt.Color(204, 255, 255));
        lbProduct.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProduct.setText("Product:");
        jPanel4.add(lbProduct);

        cbProduct.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jPanel4.add(cbProduct);

        jPanel2.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        lbPrice.setBackground(new java.awt.Color(204, 255, 255));
        lbPrice.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPrice.setText("Price:");
        jPanel5.add(lbPrice);

        tfPrice.setEditable(false);
        tfPrice.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        tfPrice.setFocusable(false);
        jPanel5.add(tfPrice);

        jPanel2.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(204, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        lbQuantity.setBackground(new java.awt.Color(204, 255, 255));
        lbQuantity.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lbQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQuantity.setText("Quantity:");
        jPanel6.add(lbQuantity);

        spinQuantity.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        spinQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        jPanel6.add(spinQuantity);

        jPanel2.add(jPanel6);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 390, 330));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Product ID", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(orderTable);
        if (orderTable.getColumnModel().getColumnCount() > 0) {
            orderTable.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(552, 120, 530, -1));

        lbTitle.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        lbTitle.setText("Modify Sales Order");
        jPanel1.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 400, 60));

        btAddNew.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btAddNew.setText("Add New");
        btAddNew.setFocusPainted(false);
        btAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddNewActionPerformed(evt);
            }
        });
        jPanel1.add(btAddNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 120, 50));

        btSave.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btSave.setText("Save");
        btSave.setFocusPainted(false);
        btSave.setMaximumSize(new java.awt.Dimension(80, 23));
        btSave.setMinimumSize(new java.awt.Dimension(80, 23));
        btSave.setPreferredSize(new java.awt.Dimension(80, 23));
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 510, 100, 50));

        btEdit.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btEdit.setText("Edit");
        btEdit.setFocusPainted(false);
        btEdit.setMaximumSize(new java.awt.Dimension(80, 23));
        btEdit.setMinimumSize(new java.awt.Dimension(80, 23));
        btEdit.setPreferredSize(new java.awt.Dimension(80, 23));
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        jPanel1.add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, 100, 50));

        btDelete.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btDelete.setText("Delete");
        btDelete.setFocusPainted(false);
        btDelete.setMaximumSize(new java.awt.Dimension(80, 23));
        btDelete.setMinimumSize(new java.awt.Dimension(80, 23));
        btDelete.setPreferredSize(new java.awt.Dimension(80, 23));
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 510, 120, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1110, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        salesManageSales sms = new salesManageSales(modifySalesOrder.row);
        sms.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddNewActionPerformed
        // TODO add your handling code here:
        try{
            String productSelected = (String)cbProduct.getSelectedItem();
            String addingProduct = productSelected.split(" ",2)[1];
            String addingID = productSelected.split(" ",2)[0];
            String addingPrice = tfPrice.getText();
            String addingQuantity = spinQuantity.getValue().toString();
        
        
            DefaultTableModel model = (DefaultTableModel)orderTable.getModel();

            if((addingProduct != null) && (addingID != null) && (addingPrice != null)  && (addingQuantity != null)){
                // get the current table row count and get numbering for next row

                model.addRow(new Object[]{addingProduct, addingID, addingPrice, addingQuantity});
            }else {
                JOptionPane.showMessageDialog(null,"Something went wrong");
            }
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", HEIGHT);
        }
        selectionInitialize();  
    }//GEN-LAST:event_btAddNewActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        int selectedRow = orderTable.getSelectedRow();
        
        String productSelected = (String)cbProduct.getSelectedItem();
        String editProduct = productSelected.split(" ",2)[1];
        String editID = productSelected.split(" ",2)[0];
        String editPrice = tfPrice.getText();
        String editQuantity = spinQuantity.getValue().toString();
        
        model.setValueAt(editID,selectedRow,0);
        model.setValueAt(editProduct,selectedRow,1);
        model.setValueAt(editPrice,selectedRow,2);
        model.setValueAt(editQuantity,selectedRow,3);
        
        JOptionPane.showMessageDialog(null, "Edit Done, you can view on the table");
    }//GEN-LAST:event_btEditActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        // TODO add your handling code here:
        String filepath = "SalesOrder.txt";
        String SOID = (String)cbSOID.getSelectedItem();
        
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();
        List<Object[]> rowDataList = new ArrayList<>();
        
        
        for(int tbrow = 0;tbrow < rowCount; tbrow++){
            Object[] rowData = new Object[colCount + 2];
            rowData[0] = SOID;
            for(int col = 0; col < colCount; col++){
                rowData[col + 1] = model.getValueAt(tbrow, col);                
            }
            rowData[5] = "Unapproved";
            rowDataList.add(rowData);
        }
        
        for (Object[] rowData : rowDataList) {
            System.out.println(Arrays.toString(rowData));
            // Process or manipulate each row's data as needed
        }
        System.out.println("   ");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            List<String> fileLines = new ArrayList<>();
            
            String line;
            
            while((line = br.readLine()) != null){
                String [] lines = line.split(",");
                System.out.println(Arrays.toString(lines));
                
                if(lines.length > 0 && !lines[0].equals(SOID)){
                    fileLines.add(line);
                }
            }
            
            for(Object newRowData : rowDataList){
                String newRowString = String.join(",", Arrays.stream((Object[]) newRowData).map(Object::toString).toArray(String[]::new));
                fileLines.add(newRowString);
            }
            
//            for(String updatedLine : fileLines){
//              System.out.println("Hello" + updatedLine);
//            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            for(String updatedLine : fileLines){
                bw.write(updatedLine);
                bw.newLine();
            }
            bw.close();
            
            JOptionPane.showMessageDialog(null,"Changes saved to database");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(modifySalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(modifySalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSaveActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        
        int selectedRow = orderTable.getSelectedRow();
        model.removeRow(selectedRow);
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
            java.util.logging.Logger.getLogger(modifySalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modifySalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modifySalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modifySalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new modifySalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddNew;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox<String> cbProduct;
    private javax.swing.JComboBox<String> cbSOID;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbPrice;
    private javax.swing.JLabel lbProduct;
    private javax.swing.JLabel lbQuantity;
    private javax.swing.JLabel lbSOID;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable orderTable;
    private javax.swing.JSpinner spinQuantity;
    private javax.swing.JTextField tfPrice;
    // End of variables declaration//GEN-END:variables
}
