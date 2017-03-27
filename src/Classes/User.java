package Classes;

import Classes.Auctions.Auction;
import java.util.*;
import Exceptions.*;

/**
 * The User class represents a user from our application.
 * @author  Lesley Peters
 * @version 0.1, March 27 
 */
public class User {

	Grand_Exchange manages;
	private int BSN;
	private String username;
	private String password;
	private String alias;
	private String email;
	private boolean verified;
	private double saldo;
	private List<Bid> bids;
	private List<Auction> placedAuctions;
	private List<Queue_Purchase> placedOrders;
        private List<Transaction> transactions;
        
        public User(String username, String Password){
            // TODO: get data from database and fill in the properties
            this.username = username;
            this.password = password;
        }
        
        /**
	 * Adds feedback to another user.
         * @param receiver is the username from the feedback receiver
         * @param description is the string that contains the feedback message
	 * @exception EmptyFieldException is thrown when the receiver or description String is empty
	 */
        public void sendFeedback(String receiver, int rating, String description) throws EmptyFieldException{
            // TODO: send feedback to other user
            
            
            if(receiver == ""){
                throw new EmptyFieldException("Feedback receiver can't be empty.");
            }
            
            if(description == ""){
                throw new EmptyFieldException("Feedback description can't be empty.");
            }
            
            throw new UnsupportedOperationException("Not implemented yet.");
        }

        /**
	 * Creates a new auction for this user
         * @param auction is the new auction added to the user
	 */
        public void addAuction(Auction auction){
            this.placedAuctions.add(auction);
        }
        
        /**
	 * Adds feedback to another user.
         * @param auction is the auction that needs be removed from the user's lists
	 */
        public void removeAuction(Auction auction){
            this.placedAuctions.remove(auction);
        }
        
        /**
	 * Adds feedback to another user.
         * @param auction is the auction where the user wants to place the bid
         * @param bid is the bid that the user wants to place
	 */
        public void requestPlaceBid(Auction auction, Bid bid){
            // TODO: place the bid to the given auction, then return if the bid succeeded or not
            throw new UnsupportedOperationException("Not implemented yet.");
        }
        
        // Het lijkt me logisch dat we een bieding niet kunnen removen, mocht je dit wel logisch vinden, vul hem dan maar in :P
        public void requestRemoveBid(){throw new UnsupportedOperationException("Not implemented yet.");}
        
        /**
	 * Adds or removes momey from the user's saldo
         * @param amount is the amount of money to be added or removed from the user
	 * @exception NotEnoughMoneyException is thrown when the user doesn't have enough money
	 */
        public void addSaldo(double amount) throws NotEnoughMoneyException{
            double oldSaldo = this.saldo;
            this.saldo += amount;
            if(this.saldo<0){
                throw new NotEnoughMoneyException("Not enough money.");
            }
        }
        
        /**
	 * Returns the amount of money the user has
         * @return the amount of money the user has
	 */
        public double getSaldo(){
            return this.saldo;
        }
        
        /**
	 * Receives the transaction history from this user
         * @return a list with transaction objects
	 */
        public List<Transaction> getTransactionHistory(){
            return this.transactions;
        }
        
        /**
	 * Adds a queue purchase
         * @param purchase is the purchase that needs to be added to the list
	 */
        public void addQueuePurchase(Queue_Purchase purchase){
            this.placedOrders.add(purchase);
        }
        
         /**
	 * removes a queue purchase
         * @param purchase is the purchase that needs to be removed from the list
	 */
        public void removeQueuePurchase(Queue_Purchase purchase){
            this.placedOrders.remove(purchase);
        }
        
        /**
	 * Method that sends a request to verify the user's account
	 */
        public void requestVerification(){
            // TODO: implement the method to verify a user.
            throw new UnsupportedOperationException("Not implemented yet.");
        }
}