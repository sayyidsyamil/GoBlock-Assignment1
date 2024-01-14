package com.mycompany.pricetracker;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.swing.tree.TreeSelectionModel;

public class Category extends javax.swing.JFrame {


    private JTree jTree;

    public static String publicPath = Relate.localFilePath;


    public Category(String publicPath) {
    this.publicPath = publicPath;
    initComponents();
    setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
    populateTree();
}

    private void populateTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("ITEM GROUPS");
        Set<String> itemGroups = readItemGroupsFromDatabase();

        for (String itemGroup : itemGroups) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(itemGroup);
            Set<String> itemCategories = readItemCategoriesFromDatabase(itemGroup);

            for (String itemCategory : itemCategories) {
                DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(itemCategory);
                Set<String> items = readItemsFromDatabase(itemGroup, itemCategory);
                // Sort the itemDetails by price in ascending order
                

                for (String item : items) {
                    categoryNode.add(new DefaultMutableTreeNode(item));
                }

                groupNode.add(categoryNode);
            }

            rootNode.add(groupNode);
        }

        jTree = new JTree(rootNode);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    // Add a TreeSelectionListener to the JTree
    jTree.addTreeSelectionListener(e -> {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        // Check if the selected node is a leaf (item)
        if (selectedNode != null && selectedNode.isLeaf()) {
            String selectedItem = selectedNode.getUserObject().toString();
        }
    });
        jScrollPane1.setViewportView(jTree);
    }

    private Set<String> readItemGroupsFromDatabase() {
    Set<String> itemGroups = new TreeSet<>(Comparator.naturalOrder());

    try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        String query = "SELECT DISTINCT item_group FROM lookup_item";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String itemGroup = resultSet.getString("item_group");
                itemGroups.add(itemGroup);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return itemGroups;
}


    private Set<String> readItemCategoriesFromDatabase(String itemGroup) {
        Set<String> itemCategories = new TreeSet<>(Comparator.naturalOrder());

        try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
            String query = "SELECT DISTINCT item_category FROM lookup_item WHERE item_group = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, itemGroup);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String itemCategory = resultSet.getString("item_category");
                        itemCategories.add(itemCategory);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemCategories;
    }

    private Set<String> readItemsFromDatabase(String itemGroup, String itemCategory) {
    Set<String> items = new TreeSet<>(Comparator.naturalOrder());

    try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        String query = "SELECT item, unit FROM lookup_item WHERE item_group = ? AND item_category = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, itemGroup);
            preparedStatement.setString(2, itemCategory);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String item = resultSet.getString("item");
                    String variation = resultSet.getString("unit");

                    // Handle variations (e.g., concatenate item and variation)
                    String itemWithVariation = (variation != null) ? item + "|" + variation : item;
                    items.add(itemWithVariation);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return items;
}


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GOBLOCK Price Tracker");

        jTree1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jTree1);

        jButton1.setText("VIEW ITEM DETAILS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("VIEW TOP 5 CHEAPEST SELLER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("VIEW PRICE TREND");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("ADD TO SHOPPING CART");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

    // Check if the selected node is a leaf (item)
    if (selectedNode != null && selectedNode.isLeaf()) {
        String selectedItem = selectedNode.getUserObject().toString();

        // Extract item and unit from the selected node's text
        String[] parts = selectedItem.split("\\|", 2);
        String item = parts[0].trim();
        String unit = (parts.length > 1) ? parts[1].trim() : ""; // Check if the unit is present
        
        // Print the selected item, unit, and button text
        jTextArea1.setText("Selected Item : " + item + " Unit: "+ unit+" | Action: " + ((JButton) evt.getSource()).getText() + "\n\n");
        
        String itemCode = Relate.getItemCodeFromNameAndUnit(item, unit);
        jTextArea1.append("Item: " + item+ "\n");
        jTextArea1.append("Unit: " + unit+ "\n");
        jTextArea1.append("Category: " + Relate.getItemCategoryFromCode(itemCode)+ "\n");
        jTextArea1.append("Group: " + Relate.getItemGroupFromCode(itemCode)+ "\n");
        
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
    

    // Check if the selected node is a leaf (item)
    if (selectedNode != null && selectedNode.isLeaf()) {
        String selectedItem = selectedNode.getUserObject().toString();
        
        // Extract item and unit from the selected node's text
        String[] parts = selectedItem.split("\\|", 2);
        String item = parts[0].trim();
        String unit = (parts.length > 1) ? parts[1].trim() : ""; // Check if the unit is present
        
// Print the selected item, unit, and button text
        jTextArea1.setText("Selected Item : " + item + " Unit: "+ unit+" | Action: " + ((JButton) evt.getSource()).getText() + "\n\n");
        
        String itemCode = Relate.getItemCodeFromNameAndUnit(item, unit);

        if (itemCode != null) {
            try {
                // Get details of the selected item from the CSV data
                
                List<String[]> itemDetails = getItemDetailsFromCSV(itemCode);

                // Display the top 5 cheapest sellers in jTextArea1
                jTextArea1.append(displayCheapestSellers(itemDetails));
            } catch (Exception e) {
                e.printStackTrace();
                jTextArea1.append("\nAn error occurred while processing the request.");
            }
        } else {
            jTextArea1.append("\nItem not found in the database.");
        }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

    // Check if the selected node is a leaf (item)
    if (selectedNode != null && selectedNode.isLeaf()) {
        String selectedItem = selectedNode.getUserObject().toString();
        
        // Extract item and unit from the selected node's text
        String[] parts = selectedItem.split("\\|", 2);
        String item = parts[0].trim();
        String unit = (parts.length > 1) ? parts[1].trim() : ""; // Check if the unit is present
        
// Print the selected item, unit, and button text
        jTextArea1.setText("Selected Item : " + item + " Unit: "+ unit+" | Action: " + ((JButton) evt.getSource()).getText() + "\n\n");
        
        String itemCode = Relate.getItemCodeFromNameAndUnit(item, unit);

        if (itemCode != null) {
            try {
                // Get details of the selected item from the CSV data
                
                List<String[]> itemDetails = getItemDetailsFromCSV(itemCode);

                // Display the top 5 cheapest sellers in jTextArea1
                jTextArea1.append(displayPriceTrend(itemDetails));
            } catch (Exception e) {
                e.printStackTrace();
                jTextArea1.append("\nAn error occurred while processing the request.");
            }
        } else {
            jTextArea1.append("\nItem not found in the database.");
        }
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

    // Check if the selected node is a leaf (item)
    if (selectedNode != null && selectedNode.isLeaf()) {
        String selectedItem = selectedNode.getUserObject().toString();
        
        // Extract item and unit from the selected node's text
        String[] parts = selectedItem.split("\\|", 2);
        String item = parts[0].trim();
        String unit = (parts.length > 1) ? parts[1].trim() : ""; // Check if the unit is present
        
// Print the selected item, unit, and button text
        jTextArea1.setText("Selected Item : " + item + " Unit: "+ unit+" | Action: " + ((JButton) evt.getSource()).getText() + "\n\n");
        
        String itemCode = Relate.getItemCodeFromNameAndUnit(item, unit);

        if (itemCode != null) {
            try {
                // Get details of the selected item from the CSV data
                
                List<String[]> itemDetails = getItemDetailsFromCSV(itemCode);
                addToShoppingCart(itemCode);
            } catch (Exception e) {
                e.printStackTrace();
                jTextArea1.append("\nAn error occurred while processing the request.");
            }
        } else {
            jTextArea1.append("\nItem not found in the database.");
        }
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
    
    
   private void addToShoppingCart(String itemCode) {
    try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        // Check if the item is already in the shopping cart
        String checkQuery = "SELECT * FROM shopping_cart WHERE user_id = ? AND item_code = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, Login.userId);
            checkStatement.setString(2, itemCode);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Item is already in the cart, update the quantity
                    updateQuantityInCart(itemCode);
                } else {
                    // Item is not in the cart, insert a new record
                    insertNewItemToCart(itemCode);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating/adding item to shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void updateQuantityInCart(String itemCode) {
    try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        String updateQuery = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND item_code = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, Login.userId);
            updateStatement.setString(2, itemCode);
            updateStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item quantity updated in shopping cart!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating item quantity in shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void insertNewItemToCart(String itemCode) {
    try (Connection connection = DriverManager.getConnection(Relate.JDBC_URL, Relate.DB_USER, Relate.DB_PASSWORD)) {
        String insertQuery = "INSERT INTO shopping_cart (user_id, item_code, quantity) VALUES (?, ?, 1)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, Login.userId);
            insertStatement.setString(2, itemCode);
            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item added to shopping cart successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error adding item to shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
// Helper method to get item_code for the selected item from the database



// Helper method to get details of the selected item from the CSV data
private List<String[]> getItemDetailsFromCSV(String itemCode) throws IOException, CsvValidationException {
    String csvFilePath = publicPath;
    List<String[]> csvData = readCSV(csvFilePath);

    // Filter data for the selected item code
    return csvData.stream()
            .filter(entry -> entry.length > 1 && entry[2].equals(itemCode))
            .collect(Collectors.toList());
}

private String displayCheapestSellers(List<String[]> itemDetails) throws SQLException {
    StringBuilder detailsText = new StringBuilder("Top 5 Cheapest Sellers of the Selected Item:\n");

    // Sort the itemDetails by price in ascending order
    itemDetails.sort(Comparator.comparingDouble(entry -> Double.parseDouble(entry[3])));

    // Display only the top 5 records
    int count = 0;
    for (String[] entry : itemDetails) {
        String premiseId = entry[1];
        String productId = entry[2];
        double price = Double.parseDouble(entry[3]);
        String premiseAddress = Relate.getPremiseAddressFromCode(premiseId);

        detailsText.append(count + 1).append(". ").append("Shop: ").append(Relate.getPremiseNameFromCode(premiseId)).append("\n");
        detailsText.append("   Price: RM").append(price).append("\n");
        detailsText.append("   Address: ").append(premiseAddress).append("\n\n");

        count++;
        if (count == 5) {
            break;
        }
    }

    // Return the formatted text
    return detailsText.toString();
}



private List<String[]> readCSV(String filePath) throws IOException, CsvValidationException {
    List<String[]> data = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            data.add(nextLine);
        }
    }
    return data;
}

    private String displayPriceTrend(List<String[]> priceTrendData) {
    StringBuilder trendText = new StringBuilder("Price Trend Chart for Selected Item\n");
    trendText.append("Days | Price\n");
    trendText.append("--------------\n");

    // Map to store daily prices
    Map<String, List<Double>> dailyPrices = new TreeMap<>();

    for (String[] entry : priceTrendData) {
        String date = entry[0];
        double price = Double.parseDouble(entry[3]);

        // Check if the date is already in the map
        if (!dailyPrices.containsKey(date)) {
            dailyPrices.put(date, new ArrayList<>());
        }

        // Add the price to the list for the corresponding date
        dailyPrices.get(date).add(price);
    }

    // Iterate through the map and create the formatted string
    for (Map.Entry<String, List<Double>> entry : dailyPrices.entrySet()) {
        String date = entry.getKey();
        List<Double> prices = entry.getValue();

        // Calculate the average price for the day
        double averagePrice = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Append the daily trend to the StringBuilder
        trendText.append(String.format("%s | RM%.2f\n", date, averagePrice));
    }

    // Return the formatted text
    return trendText.toString();
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
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Category(publicPath).setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}

