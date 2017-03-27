package Classes;

import java.util.*;

public class Auction {

	User seller;
	private Date timeCreated;
	private double currentPrice;
	private double instabuyPrice;
	private boolean instabuyable;
	private int productQuantity;
	private Bid currentBid;
	Collection<Bid> bids;
	Collection<Product> forSale;

}