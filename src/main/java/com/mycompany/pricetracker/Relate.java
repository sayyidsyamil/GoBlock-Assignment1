package com.mycompany.pricetracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Relate {

    public static String JDBC_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String localFilePath = System.getProperty("user.dir") + "\\pricecatcher_2023-08.csv";

    public static void main(String[] args) {
        updateDatabaseCredentials("https://pastebin.com/raw/TUgnmD8t");
      
    }
    
        // had to change bcs spectrum limit to 20mb file only
   public static void updatePriceTrackerData() {
        // Check if the file is available locally
        if (Files.exists(Path.of(localFilePath))) {
            // File exists locally, print its content
            System.out.println(localFilePath + " exists.");
        } else {
            System.out.println("Spectrum limits data up to 20MB only! And the price tracker data is 60MB.");
            System.out.println("Default Price Tracker Data doesn't exists.");
            System.out.println("Downloading... Please wait....");
            // File doesn't exist locally, download it and save
            downloadAndSaveFile(localFilePath);
            localFilePath = System.getProperty("user.dir") + "\\pricecatcher_2023-08.csv";
            System.out.println(localFilePath + " downloaded and ready to be use.");
        }
    }
    



    private static void downloadAndSaveFile(String localFilePath) {
        String csvUrl = "https://storage.data.gov.my/pricecatcher/pricecatcher_2023-08.csv";

        try {
            // Create a URL object
            URL url = new URL(csvUrl);

            // Download the file and save it locally
            Files.copy(url.openStream(), Path.of(localFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File downloaded and saved at: " + localFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabaseCredentials(String pastebinUrl) {
        try (Scanner scanner = new Scanner(new URL(pastebinUrl).openStream())) {
            if (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                if (values.length == 3) {
                    JDBC_URL = values[0];
                    DB_USER = values[1];
                    DB_PASSWORD = values[2];
                } else {
                    System.err.println("Invalid connection with the database.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getItemNameFromCode(String itemCode) {
        return getStringFromCode("lookup_item", "item", "item_code", itemCode);
    }
    public static String getUnitFromCode(String itemCode) {
        return getStringFromCode("lookup_item", "unit", "item_code", itemCode);
    }
    
    public static String getItemCodeFromName(String itemName) {
        return getStringFromCode("lookup_item", "item_code", "item", itemName);
    }

    public static String getItemCodeFromNameAndUnit(String itemName, String itemUnit) {
    String result = null;
    String columnName = "item_code";
    String tableName = "lookup_item";
    String searchColumnItem = "item";
    String searchColumnUnit = "unit";

    String query = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", columnName, tableName, searchColumnItem, searchColumnUnit);

    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, itemName);
        preparedStatement.setString(2, itemUnit);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                result = resultSet.getString(columnName);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Handle the exception more appropriately in your actual code
    }

    return result;
}

    
    public static String getItemGroupFromCode(String itemCode) {
        return getStringFromCode("lookup_item", "item_group", "item_code", itemCode);
    }

    public static String getItemCategoryFromCode(String itemCode) {
        return getStringFromCode("lookup_item", "item_category", "item_code", itemCode);
    }

    public static String getPremiseNameFromCode(String premiseCode) {
        return getStringFromCode("lookup_premise", "premise", "premise_code", premiseCode + ".0");
    }

    public static String getPremiseCodeFromName(String premiseName) {
        return getStringFromCode("lookup_premise", "premise_code", "premise", premiseName);
    }

    public static String getPremiseAddressFromCode(String premiseCode) {
        return getStringFromCode("lookup_premise", "address", "premise_code", premiseCode+ ".0");
    }

    public static String getPremiseTypeFromCode(String premiseCode) {
        return getStringFromCode("lookup_premise", "premise_type", "premise_code", premiseCode+ ".0");
    }

    public static String getPremiseStateFromCode(String premiseCode) {
        return getStringFromCode("lookup_premise", "state", "premise_code", premiseCode+ ".0");
    }

    public static String getPremiseDistrictFromCode(String premiseCode) {
        return getStringFromCode("lookup_premise", "district", "premise_code", premiseCode+ ".0");
    }
    
    public static List<String> getPremiseCodesFromDistrict(String premiseDistrict) {
    List<String> result = new ArrayList<>();
    String query = "SELECT premise_code FROM lookup_premise WHERE district = ?";

    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, premiseDistrict);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(resultSet.getString("premise_code"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
}

    
    

    private static String getStringFromCode(String tableName, String columnName, String searchColumn, String searchValue) {
        String result = null;
        String query = String.format("SELECT %s FROM %s WHERE %s = ?", columnName, tableName, searchColumn);

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, searchValue);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getString(columnName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
