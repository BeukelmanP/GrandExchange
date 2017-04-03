/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorian
 */
public class Connection {
    
    private java.sql.Connection myConn = null;
    private PreparedStatement pstmt = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    ArrayList<Auction> auctions;
    
    static final String GET_FROM_AUCTIONS_SQL = "SELECT ? FROM ? WHERE ID = ?";

    
    
    public Connection()
    {
    
    }
    
    public void getConnection(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbName", "Login" , "Pass");
        } 
        catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Starting connection to databse...");
    }
    
    public Collection<Auction> getAuctions(String select, String from, String where, String is){
        
        auctions = new ArrayList<Auction>() {};
    
        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_AUCTIONS_SQL);
            pstmt.setString(1, select);
            pstmt.setString(1, from);
            pstmt.setString(1, where);
            pstmt.setString(1, is);
            
            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            myRs.getRow();
        } catch (Exception e) {
        }
        
        return auctions;
    }
    
    
    private void closeConnection(){
        try {
            myRs.close();
            myConn.close();
            pstmt.close();
            System.out.println("Closing connection to database...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
}
