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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author piete
 */
public class BidTest {

    User seller;
    User bidder;
    Product P;
    Countdown auction;
    Bid bidding;

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
        seller = new User("pieter", "test");
        bidder = new User("henk", "test");
        P = new Product("123456789", "PennenSet", "Set van 20 pennen in rood(4), blauw(6), groen(4) en zwart(6)");
        auction = new Countdown(seller, P, 20, 100, 0.5, 10, 5);
        bidding = new Bid(auction, bidder, 15);
    }

    @After
    public void tearDown() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExeption() {
        Bid bidding1 = new Bid(null, bidder, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExeption2() {
        Bid bidding2 = new Bid(auction, null, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExeption3() {
        Bid bidding3 = new Bid(auction, bidder, 0);
    }

    @Test
    public void testGetPlacerUsername() {
        System.out.println("Test Method: getPlacerUsername:");
        String Actual = bidder.getUsername();
        String Expected = "henk";
        System.out.println("Actual: " + Actual + " Expected: " + Expected + "\n");
        assertEquals(Actual, Expected);
    }

    @Test
    public void testGetAmount() {
        System.out.println("Test Method: getAmount:");
        double Actual = bidding.getAmount();
        double Expected = 15;
        System.out.println("Actual: " + Actual + " Expected: " + Expected + "\n");
        assertEquals(Actual, Expected, 0.00005);
    }

    @Test
    public void testGetAuction() {
        System.out.println("Test Method: getAuction:");
        Auction Actual = bidding.getAuction();
        Auction Expected = auction;
        System.out.println("Actual: " + Actual + " Expected: " + Expected + "\n");
        assertEquals(Actual, Expected);
    }

}
