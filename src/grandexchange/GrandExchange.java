/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grandexchange;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Product;
import Classes.User;
import Controllers.AuctionController;
import java.io.IOException;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author piete
 */
public class GrandExchange extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Auction.fxml"));
        Parent root = loader.load();
        //Make demo Auction
        AuctionController controller = loader.<AuctionController>getController();
        User user = new User("Kyle", "PassWW");
        Date date = new Date();
        Product product = new Product("0479588001", "Riem", "PREMIUM QUALITY. Een smalle riem van suède met leren details. De riem heeft een metalen gesp. Breedte 3 cm. ");
        Auction auction = new Countdown(user, product, 15, 20, 1, 600, 10,StatusEnum.New, "Het product komt nieuw in de verpakking");
        Bid bid = new Bid(user, 15);
//        auction.addBid(bid);
        //set demo auction
        controller.setAuction(auction);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Auction");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
