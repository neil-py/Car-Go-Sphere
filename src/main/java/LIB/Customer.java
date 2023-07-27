/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author neil
 */
public class Customer implements IDBConnect{
    
    
    private String fname;
    private String lname;
    private String address;
    private String contactNo;
    private String licenseNo;

    public Customer(String fname, String lname, String address, String contactNo, String licenseNo) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.contactNo = contactNo;
        this.licenseNo = licenseNo;
    }

    @Override
    public void register() {
        String query = "INSERT INTO CUSTOMERS (fname, lname, address, contactNo, licenseNo)" +
                                                     " VALUES (?, ?, ?, ?, ?);";
        try{
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, address);
            pstmt.setString(4, contactNo);
            pstmt.setString(5, licenseNo);

            pstmt.executeUpdate();
            
            conn.close();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }
    
}
