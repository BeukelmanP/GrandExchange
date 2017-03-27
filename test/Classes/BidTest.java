/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author piete
 */
public class BidTest {

    public BidTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        User seller = new User("", "");
        User bidder = new User("", "");
        Product P = new Product(123456789, "PennenSet", "Set van 20 pennen in rood(4), blauw(6), groen(4) en zwart(6)");
        Countdown auction = new Countdown(seller, P, 20, 100, 0.5, 10, 5);
        Bid bidding = new Bid(auction, bidder, 15);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPlacerUsername method, of class Bid.
     */
    @Test
    public void testGetPlacerUsername() {

    }

}
