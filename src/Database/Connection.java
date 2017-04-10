/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Classes.Auctions.Standard;
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
import java.util.Date;
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

    
    static final String GET_FROM_AUCTIONS_SQL = "SELECT ? FROM auction WHERE ? = ?";
    static final String GET_FROM_AUCTIONS = "SELECT * FROM auction";
    static final String GET_FROM_USER_ID = "SELECT * FROM user WHERE id = ?";
    static final String GET_FROM_USER_byLOGININFO = "SELECT * FROM user WHERE username = ? and password = ?";
    static final String GET_FROM_PRODUCT = "SELECT * FROM product WHERE id = ?";
    static final String SET_USER_NEW = "INSERT INTO user(bsn, username, password, alias, email, verified, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public Connection() {

    }

    public void getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?zeroDateTimeBehavior=convertToNull", "lesleya213_pts", "wachtwoord123");
            System.out.println("started connection to database...");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to start connection to database...");

        }
    }

    public ArrayList<Auction> getAuctions(String selectFrom, String where, String is) {

        auctions = new ArrayList<Auction>() {
        };
        int id;
        User user;
        Product product;
        int quantity;
        Date date;
        double price;
        double instabuyprice;
        double priceloweringAmount;
        double priceloweringDelay;
        double minprice;
        StatusEnum status;
        String description;
        String imageURL;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_AUCTIONS);
//            pstmt.setString(1, selectFrom);
//            pstmt.setString(2, where);
//            pstmt.setString(3, is);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {

                if (myRs.getString("type").equals("countdown")) {
                    id = myRs.getInt("id");
                    user = getUser((myRs.getInt("sellerID")));
                    product = getProduct(myRs.getInt("productID"));
                    quantity = myRs.getInt("productquantity");
                    price = myRs.getDouble("currentprice");
                    priceloweringAmount = myRs.getDouble("priceloweringAmount");
                    priceloweringDelay = myRs.getDouble("priceloweringdelay");
                    minprice = myRs.getDouble("minPrice");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    auction = new Countdown(id, user, product, quantity, price, priceloweringAmount, priceloweringDelay, minprice, status, description, imageURL);
                }
                
                // In case of Direct 
                if (myRs.getString("type").equals("direct")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    auction = new Direct(id, user, product, price, quantity, status, description, imageURL);
                }
                
                if (myRs.getString("type").equals("standard")){
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    date = myRs.getDate("timeend");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    auction = new Standard(id,user,product,price,quantity,date,status,description,imageURL);
                }

                auctions.add(auction);
                auction = null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Je bent fucked.");
        }
        
        closeConnection();
        return auctions;
    }

    public User getUser(int id) {
        User user = null;
        int bsn;
        String username;
        String password;
        String alias;
        String email;
        boolean verified;
        float saldo;
        
    PreparedStatement preparedStatement = null;
    ResultSet resultset = null;

        if (myConn != null) {

            try {
                preparedStatement = myConn.prepareStatement(GET_FROM_USER_ID);
                preparedStatement.setInt(1, id);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                bsn = resultset.getInt("bsn");
                username = resultset.getString("username");
                password = resultset.getString("password");
                alias = resultset.getString("alias");
                email = resultset.getString("email");
                verified = resultset.getBoolean("verified");
                saldo = resultset.getFloat("saldo");
                String imgURL = resultset.getString("imageUrl");

                user = new User(bsn, username, password, alias, email, verified, saldo, imgURL);

                return user;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("failed to get user with id:" + id);
            System.out.println("There is no existing connection");
        }

        return user;
    }

    public User getUser(String username, String password) {
        User user = null;
            try {
                getConnection();
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
                int bsn = myRs.getInt("bsn");
                String usernm = myRs.getString("username");
                String pass = myRs.getString("password");
                String alias = myRs.getString("alias");
                String email = myRs.getString("email");
                boolean verified = myRs.getBoolean("verified");
                double saldo = myRs.getDouble("saldo");
                String imgURL = myRs.getString("imageUrl");

                user = new User(bsn, usernm, pass, alias, email, verified, saldo, imgURL);
                closeConnection();
                return user;

            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
            
        closeConnection();
        return user;
    }

    public Boolean setUser_REGISTER(int bsn, String username, String password, String alias, String email, double saldo) {
        User user = null;
        boolean verified = false;

        if (myConn != null) {
            try {
                getConnection();
                pstmt = myConn.prepareStatement(SET_USER_NEW);
                pstmt.setInt(1, bsn);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, alias);
                pstmt.setString(5, email);
                pstmt.setBoolean(6, verified);
                pstmt.setDouble(7, saldo);

                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("failed to register new user");
                closeConnection();
                return false;
            }
            System.out.println("succesfully registered new user with username: " + username);
            closeConnection();
            return true;
        } else {
            System.out.println("failed to register new user");
            closeConnection();
            return false;
        }
        
    }

    private Product getProduct(int productID) {

        Product product = null;
        String name;
        String description;
        String gtin;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        
        

        if (myConn != null) {

            try {
                preparedStatement = myConn.prepareStatement(GET_FROM_PRODUCT);
                preparedStatement.setInt(1, productID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                name = resultset.getString("name");
                description = resultset.getString("description");
                gtin = resultset.getString("gtin");

                product = new Product(gtin,name, description);

                return product;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            getConnection();
            getProduct(productID);
        }
        return product;
    }

    private void closeConnection() {
        try {
            myRs.close();
            myConn.close();
            pstmt.close();
            System.out.println("Closing connection to database...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
;
}
