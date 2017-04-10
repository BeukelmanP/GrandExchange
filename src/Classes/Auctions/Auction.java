package Classes.Auctions;

import Classes.Bid;
import Classes.Product;
import Classes.User;
import java.text.DecimalFormat;
import java.util.*;

public abstract class Auction {

    User seller;
    private int id;
    private double currentPrice;
    private double instabuyPrice;
    private boolean instabuyable;
    private int productQuantity;
    private Bid currentBid;
    private ArrayList<Bid> bids;
    private Product product;
    private StatusEnum status;
    private String description;
    private String[] imageURLs;
    private double instabuy;

    /**
     *
     * @param seller
     * @param product
     * @param price
     * @param quantity
     * @param status
     * @param description
     * @param imageURLs
     * @param instabuy
     */
    public Auction(int id, User seller, Product product, double price, int quantity, StatusEnum status, String description, String imageURLs, double instabuy) {
        this.id = id;
        this.seller = seller;
        this.product = product;
        DecimalFormat decim = new DecimalFormat("#.00");
        this.currentPrice = price;
        this.productQuantity = quantity;
        this.instabuyable = false;
        this.status = status;
        this.description = description;
        this.imageURLs = imageURLs.split(";");
        bids = new ArrayList<>();
        this.instabuy = instabuy;
    }

    /**
     *
     * @param seller
     * @param product
     * @param quantity
     * @param price
     * @param instabuyprice
     * @param status
     * @param description
     * @param imageURLs
     */
    public Auction(User seller, Product product, int quantity, double price, double instabuyprice, StatusEnum status, String description, String imageURLs) {
        this.seller = seller;
        this.product = product;
        this.currentPrice = price;
        this.productQuantity = quantity;
        this.instabuyPrice = instabuyprice;
        this.instabuyable = true;
        this.status = status;
        this.description = description;
        this.imageURLs = imageURLs.split(";");
        bids = new ArrayList<>();  
    }

    /**
     * returns the highest bid at the moment
     *
     * @return Bid
     */
    public Bid getBestBid() {
        return null;
    }

    /**
     *
     * @param bid
     */
    public void addBid(Bid bid) {
        if (bid == null) {
            throw new IllegalArgumentException();
        } else {
            bids.add(bid);
        }
    }

    /**
     * gets all bids made on the auction
     *
     * @return List<Bid>
     */
    public ArrayList<Bid> getBids() {
        return bids;
    }

    /**
     * send a request for email contact
     *
     * @param emailRequester : may not be empty nor null
     */
    public void sendMailRequest(String emailRequester) {

    }

    public Product getProduct() {
        return product;
    }

    public String getDescription() {
        return description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int buyAmount) {
        productQuantity = productQuantity - buyAmount;
    }

    public String[] getImageURLs() {

        return imageURLs;
    }

    public User getSeller() {

        return seller;
    }

}
