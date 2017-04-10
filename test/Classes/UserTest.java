/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import Classes.Auctions.Auction;
import Classes.Auctions.StatusEnum;
import Exceptions.EmptyFieldException;
import Exceptions.NotEnoughMoneyException;
import java.util.Collection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Robin
 */
public class UserTest {
    User TestUser;
    Grand_Exchange GE;
    Product p;
    Auction a;
    Transaction trans;
    
    
    @Before
    public void setUp() {
        //gebruiker wachtwoord
        TestUser = new User("Robin", "test");

    }

    @Test(expected= EmptyFieldException.class)
    public void TestSendFeedbackNoReceiver() throws EmptyFieldException {
      TestUser.sendFeedback("", 0, "testtest");
    }
    
     @Test(expected= EmptyFieldException.class)
    public void TestSendFeedbackNoDescription() throws EmptyFieldException {
      TestUser.sendFeedback("test", 0, "");
    }
    
    @Test
    public void TestaddAuction()
    {
        p = new Product("12345", "testprodcut", "testomschrijving");
        a = new Auction(TestUser,p, 10, 3, StatusEnum.New, "omschrijving");
        TestUser.addAuction(a);
        assertEquals(1, GE.getAuctions().size());
        
        
    }  @Test
    public void TestRemoveAuction()
    {
        p = new Product("12345", "testprodcut", "testomschrijving");
        a = new Auction(TestUser,p, 10, 3, StatusEnum.New, "omschrijving");
        TestUser.addAuction(a);
        assertEquals(1, GE.getAuctions().size());
        TestUser.removeAuction(a);
        assertEquals(0, GE.getAuctions().size());
    }
    
    @Test
    public void TestRequestPlaceBid()
    {
        Bid b = new Bid(TestUser, 10);
        TestUser.requestPlaceBid(a, b);
        assertEquals(1, a.getBids().size());
        
    }
    @Test
    public void TestRequestRemoveBid()
    {
     Bid b = new Bid(TestUser, 10);
        TestUser.requestPlaceBid(a, b);
       assertEquals(1, a.getBids().size());
        TestUser.requestRemoveBid();
        assertEquals(0, a.getBids().size());
    }
    @Test
    public void TestAddSaldo() throws NotEnoughMoneyException
    {
        TestUser.addSaldo(10);
        
        assertEquals(10,TestUser.getSaldo(),1);
    }
    
    @Test
    public void TestaddQueuePurchase()
    {
        Queue_Purchase q = new Queue_Purchase(5, 5, 10);
        TestUser.addQueuePurchase(q);
       
    }
    
      @Test
    public void TestRemoveQueuePurchase()
    {
        Queue_Purchase q = new Queue_Purchase(5, 5, 10);
        TestUser.addQueuePurchase(q);
        
        TestUser.removeQueuePurchase(q);
        
    }
    
    @Test
    public void TestrequestVerification()
    {
        
    }
}
