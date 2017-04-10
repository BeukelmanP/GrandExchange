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

    public Standard(int id, User seller, Product product, double price, int quantity, Date timeEnd,StatusEnum status, String description,String imageURLs, double instabuy) {
        super(id, seller, product, price, quantity,status,description,imageURLs,instabuy);
        this.timeEnd = timeEnd;

    }

    public Standard(User seller, Product product, int quantity, double price, double instabuyprice, Date timeEnd,StatusEnum status, String description,String imageURLs) {
        super(seller, product, quantity, price, instabuyprice,status,description,imageURLs);
        this.timeEnd = timeEnd;

    }
}
