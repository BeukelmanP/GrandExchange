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
    Collection<Product> forSale;

    /**
     * returns the highest bid at the moment
     *
     * @return Bid
     */
    public Bid getBestBid() {
        return null;
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
     * @param emailRequester : may not be empty nor null
     */
    public void sendMailRequest(String emailRequester) {

    }
}
