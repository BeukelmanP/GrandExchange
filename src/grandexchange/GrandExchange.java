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
        Product product = new Product("0479588001", "Audi R8 4.2 V8 FSI CARBON", "De Audi R8 Coupé is de benchmark. Met een vermogen van 610 pk, een topsnelheid van 330 km/h en een acceleratie van 0 naar 100 km/h in 3,2 is de topversie - de Audi R8 V10 plus - goed voor adembenemende prestaties. Het scherpe design past daar goed bij. De Audi R8 Coupé is onmiskenbaar een bolide met race-DNA.");
        Auction auction = new Countdown(user, product, 59999, 1, 1, 600, 10,StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.","https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
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
