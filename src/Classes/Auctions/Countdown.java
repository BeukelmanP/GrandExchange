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
public class Countdown extends Auction {

    private double priceloweringAmount;
    private double priceloweringDelay;
    private double minPrice;
    private Date creationDate;

    public Countdown(User seller, Product product, int quantity, double price, double instabuyprice,double priceloweringAmount, double priceloweringDelay, double minprice,StatusEnum status, String description,String imageURLs) {
        super(seller, product, quantity, price, instabuyprice,status,description,imageURLs);
        this.priceloweringAmount= priceloweringAmount;
        this.priceloweringDelay=priceloweringDelay;
        this.minPrice= minprice;
    }

    public Countdown(int id,User seller, Product product, int quantity , double price, double priceloweringAmount, double priceloweringDelay, double minprice,StatusEnum status, String description,String imageURLs, double instabuy, Date creatDate) {
        super(id, seller, product, price, quantity,status,description,imageURLs, instabuy);
        this.priceloweringAmount= priceloweringAmount;
        this.priceloweringDelay=priceloweringDelay;
        this.minPrice= minprice;
        this.creationDate = creatDate;
    }

}
