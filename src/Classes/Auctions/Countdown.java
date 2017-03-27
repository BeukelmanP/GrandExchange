/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;

/**
 *
 * @author piete
 */
public class Countdown extends Auction {

    private double priceloweringAmount;
    private double priceloweringDelay;
    private double minPrice;

    public Countdown(User seller, Product product, int quantity, int price, double instabuyprice,double priceloweringAmount, double priceloweringDelay, double minprice) {
        super(seller, product, quantity, price, instabuyprice);
        this.priceloweringAmount= priceloweringAmount;
        this.priceloweringDelay=priceloweringDelay;
        this.minPrice= minprice;
    }

    public Countdown(User seller, Product product, int price, int quantity ,double priceloweringAmount, double priceloweringDelay, double minprice) {
        super(seller, product, price, quantity);
        this.priceloweringAmount= priceloweringAmount;
        this.priceloweringDelay=priceloweringDelay;
        this.minPrice= minprice;
    }

}
