/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Car implements Backend{
    private String make;
    private String model;
    private String type;
    private String plateNo;
    private int year;
    private String fuelType;
    private double ratePerDay;
    private String status;
    
    public Car (String make, String model, String type, String plateNo, int year, String fuelType, double ratePerDay, String status){
        this.make=make;
        this.model=model;
        this.type=type;
        this.plateNo=plateNo;
        this.year=year;
        this.fuelType=fuelType;
        this.ratePerDay=ratePerDay;
        this.status=status;
    }
    
    @Override
    public void register(){
        
        String query = "INSERT INTO CARS (make, model, type, plateNo, year, fuelType, ratePerDay, status)" +
                                                     " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try{
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, make);
            pstmt.setString(2, model);
            pstmt.setString(3, type);
            pstmt.setString(4, plateNo);
            pstmt.setInt(5, year);
            pstmt.setString(6, fuelType);
            pstmt.setDouble(7, ratePerDay);
            pstmt.setString(8, status);
            pstmt.executeUpdate();
            
            conn.close();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        
    }
    
}
