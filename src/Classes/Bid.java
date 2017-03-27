package Classes;

import Classes.Auctions.Auction;
import java.util.Date;

public class Bid {

    private double amount;
    private Date timeCreated;
    private User placer;

    public Bid(Auction auction, User placer, double amount) {
        this.placer = placer;
        this.amount = amount;

        Date date = new Date();
        this.timeCreated = date;
    }

    /**
     * This metod is used to get a string of the placer of this bid.
     *
     * @return User
     */
    public String getPlacerUsername() {
        return this.placer.toString();
    }

}
