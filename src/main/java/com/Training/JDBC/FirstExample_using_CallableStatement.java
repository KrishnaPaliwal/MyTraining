package com.Training.JDBC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FirstExample_using_CallableStatement {
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
	    
	   CallableStatement callable = conn.prepareCall("call GetAllProducts()");
	   callable.registerOutParameter(1, Types.FLOAT);
	  // callable.registerOutParameter(parameterIndex, OracleTyp.);
	   System.out.println(callable.getString("first"));
	   callable.execute();

	    //STEP 6: Clean-up environment

	   callable.close();
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
}
