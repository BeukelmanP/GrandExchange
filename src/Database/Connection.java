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
import java.sql.Timestamp;
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
    ArrayList<Product> products;

    static final String GET_FROM_AUCTIONS_SQL = "SELECT ? FROM auction WHERE ? = ?";
    static final String GET_FROM_AUCTIONS = "SELECT * FROM auction";
    static final String GET_FROM_USER_ID = "SELECT * FROM user WHERE id = ?";
    static final String GET_FROM_USER_BYLOGININFO = "SELECT * FROM user WHERE BINARY username = ? and BINARY password = ?";
    static final String GET_FROM_PRODUCT = "SELECT * FROM product WHERE id = ?";
    static final String SET_USER_NEW = "INSERT INTO user(bsn, username, password, alias, email, verified, imageURL, saldo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    static final String REMOVE_USER_BYBSN = "DELETE FROM user WHERE bsn = ?";
    static final String GET_AUCTION_BY_ID = "SELECT * FROM auction WHERE id = ?";
    static final String GET_FROM_PRODUCTS = "SELECT * FROM product";
    public Connection() {

    }

    public boolean getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?zeroDateTimeBehavior=convertToNull", "lesleya213_pts", "wachtwoord123");
            System.out.println("started connection to database...");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to start connection to database...");
            return false;
        }
    }
    
    public Auction getAuction(int id) {
        User user;
        Product product;
        int quantity;
        Timestamp date;
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
            pstmt = myConn.prepareStatement(GET_AUCTION_BY_ID);
            pstmt.setInt(1, id);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            myRs.next();
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
                    instabuyprice = myRs.getDouble("instabuyprice");
                    date = myRs.getTimestamp("timecreated");
                    
                    auction = new Countdown(id, user, product, quantity, price, priceloweringAmount, priceloweringDelay, minprice, status, description, imageURL, instabuyprice, date);
                }

                // In case of Direct 
                if (myRs.getString("type").equals("direct")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Direct(id, user, product, price, begin, quantity, status, description, imageURL, instabuyprice);
                }

                //In case of standard auction
                if (myRs.getString("type").equals("standard")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    date = myRs.getTimestamp("timeend");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Standard(id, user, product, price, quantity, begin, date, status, description, imageURL, instabuyprice);
                }
                return auction;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to get auction by ID");
        }

        closeConnection();
        return null;
    }
    
    public ArrayList<Auction> getAuctions(String selectFrom, String where, String is) {

        auctions = new ArrayList<Auction>() {};
        int id;
        User user;
        Product product;
        int quantity;
        Timestamp date;
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
                    instabuyprice = myRs.getDouble("instabuyprice");
                    date = myRs.getTimestamp("timecreated");
                    auction = new Countdown(id, user, product, quantity, price, priceloweringAmount, priceloweringDelay, minprice, status, description, imageURL, instabuyprice, date);
                }

                // In case of Direct 
                if (myRs.getString("type").equals("direct")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Direct(id, user, product, price, begin, quantity, status, description, imageURL, instabuyprice);
                }

                if (myRs.getString("type").equals("standard")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    date = myRs.getTimestamp("timeend");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Standard(id, user, product, price, quantity, begin, date, status, description, imageURL, instabuyprice);
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
    
    public ArrayList<Product> getProducts() {

        this.products = new ArrayList<>();
        Product product;
        int id;
        String name;
        String description;
        String gtin;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_PRODUCTS);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {
                    id = myRs.getInt("id");
                    name = myRs.getString("name");
                    description = myRs.getString("description");
                    gtin = myRs.getString("gtin");
                    product = new Product(gtin, name, description);
                
                products.add(product);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Product ding connection ophalen failed ofzo.");
        }

        closeConnection();
        return products;
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
            System.out.println("There is no existing connection");
        }

        return user;
    }

    public User getUser(String username, String password) {
        User user = null;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_USER_BYLOGININFO);
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
        } catch (SQLException ex) {
            System.out.println("User not found");
            closeConnection();
        }

        return user;
    }
    

    public boolean hasDuplicateBSN(int checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE bsn = ?");
            pstmt.setInt(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            int bsn = myRs.getInt("bsn");
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    public boolean hasDuplicateUsername(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE username = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String username = myRs.getString("username");
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    public boolean hasDuplicateEmail(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE email = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String email = myRs.getString("email");
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    public boolean hasDuplicateAlias(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE alias = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String alias = myRs.getString("alias");
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }
    
    public Boolean setUser_REGISTER(int bsn, String username, String password, String alias, String email, String imageUrl, double saldo) {
        getConnection();

        if (myConn != null) {
            if (getUser(username, password) == null) {
                try {
                    getConnection();
                    boolean verified = false;
                    pstmt = myConn.prepareStatement(SET_USER_NEW);
                    pstmt.setInt(1, bsn);
                    pstmt.setString(2, username);
                    pstmt.setString(3, password);
                    pstmt.setString(4, alias);
                    pstmt.setString(5, email);
                    pstmt.setBoolean(6, verified);
                    pstmt.setString(7, imageUrl);
                    pstmt.setDouble(8, saldo);

                    if (pstmt.executeUpdate() > 0) {
                        System.out.println("succesfully registered new user with username: " + username);
                        return true;
                    } else {
                        System.out.println("Couldn't insert new user. Rows are unaffected.");
                        return false;
                    }
                } catch (SQLException ex) {
                    System.out.println("failed to register new user. SQLException");
                    ex.printStackTrace();
                    closeConnection();
                    return false;
                }
            } else {
                System.out.println("Registration of duplicate user isn't allowed.");
                return false;
            }
        } else {
            System.out.println("failed to register new user. No connection to database.");
            return false;
        }
    }

    /**
     * removes a user with given bsn note: doesn't delete any objects yet that
     * the user created (e.g. auctions, bids, feedbacks)
     *
     * @param bsn
     * @return
     */
    public Boolean removeUser_BYBSN(int bsn) {
        getConnection();

        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(REMOVE_USER_BYBSN);
                pstmt.setInt(1, bsn);

                if (pstmt.executeUpdate() > 0) {
                    System.out.println("succesfully deleted user with bsn: " + bsn);
                    return true;
                } else {
                    System.out.println("Couldn't delete user because User with bsn: " + bsn + " doesn't exist in the database");
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("failed to register new user. SQLException");
                ex.printStackTrace();
                closeConnection();
                return false;
            }
        } else {
            System.out.println("failed to register new user. No connection to database.");
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

                product = new Product(gtin, name, description);

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
    
    
    public void updateAuction (Auction auction){

    }

    private boolean closeConnection() {
        try {
            myRs.close();
            myConn.close();
            pstmt.close();
            System.out.println("Closing connection to database...");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
