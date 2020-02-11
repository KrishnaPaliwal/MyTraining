package com.Training.JDBC;

//STEP 1. Import required packages
import java.sql.*;

public class FirstExample_using_PreparedStatement {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost:3306/test";

 //  Database credentials
 static final String USER = "root";
 static final String PASS = "root";
 
 public static void main(String[] args) {
 Connection conn = null;
 PreparedStatement pStmt = null;
 try{
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);
    	

    //STEP 4: Execute a query
    System.out.println("Creating prepareStatement...");

   String sql;
    sql = "SELECT id, first, last, age FROM Employees";
    
    pStmt = conn.prepareStatement(sql);
    
    ResultSet rs = pStmt.executeQuery();
    //STEP 5: Extract data from result set
    while(rs.next()){
       //Retrieve by column name
       int id  = rs.getInt("id");
       int age = rs.getInt("age");
       String first = rs.getString("first");
       String last = rs.getString("last");

       //Display values
       System.out.print("ID: " + id);
       System.out.print(", Age: " + age);
       System.out.print(", First: " + first);
       System.out.println(", Last: " + last);
    }
    
    int x = pStmt.executeUpdate("update Employees set last = 'Paliwalaggfdftt' where last='Paliwalatt'");
    int y = pStmt.executeUpdate("delete from Employees where Id=103");
    
    String sql2;
    sql2 = "insert into Employees(Id, age, first, last) values (? , ? , ? , ?)"; 
    pStmt = conn.prepareStatement(sql2);
    pStmt.setInt(1, 108);
    pStmt.setInt(2, 50);
    pStmt.setString(3, "Laxmi");
    pStmt.setString(4, "Baisa");
    
    int y2 = pStmt.executeUpdate();
    
    //or
    int z = pStmt.executeUpdate("insert into Employees values (104, 60, 'Mohaniiiii', 'Agarwal')");
    
    //STEP 6: Clean-up environment
    rs.close();
    pStmt.close();
    conn.close();
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(pStmt!=null)
          pStmt.close();
    }catch(SQLException se2){
    }// nothing we can do
    try{
       if(conn!=null)
          conn.close();
    }catch(SQLException se){
       se.printStackTrace();
    }//end finally try
 }//end try
 System.out.println("Goodbye!");
}//end main
}//end FirstExample