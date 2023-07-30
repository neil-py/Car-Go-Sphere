/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neil
 */
public class BookRental implements Backend{
    
    private int custID;
    private int carID;
    private String rentDate;
    private String returnDate;
    
    
    public BookRental(int custID, int carID, String rentDate, String returnDate){
        this.custID=custID;
        this.carID=carID;
        this.rentDate=rentDate;
        this.returnDate=returnDate;
        CalculateRentRate();
    }
    
    public int calculateDays() {
        try {
            LocalDate startDate = LocalDate.parse(rentDate);
            LocalDate endDate = LocalDate.parse(returnDate);

            // Calculate the difference in days between startDate and endDate
            long differenceInDays = ChronoUnit.DAYS.between(startDate, endDate);

            // Convert the result to int and return
            return (int) differenceInDays;
        } catch (Exception e) {
            System.out.println("Error Parsing");
            return 0; // Return a default value or handle the exception as needed
        }
    }
    
    public double CalculateRentRate(){
        int days = calculateDays();
        double rate = 0; // Initialize rate to a default value in case of an issue.
        double totalRate=0;

        String query = "SELECT ratePerDay FROM CARS WHERE carID=?;";
        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            try (Connection conn = connect.getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, carID);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    rate = rs.getDouble("ratePerDay");
                } else {
                    System.out.println("Error: No rate found for carID: " + carID);
                }
            }
            totalRate = (double) days * rate;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return totalRate;
    }
   

    @Override
    public void register() {
        double totalRentCost = CalculateRentRate();
        String query2 = "INSERT INTO RENTALS (custID, carID, rentalDate, returnDate, totalCost, status)" +
                                                     " VALUES (?, ?, ?, ?, ?, ?);";
        
        try{
            System.out.println(rentDate);
            System.out.println(returnDate);
            System.out.println(totalRentCost);
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement pstmt2 = conn.prepareStatement(query2);
            pstmt2.setInt(1, custID);
            pstmt2.setInt(2, carID);
            pstmt2.setString(3, rentDate);
            pstmt2.setString(4, returnDate);
            pstmt2.setDouble(5, totalRentCost);
            pstmt2.setString(6, "Currently Rented");
            pstmt2.executeUpdate();

            conn.close();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        
    }
}
