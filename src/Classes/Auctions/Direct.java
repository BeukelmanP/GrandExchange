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
public class Direct extends Auction {

    public Direct(int id,User seller, Product product, double price, int quantity,StatusEnum status, String description,String imageURLs) {
        super(id, seller, product, price, quantity,status,description,imageURLs);

    }

    public Direct(User seller, Product product, double price, int quantity, double instabuyprice,StatusEnum status, String description,String imageURLs) {
        super(seller, product, quantity, price, instabuyprice,status,description,imageURLs);
    }
}
