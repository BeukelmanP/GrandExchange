/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Classes.Auctions.StatusEnum;
import Classes.Product;
import Classes.User;
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
    private Auction auction;
    ArrayList<Auction> auctions;
    
    static final String GET_FROM_AUCTIONS_SQL = "SELECT ? FROM AUCTION WHERE ? = ?";
    static final String GET_FROM_USER_byBSN = "SELECT * FROM user WHERE bsn = ?";
    static final String GET_FROM_USER_byLOGININFO = "SELECT * FROM user WHERE username = ? and password = ?";
    static final String GET_FROM_PRODUCT = "SELECT * FROM product WHERE id = ?";
    static final String SET_USER_NEW = "INSERT INTO user(bsn, username, password, alias, email, verified, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";

    
    public Connection()
    {
    
    }
    
    public void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grandexchange_local", "root" , "root");
            System.out.println("started connection to database...");
        } 
        catch (ClassNotFoundException|SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to start connection to database...");

        }
    }
    
    
//    public Collection<Auction> getAuctions(String selectFrom, String where, String is){
//        
//        auctions = new ArrayList<Auction>() {};
//        User user;
//        Product product;
//        int quantity;
//        int price;
//        double instabuyprice; 
//        double priceloweringAmount;
//        double priceloweringDelay;
//        double minprice;
//        StatusEnum status;
//        String description;
//        
//    
//        try {
//            getConnection();
//            pstmt = myConn.prepareStatement(GET_FROM_AUCTIONS_SQL);
//            pstmt.setString(1, selectFrom);
//            pstmt.setString(2, where);
//            pstmt.setString(3, is);
//            
//            myRs = pstmt.executeQuery();
//            myRs.next();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        try {
//            while(myRs.next()){
//                if (myRs.getString("type").equals("countdown")){
//                    user = getUser(myRs.getInt("id"));
//                    product = getProduct(myRs.getInt("productID"));
//                    quantity = 
//                    
//                     
//                acution = new Countdown();
//                }
//                if (myRs.getString("type").equals("direct")){
//                acution = new Direct();
//                }
//                if (myRs.getString("type").equals("standard")){
//                acution = new Standard();
//                }
//                
//                auctions.add(auction);
//                
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        
//        return auctions;
//    }
    
    public User getUser(int bsn){
        User user = null;
        //int id;
        //int bsn;
        String username;
        String password;
        String alias;
        String email;
        boolean verified;
        float saldo;
        
        if (myConn != null){
            
            try {
            pstmt = myConn.prepareStatement(GET_FROM_USER_byBSN);
            pstmt.setInt(1, bsn);
            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            try {
                //id = myRs.getInt("id");
                bsn = myRs.getInt("bsn");
                username = myRs.getString("username");
                password = myRs.getString("password");
                alias = myRs.getString("alias");
                email = myRs.getString("email");
                verified = myRs.getBoolean("verified");
                saldo = myRs.getFloat("saldo");
                
                user = new User(bsn, username, password, alias, email, verified, saldo);
                
                return user;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else{
            System.out.println("failed to get user with bsn:" + bsn);
        }
        
        return user;
    }
    
    public User getUser(String username, String password){
        User user = null;
        //int id;
        int bsn;
        //String username;
        //String password;
        String alias;
        String email;
        boolean verified;
        float saldo;
        
        if (myConn != null){
            
            try {
            pstmt = myConn.prepareStatement(GET_FROM_USER_byLOGININFO);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            try {
                //id = myRs.getInt("id");
                bsn = myRs.getInt("bsn");
                username = myRs.getString("username");
                password = myRs.getString("password");
                alias = myRs.getString("alias");
                email = myRs.getString("email");
                verified = myRs.getBoolean("verified");
                saldo = myRs.getFloat("saldo");
                
                user = new User(bsn, username, password, alias, email, verified, saldo);
                
                return user;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else{
            System.out.println("failed to get user with username:" + username);
        }
        
        return user;
    }
 
    public Boolean setUser_REGISTER(int bsn, String username, String password, String alias, String email, double saldo){
        User user = null;
        //int id;
        //int bsn;
//        String username;
//        String password;
//        String alias;
//        String email;
        boolean verified = false;
        //float saldo;
        
        if (myConn != null)
        {
            try {
            pstmt = myConn.prepareStatement(SET_USER_NEW);
            pstmt.setInt(1, bsn);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, alias);
            pstmt.setString(5, email);
            pstmt.setBoolean(6, verified);
            pstmt.setDouble(7, saldo);

            pstmt.executeUpdate();
            } 
                catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("failed to register new user");
                return false;
            }
            System.out.println("succesfully registered new user with username: " + username);
            return true;
        }
        else
        {
            System.out.println("failed to register new user");
            return false;
        }
    }

    private Product getProduct(int productID){
        
        Product product = null;
        String name;
        String description;
        String gtin;
        
        
        if (myConn != null){
            
            try {
            pstmt = myConn.prepareStatement(GET_FROM_PRODUCT);
            pstmt.setInt(1, productID);
            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            try {
                name = myRs.getString("name");
                description = myRs.getString("description");
                gtin = myRs.getString("gtin");
                
                product = new Product(name, description, gtin);
                
                return product;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        else{
            getConnection();
            getProduct(productID);
        }
        return product;
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
