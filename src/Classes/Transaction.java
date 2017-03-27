package Classes;

import Classes.Auctions.Auction;
import java.util.*;

public class Transaction {

    private double amount;
    Collection<Bid> fromOwnerOf;
    Collection<Auction> toOwnerOf;

    /**
     * returns the total price
     *
     * @return double
     */
    public double getAmount() {
        return 0;
    }

    /**
     * returns Auction of transaction
     *
     * @return Auction
     */
    public Auction getAuction() {
        return null;
    }

}
