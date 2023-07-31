/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package APP;


import LIB.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import javax.swing.*;



public class ManageRentals extends javax.swing.JFrame {

    private int selectedRentID = 0;
    public int mode = 1;
    public ManageRentals() {
        initComponents();
        update();
    }

    private void update() {
        selectedRentID = 0;
        Connection conn;
        DefaultTableModel tableModel = (DefaultTableModel)rentalTable.getModel();
        tableModel.getDataVector().removeAllElements();
        
        //remove information from inputs
        rentIDInp.setText("");
        custIDInp.setText("");
        lastNameInp.setText("");
        firstNameInp.setText("");
        carIDInp.setText("");
        makeInp.setText("");
        modelInp.setText("");
        rentalDateInp.setCalendar(null);
        returnDateInp.setCalendar(null);
        totalCostInp.setText("");
        
        //rentalDateIN.
        
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:CarManagementRental.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RENTALS r JOIN CUSTOMERS c ON  r.custID = c.custID JOIN CARS ca ON r.carID = ca.carID;");
            while(rs.next()){
                //Get raw data from sql
                String rentID = String.valueOf((int)rs.getInt("rentID"));
                String custID = String.valueOf((int)rs.getInt("custID"));
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String carID = String.valueOf((int)rs.getInt("carID"));
                String make = rs.getString("make");
                String model = rs.getString("model");
                String rentalDate = rs.getString("rentalDate");
                String returnDate = rs.getString("returnDate");
                String totalCost = String.valueOf(rs.getFloat("totalCost"));
                String status = rs.getString("status");
                
                //Add the data to table
                String rowData[] = {rentID, custID, lname, fname, carID, make, model, rentalDate, returnDate, totalCost, status};
                tableModel.addRow(rowData);
            }
            conn.close();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void update(String query){
        Connection conn;
        DefaultTableModel tableModel = (DefaultTableModel)rentalTable.getModel();
        tableModel.getDataVector().removeAllElements();
        try{
            DatabaseConnection connect = DatabaseConnection.getInstance();
            conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                //Get raw data from sql
                String rentID = String.valueOf((int)rs.getInt("rentID"));
                String custID = String.valueOf((int)rs.getInt("custID"));
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String carID = String.valueOf((int)rs.getInt("carID"));
                String make = rs.getString("make");
                String model = rs.getString("model");
                String rentalDate = rs.getString("rentalDate");
                String returnDate = rs.getString("returnDate");
                String totalCost = String.valueOf(rs.getFloat("totalCost"));
                String status = rs.getString("status");
                
                //Add the data to table
                String rowData[] = {rentID, custID, lname, fname, carID, make, model, rentalDate, returnDate, totalCost, status};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
            conn.close();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public String makeQuery() {
        String query = "";
        // For Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String rentalDate;
        String returnDate;
        
        String rentID = rentIDInp.getText();
        String custID = custIDInp.getText();
        String lastName = lastNameInp.getText();
        String firstName = firstNameInp.getText();
        String carID = carIDInp.getText();
        String make = makeInp.getText();
        String model = modelInp.getText();
        
        //rentalDate
        if (rentalDateInp.getDate() != null) {
            java.util.Date dRentalDate = rentalDateInp.getDate();
            rentalDate = dateFormat.format(dRentalDate);
        }
        
        else {
            rentalDate = "";
        }
        
        //returnDate
        if (returnDateInp.getDate() != null) {
            java.util.Date dReturnDate = returnDateInp.getDate();
            returnDate = dateFormat.format(dReturnDate); 
        }
        else {
            returnDate = "";
        }
        
        String totalCost = totalCostInp.getText();
        
        if((("".equals(rentID))&&("".equals(custID))&&("".equals(lastName))&&("".equals(firstName))&&("".equals(carID)))&&("".equals(make))&&("".equals(model))&&("".equals(rentalDate))&&("".equals(returnDate))&&("".equals(totalCost))) {
            query = "SELECT * FROM RENTALS r JOIN CUSTOMERS c ON  r.custID = c.custID JOIN CARS ca ON r.carID = ca.carID";
        } else{
            query = "SELECT * FROM RENTALS r JOIN CUSTOMERS c ON  r.custID = c.custID JOIN CARS ca ON r.carID = ca.carID WHERE";
            boolean thereExistFilter = false;
            if(!"".equals(rentID)){
                query += " rentID = '" + rentID + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(custID)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " r.custID = '" + custID + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(lastName)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " lname = '" + lastName + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(firstName)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " fname = '" + firstName + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(rentalDate)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " rentalDate >= '" + rentalDate + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(returnDate)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " returnDate <= '" + returnDate + "'";
                thereExistFilter=true;
            }
            
            if(!"".equals(totalCost)){
                if(thereExistFilter){
                    query+= " AND ";
                }
                query += " totalCost = '" + totalCost + "'";
            }
        }
        
        return query;
    }
    
    public boolean checkFields() {
        //Check if any fields are empty
        return (rentIDInp.getText().isEmpty() || custIDInp.getText().isEmpty() || lastNameInp.getText().isEmpty() || firstNameInp.getText().isEmpty() ||
                carIDInp.getText().isEmpty() || makeInp.getText().isEmpty() || modelInp.getText().isEmpty());
    }
    
    public boolean confirmationWindow(String buttonValue, String rentalDate, String returnDate) {
        //Confirmation Window
        UIManager.put("OptionPane.okButtonText", buttonValue);
        String confirmation = "Rent ID: " + rentIDInp.getText() + "\n"
                            + "Customer ID: " + custIDInp.getText() + "\n"
                            + "Customer Name: " + firstNameInp.getText() + lastNameInp.getText() + "\n"
                            + "Car ID: " + carIDInp.getText() + "\n"
                            + "Make: " + makeInp.getText() + "\n"
                            + "Model: " + modelInp.getText() + "\n"
                            + "Rental Date: " + rentalDate + "\n"
                            + "Return Date: " + returnDate;
        int result = JOptionPane.showConfirmDialog(null,confirmation, "Confirm Return of Car?", JOptionPane.OK_CANCEL_OPTION);
        return (result == JOptionPane.CANCEL_OPTION);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rentalTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        rentIDInp = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        custIDInp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lastNameInp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        carIDInp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        makeInp = new javax.swing.JTextField();
        modelInp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rentalDateInp = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        returnDateInp = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        totalCostInp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        resolveButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        firstNameInp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        resolveButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Rentals");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(245, 249, 252));
        jPanel1.setForeground(new java.awt.Color(244, 248, 255));
        jPanel1.setToolTipText("");
        jPanel1.setPreferredSize(new java.awt.Dimension(20, 304));

        rentalTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        rentalTable.setForeground(new java.awt.Color(26, 34, 65));
        rentalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rent ID", "Customer ID", "Last Name", "First Name", "Car ID", "Make", "Model", "Rental Date", "Return Date", "Total Cost", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rentalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rentalTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rentalTable);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel1.setText("Rent ID");

        rentIDInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        rentIDInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentIDInpActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel2.setText("Customer ID");

        custIDInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        custIDInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                custIDInpActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel3.setText("Last Name");

        lastNameInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lastNameInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameInpActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel4.setText("Car ID");

        carIDInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        carIDInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carIDInpActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel5.setText("Make");

        makeInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        makeInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeInpActionPerformed(evt);
            }
        });

        modelInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        modelInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelInpActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel6.setText("Model");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel7.setText("Rental Date");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel8.setText("Return Date");

        totalCostInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        totalCostInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalCostInpActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel9.setText("Total Cost");

        jButton1.setBackground(new java.awt.Color(26, 34, 65));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Apply Filter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        resolveButton.setBackground(new java.awt.Color(35, 194, 150));
        resolveButton.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        resolveButton.setText("Clear Filters");
        resolveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resolveButtonActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton2.setText("Sort: Returned");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        firstNameInp.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        firstNameInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameInpActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel10.setText("First Name");

        resolveButton1.setBackground(new java.awt.Color(51, 51, 255));
        resolveButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        resolveButton1.setForeground(new java.awt.Color(255, 255, 255));
        resolveButton1.setText("Return Car");
        resolveButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resolveButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton3.setText("Revert Status");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rentIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(custIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lastNameInp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(firstNameInp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(carIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(makeInp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(266, 266, 266)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resolveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resolveButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(modelInp, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(rentalDateInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(returnDateInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(totalCostInp)
                                        .addGap(23, 23, 23)))))))
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rentIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(custIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lastNameInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(carIDInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(makeInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(modelInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(firstNameInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rentalDateInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(returnDateInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalCostInp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                .addComponent(resolveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resolveButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)))
                .addGap(2, 2, 2)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rentIDInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentIDInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rentIDInpActionPerformed

    private void custIDInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_custIDInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_custIDInpActionPerformed

    private void lastNameInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameInpActionPerformed

    private void carIDInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carIDInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carIDInpActionPerformed

    private void makeInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_makeInpActionPerformed

    private void modelInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modelInpActionPerformed

    private void totalCostInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalCostInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalCostInpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String query = makeQuery();
        query += ";";
        update(query);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rentalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentalTableMouseClicked
        // TODO add your handling code here: 
        
        int SelectedRow = rentalTable.getSelectedRow(); 
        
        //For dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date jRentalDate = new java.util.Date();
        java.util.Date jReturnDate = new java.util.Date();
        
        rentIDInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 0)));
        custIDInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 1)));
        lastNameInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 2)));
        firstNameInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 3)));
        carIDInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 4)));
        makeInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 5)));
        modelInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 6)));
        
        //Rental Date
        String rawRentalDate = String.valueOf(rentalTable.getValueAt(SelectedRow, 7));
        try {
            jRentalDate = dateFormat.parse(rawRentalDate);  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
        }
        rentalDateInp.setDate(jRentalDate);
        
        //Return Date
        String rawReturnDate = String.valueOf(rentalTable.getValueAt(SelectedRow, 8));
        try {
            jReturnDate = dateFormat.parse(rawReturnDate);  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
        }
        returnDateInp.setDate(jReturnDate);
        
        totalCostInp.setText(String.valueOf(rentalTable.getValueAt(SelectedRow, 9)));
    }//GEN-LAST:event_rentalTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        String query = "";
        
        switch(mode) {
            case 1:
                jButton2.setText("Sort: Currently Rented");
                query = makeQuery();
                query += " AND r.status = 'Currently Rented';";
                update(query);
                query = "";
                mode = 2;
                break;
            case 2:
                jButton2.setText("Sort: Returned");
                query = makeQuery();
                query += " AND r.status = 'Returned';";
                update(query);
                query = "";
                mode = 3;
                break;
            case 3:
                jButton2.setText("Sort: Show All");
                update();
                mode = 1;
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void firstNameInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameInpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameInpActionPerformed

    private void resolveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolveButtonActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_resolveButtonActionPerformed

    private void resolveButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolveButton1ActionPerformed
        // TODO add your handling code here:
        if(checkFields()) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        
        //For dates later
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //rentalDate
        if (rentalDateInp.getDate() == null) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        java.util.Date dRentalDate = rentalDateInp.getDate();
        String rentalDate = dateFormat.format(dRentalDate);
        //returnDate
        if (returnDateInp.getDate() == null) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        java.util.Date dReturnDate = returnDateInp.getDate();
        String returnDate = dateFormat.format(dReturnDate);
        
        if(confirmationWindow("Confirm", rentalDate, returnDate)) {
            return;
        }
        
        //For the SQL query
        String rentalQuery = "UPDATE RENTALS " +
                             "SET STATUS = 'Returned' " +
                             "WHERE rentID = ? AND custID = ? AND carID = ? AND rentalDate = ? AND returnDate = ?;";
        
        String carsQuery = "UPDATE CARS " +
                           "SET status = 'Available' " +
                           "WHERE carID = ? AND model = ? AND make = ?;";
        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement rentalStmt = conn.prepareStatement(rentalQuery);
            PreparedStatement carsStmt = conn.prepareStatement(carsQuery);
            
            rentalStmt.setString(1, rentIDInp.getText());
            rentalStmt.setString(2, custIDInp.getText());
            rentalStmt.setString(3, carIDInp.getText());
            rentalStmt.setString(4, rentalDate);
            rentalStmt.setString(5, returnDate);
            
            
            carsStmt.setString(1, carIDInp.getText());
            carsStmt.setString(2, modelInp.getText());
            carsStmt.setString(3, makeInp.getText());
            
            carsStmt.execute();
            
            rentalStmt.execute();
            
            conn.close();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        
        update();
    }//GEN-LAST:event_resolveButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:if(checkFields()) {
        if (checkFields()) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        //For dates later
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //rentalDate
        if (rentalDateInp.getDate() == null) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        
        java.util.Date dRentalDate = rentalDateInp.getDate();
        String rentalDate = dateFormat.format(dRentalDate);
        //returnDate
        if (returnDateInp.getDate() == null) {
            JOptionPane.showMessageDialog(this, "ERROR: Please select a line to return car");
            return;
        }
        java.util.Date dReturnDate = returnDateInp.getDate();
        String returnDate = dateFormat.format(dReturnDate);
        
        if (confirmationWindow("Revert", rentalDate, returnDate)) {
            return;
        }
        
        //For the SQL query
        String rentalQuery = "UPDATE RENTALS " +
                             "SET STATUS = 'Currently Rented' " +
                             "WHERE rentID = ? AND custID = ? AND carID = ? AND rentalDate = ? AND returnDate = ?;";
        
        String carsQuery = "UPDATE CARS " +
                           "SET status = 'Rented' " +
                           "WHERE carID = ? AND model = ? AND make = ?;";
        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement rentalStmt = conn.prepareStatement(rentalQuery);
            PreparedStatement carsStmt = conn.prepareStatement(carsQuery);
            
            rentalStmt.setString(1, rentIDInp.getText());
            rentalStmt.setString(2, custIDInp.getText());
            rentalStmt.setString(3, carIDInp.getText());
            rentalStmt.setString(4, rentalDate);
            rentalStmt.setString(5, returnDate);
            
            
            carsStmt.setString(1, carIDInp.getText());
            carsStmt.setString(2, modelInp.getText());
            carsStmt.setString(3, makeInp.getText());
            
            carsStmt.execute();
            
            rentalStmt.execute();
            
            conn.close();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        
        update();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        new Menu().setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carIDInp;
    private javax.swing.JTextField custIDInp;
    private javax.swing.JTextField firstNameInp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField lastNameInp;
    private javax.swing.JTextField makeInp;
    private javax.swing.JTextField modelInp;
    private javax.swing.JTextField rentIDInp;
    private com.toedter.calendar.JDateChooser rentalDateInp;
    private javax.swing.JTable rentalTable;
    private javax.swing.JButton resolveButton;
    private javax.swing.JButton resolveButton1;
    private com.toedter.calendar.JDateChooser returnDateInp;
    private javax.swing.JTextField totalCostInp;
    // End of variables declaration//GEN-END:variables
}
