/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;
import java.util.Date;

/**
 *
 * @author piete
 */
public class Standard extends Auction {

    private Date timeEnd;

    public Standard(User seller, Product product, double price, int quantity, Date timeEnd) {
        super(seller, product, price, quantity);
        this.timeEnd = timeEnd;

    }

    public Standard(User seller, Product product, int quantity, double price, double instabuyprice, Date timeEnd) {
        super(seller, product, quantity, price, instabuyprice);
        this.timeEnd = timeEnd;

    }
}
