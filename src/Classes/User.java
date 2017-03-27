package Classes;

import java.util.*;

public class User {

	Grand_Exchange manages;
	private int BSN;
	private String username;
	private String password;
	private String alias;
	private String email;
	private boolean verified;
	private double saldo;
	Collection<Bid> bids;
	Collection<Auction> placedAuctions;
	Collection<Queue_Purchase> placedOrders;

}