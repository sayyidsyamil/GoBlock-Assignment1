package com.mycompany.pricetracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class Cart extends javax.swing.JFrame {
    
    public static String publicPath = Relate.localFilePath;

    public List<String[]> itemDetails = readCSVData();

  

     public Cart() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        displayCartData();
        loadDistricts();
    }
     
     private void loadDistricts() {
        try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
            // Fetch unique districts from the SQL SELECT query
            String query = "SELECT DISTINCT district FROM lookup_premise";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Clear existing items in the JComboBox
                jComboBox1.removeAllItems();

                // Add an option for All Districts
                jComboBox1.addItem("All Districts");

                // Add fetched districts to the JComboBox
                while (resultSet.next()) {
                    String district = resultSet.getString("district");
                    jComboBox1.addItem(district);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
     
   private void displayCartData() {
        try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
            String query = "SELECT * FROM shopping_cart WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Login.userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);  // Clear existing rows

                    while (resultSet.next()) {
                        int cartId = resultSet.getInt("cart_id");
                        String itemCode = Relate.getItemNameFromCode(resultSet.getString("item_code"));
                        int quantity = resultSet.getInt("quantity");
                        String unit = Relate.getUnitFromCode(resultSet.getString("item_code"));

                        // Add the data to the table
                        model.addRow(new Object[]{cartId, itemCode, unit,quantity});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GOBLOCK Price Tracker");
        setResizable(false);

        jTable1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Id", "Item", "Unit", "Quantity"
            }
        ));
        jTable1.setFocusable(false);
        jScrollPane3.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(211, 228, 186));
        jButton1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office building 1.png"))); // NOI18N
        jButton1.setText("Get best shop");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 172, 172));
        jButton2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/waste basket 1.png"))); // NOI18N
        jButton2.setText("Remove");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 226, 172));
        jButton3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/money bag 1.png"))); // NOI18N
        jButton3.setText("Get best price");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel1.setText("Your Shopping Cart");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gOblock.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel3.setText("View and modify your loved item");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int[] selectedRows = jTable1.getSelectedRows();

        if (selectedRows.length == 0) {
            // Show a message to inform the user to select items to remove
            JOptionPane.showMessageDialog(this, "Please select items to remove from the cart.");
            return;
        }

        // Remove selected items from the database
        String deleteQuery = "DELETE FROM shopping_cart WHERE cart_id = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            for (int selectedRow : selectedRows) {
                int cartId = (int) model.getValueAt(selectedRow, 0);
                deleteStatement.setInt(1, cartId);
                deleteStatement.executeUpdate();
            }
        }

        // Refresh the displayed cart data after removal
        displayCartData();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception (e.g., show an error message)
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         int[] selectedRows = jTable1.getSelectedRows();

    if (selectedRows.length == 0) {
        // Show a message to inform the user to add items to the cart first
        JOptionPane.showMessageDialog(this, "Please select item from the cart first.");
        return;
    }

    // Get the item codes from the selected rows in the table
    List<String> selectedItems = new ArrayList<>();
    for (int selectedRow : selectedRows) {
        String itemCode = Relate.getItemCodeFromNameAndUnit(jTable1.getValueAt(selectedRow, 1).toString(), jTable1.getValueAt(selectedRow, 2).toString());
        selectedItems.add(itemCode);
    }

    // Get the cheapest seller for all selected items
    String bestShopId = getBestShop(selectedItems);

    StringBuilder detailsText = new StringBuilder("The best shop to get the selected item is:\n\n");
    detailsText.append("Name:" + Relate.getPremiseNameFromCode(bestShopId) + "\n");
    detailsText.append("Address:" + Relate.getPremiseAddressFromCode(bestShopId) + "\n");
    detailsText.append("District:" + Relate.getPremiseDistrictFromCode(bestShopId) + "\n");
    detailsText.append("State:" + Relate.getPremiseStateFromCode(bestShopId) + "\n");
    detailsText.append("Type:" + Relate.getPremiseTypeFromCode(bestShopId) + "\n");

    jTextArea1.setText(detailsText.toString());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int[] selectedRows = jTable1.getSelectedRows();

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "Please select an item from the cart first.");
        return;
    }

    // Display the best prices and sellers in the JTextArea for each selected row
    StringBuilder detailsText = new StringBuilder("The best prices and sellers for selected items are:\n\n");

    for (int selectedRow : selectedRows) {
        String itemCode = Relate.getItemCodeFromNameAndUnit(
                jTable1.getValueAt(selectedRow, 1).toString(),
                jTable1.getValueAt(selectedRow, 2).toString());

        String selectedDistrict = jComboBox1.getSelectedItem().toString();
        String bestPriceAndSellerText = getBestPriceAndSeller(itemCode, selectedDistrict);

        detailsText.append(bestPriceAndSellerText);
    }

    jTextArea1.setText(detailsText.toString());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    
private String getBestShop(List<String> itemCodes) {
    String selectedDistrict = jComboBox1.getSelectedItem().toString();

    // Check if "All Districts" is selected
    boolean considerAllDistricts = selectedDistrict.equals("All Districts");

    List<String> selectedDistrictCodes = Relate.getPremiseCodesFromDistrict(selectedDistrict);

    try {
        // Create a comma-separated list of items for matching with CSV data
        String itemCodesStr = itemCodes.stream().collect(Collectors.joining("','", "'", "'"));

        // Filter CSV data based on selected items and district
        List<String[]> filteredData = itemDetails.stream()
                .skip(1) // Skip the header row
                .filter(entry -> {
                    boolean isInDistrict = considerAllDistricts || selectedDistrictCodes.contains(entry[1] + ".0");
                    boolean containsItem = itemCodesStr.contains(entry[2]);
                    return containsItem && isInDistrict;
                })
                .collect(Collectors.toList());

        // Find the shop with the lowest total price
        String bestShopInfo = calculateBestShopForItems(filteredData, itemCodes);

        return bestShopInfo;

    } catch (Exception e) {
        e.printStackTrace();
        // Handle the exception (e.g., show an error message)
    }

    return null; // Return null if an error occurs
}


private String calculateBestShopForItems(List<String[]> filteredData, List<String> selectedItems) {
    try {
        if (filteredData.isEmpty() || selectedItems.isEmpty()) {
            System.out.println("No shops or selected items found in the given data.");
            return null;
        }

        // Filter data for the selected items
        List<String[]> itemData = filteredData.stream()
                .filter(entry -> selectedItems.contains(entry[2]))
                .collect(Collectors.toList());

        if (filteredData.isEmpty()) {
            System.out.println("No shops found for the selected items.");
            return null;
        }

        // Initialize variables to store overall best shop information
        String bestShopCode = null;
        double lowestPrice = Double.MAX_VALUE;

        // Iterate through each shop and find the cheapest entry for each item
        for (String[] shopData : itemData) {
            String shopCode = shopData[1];

            // Filter data for the current shop
            List<String[]> shopEntries = itemData.stream()
                    .filter(entry -> entry[1].equals(shopCode))
                    .collect(Collectors.toList());

            // Initialize variables to store cheapest entry information for each item in the current shop
            String cheapestItemCode = null;
            double cheapestItemPrice = Double.MAX_VALUE;

            // Iterate through each selected item and find the cheapest entry for that item in the current shop
            for (String itemCode : selectedItems) {
                List<String[]> itemEntries = shopEntries.stream()
                        .filter(entry -> entry[2].equals(itemCode))
                        .collect(Collectors.toList());

                if (!itemEntries.isEmpty()) {
                    double itemPrice = Double.parseDouble(itemEntries.get(0)[3]);

                    // Update cheapest item information if the current item has a lower price
                    if (itemPrice < cheapestItemPrice) {
                        cheapestItemCode = itemCode;
                        cheapestItemPrice = itemPrice;
                    }
                }
            }

            if (cheapestItemCode != null) {
                // Calculate the total price for the current shop
                double totalPrice = shopEntries.stream()
                        .mapToDouble(entry -> Double.parseDouble(entry[3]))
                        .sum();


                // Update overall best shop information if the current shop has a lower total price
                if (totalPrice < lowestPrice || (totalPrice == lowestPrice && shopCode.compareTo(bestShopCode) > 0)) {
                    lowestPrice = totalPrice;
                    bestShopCode = shopCode;
                }
            }
        }

        // Create and return a string array with bestShopCode and lowestPrice
        return bestShopCode;

    } catch (NumberFormatException e) {
        e.printStackTrace();
        // Handle the exception (e.g., show an error message)
    }

    return null; // Return null if an error occurs
}



private String getBestPriceAndSeller(String selectedItem, String selectedDistrict) {
    boolean considerAllDistricts = "All Districts".equals(selectedDistrict);
    List<String> selectedDistrictCodes = Relate.getPremiseCodesFromDistrict(selectedDistrict);

    try {
        List<String[]> filteredData = itemDetails.stream()
                .skip(1)
                .filter(entry -> {
                    boolean isInDistrict = considerAllDistricts || selectedDistrictCodes.contains(entry[1] + ".0");
                    boolean isItemSelected = selectedItem.equals(entry[2]);
                    return isItemSelected && isInDistrict;
                })
                .sorted(Comparator.comparingDouble(entry -> Double.parseDouble(entry[3])))
                .collect(Collectors.toList());

        if (!filteredData.isEmpty()) {
            String[] bestPriceAndSeller = filteredData.get(0);
            String premiseId = bestPriceAndSeller[1];
            double price = Double.parseDouble(bestPriceAndSeller[3]);
            String premiseAddress = Relate.getPremiseAddressFromCode(premiseId);

            return "\nItem: " + Relate.getItemNameFromCode(selectedItem) + "\n"
                    + "Unit: " + Relate.getUnitFromCode(selectedItem) + "\n"
                    + "Shop: " + Relate.getPremiseNameFromCode(premiseId) + "\n"
                    + "Price: RM" + price + "\n"
                    + "Address: " + premiseAddress + "\n";
        } else {
            return "\nNo information available for the selected item.\n";
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "An error occurred while retrieving information.\n";
    }
}


    private List<String[]> readCSVData() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(publicPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
        return records;
    }

    
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
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cart().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}

