package Classes;

import Classes.Auctions.Auction;
import Classes.User;
import java.util.*;
import Database.*;

public class Grand_Exchange {

    ArrayList<Product> products;
    ArrayList<User> users;
    ArrayList<Auction> auctions;
    Connection con;

    public User loggedInUser;// = new User("AAP","test","http://www.jamiemagazine.nl/upload/artikel/jm/banaan-vierkant.jpg");

    public Grand_Exchange() {
        products = new ArrayList<>();
        users = new ArrayList<>();
        auctions = new ArrayList<>();
        con = new Connection();

        //Gets all existing auctions.
        auctions = con.getAuctions("*", "auction", "''");
    }

    public void Load() {

    }

    /**
     * adds user tot he collection of users
     *
     * @param user : may not be null
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.add(user);
        }
    }

    /**
     * removes user from collection of users
     *
     * @param user : may not be null
     */
    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.remove(user);
        }
    }

    public void addAuction(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException();
        } else {
            auctions.add(auction);
        }
    }

    public void removeAuction(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException();
        } else {
            auctions.add(auction);
        }
    }

    /**
     * Adds product to collection of products
     *
     * @param product : may not be null
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.add(product);
        }
    }

    /**
     * removes product from collection of products
     *
     * @param product : may not be null
     */
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.remove(product);
        }
    }

    /**
     * Checks user login
     *
     * @param username : may not be empty nor null
     * @param password : may not be empty nor null
     */
    public boolean login(String username, String password) {
        this.loggedInUser = con.getUser(username, password);
        if (this.loggedInUser != null) {
            System.out.println("user with username " + loggedInUser.getUsername() + " is logged in");
            return true;
        } else {
            System.out.println("no user is logged in");
            return false;
        }
    }

    /**
     * logout of user who is logged in
     *
     */
    public void logout() {
        this.loggedInUser = null;
        System.out.println("logged out user");
    }

    /**
     * handles transaction
     *
     * @param transaction : may not be null
     */
    public void handleTransaction(Transaction transaction) {

    }

    /**
     * Returns list of all 'official' products
     *
     * @return List<Product>
     */
    public Collection<Product> getProducts() {
        return products;
    }

    public Collection<Auction> getAuctions() {
        return auctions;
    }

}
