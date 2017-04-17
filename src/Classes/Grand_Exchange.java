package Classes;

import Classes.Auctions.Auction;
import Classes.User;
import java.util.*;
import Database.*;
import java.sql.SQLException;

public class Grand_Exchange implements Observer {

    ArrayList<Product> products;
    ArrayList<User> users;
    ArrayList<Auction> auctions;
    Connection con;

    public User loggedInUser;// = new User("AAP","test","http://www.jamiemagazine.nl/upload/artikel/jm/banaan-vierkant.jpg");

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Grand_Exchange() {
        products = new ArrayList<>();
        users = new ArrayList<>();
        auctions = new ArrayList<>();
        con = new Connection();

        //Gets all existing auctions.
        auctions = con.getAuctions("*", "auction", "''");
        products = con.getProducts();

        DatabaseListener dbListener = new DatabaseListener();
        dbListener.addObserver(this);
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
     * gets user from collection of users with given username
     *
     * @param userName
     */
    public User getUser(String userName) {
        User missingUser = null;
        for (User u : this.users)
        {
            if (u.getUsername().equals(userName))
            {
                missingUser = u;
            }
        }
        return missingUser;
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
    public ArrayList<Product> getProducts() {
        return products;
    }
    
    public ArrayList<Product> getProducts(String name, CategoryEnum category) {
        ArrayList<Product> tempList = new ArrayList<>();
        String productName = name.toLowerCase();
        for(Product p : products){
            if(productName.equals("")){
                if (p.getCategory().equals(category)){
                    tempList.add(p);
                }
            }            
            else if(p.getName().contains(productName) && p.getCategory().equals(category)){
                tempList.add(p);
            }
            else if (p.getName().contains(productName)){
                tempList.add(p);
            }
        }
        
        return tempList;
    }

    public Collection<Auction> getAuctions() {
        return auctions;
    }
    
    public boolean InstabuyItem(int amount, int auctionID, int buyerID) throws SQLException {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + "BID: " + buyerID);
            con.InstabuyItem(amount, auctionID, 1);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean addBid(double amount, int auctionID, int buyerID, double price) {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + " BID: " + buyerID + " Price: " + price);
            con.addBid(amount, auctionID, 1, price);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void updateAuctionsFromDB(ArrayList<Integer> newAuctionIDs) {
        Auction tempAuction;
        for (int i : newAuctionIDs) {
                tempAuction = con.getAuction(i);
                
                if (tempAuction == null) {
                    System.out.println("Auction is null");
                    }

                for (Auction A : auctions) {
                    if (A.getId() == tempAuction.getId()) {
                        auctions.set(auctions.indexOf(A), tempAuction);
                        System.out.println(tempAuction.getProduct().getName() + "Replaced in list.");
                    }
                }
                if(!auctions.contains(tempAuction) && tempAuction != null){
                    auctions.add(tempAuction);
                    System.out.println(tempAuction.getProduct().getName() + "New Auction added to list.");
                }
            }
        }

        public void updateUsers()
        {
            this.users.clear();
            for (User u : this.con.getAllUsers())
            {
                this.addUser(u);
            }
        }
    
        @Override
        public void update (Observable o, Object arg) {
            ArrayList<Integer> tempList = (ArrayList<Integer>) arg;
            System.out.println("New auctions found.");
            updateAuctionsFromDB(tempList);
        }


    public void updateAuction(Auction auction) {
        con.updateAuction(auction);
    }
}
