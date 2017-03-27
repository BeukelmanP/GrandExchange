package Classes;

import Classes.Auctions.Auction;
import java.util.*;

public class Transaction {

    private double amount;
    Collection<Bid> fromOwnerOf;
    Auction toOwnerOf;

    /**
     * returns the total price
     *
     * @return double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * returns Auction of transaction
     *
     * @return Auction
     */
    public Auction getAuction() {
        return toOwnerOf;
    }

}
