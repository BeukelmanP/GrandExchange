package Classes.Auctions;

import Classes.Bid;
import Classes.Product;
import Classes.User;
import java.util.*;

public abstract class Auction {

    User seller;
    private Date timeCreated;
    private double currentPrice;
    private double instabuyPrice;
    private boolean instabuyable;
    private int productQuantity;
    private Bid currentBid;
    Collection<Bid> bids;
    Product forSale;
    StatusEnum status;
    String description;

    /**
     *
     * @param seller : seller of product
     * @param product : product
     * @param price : initial price of auction
     * @param quantity : quantity
     */
    public Auction(User seller, Product product, double price, int quantity) {
        this.seller = seller;
        this.forSale = product;
        this.currentPrice = price;
        this.productQuantity = quantity;
        this.instabuyable = false;
    }

    /**
     *
     * @param seller : seller of product
     * @param product : product
     * @param quantity : quantity
     * @param price : initial price of auction
     * @param instabuyprice : price to buy direct
     */
    public Auction(User seller, Product product, int quantity, double price, double instabuyprice) {
        this.seller = seller;
        this.forSale = product;
        this.currentPrice = price;
        this.productQuantity = quantity;
        this.instabuyPrice = instabuyprice;
        this.instabuyable = true;

    }

    /**
     * returns the highest bid at the moment
     *
     * @return Bid
     */
    public Bid getBestBid() {
        return null;
    }

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
    public List<Bid> getBids() {
        return null;
    }

    /**
     * send a request for email contact
     *
     * @param emailRequester : may not be empty nor null
     */
    public void sendMailRequest(String emailRequester) {

    }
}
