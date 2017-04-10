/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class AuctionController implements Initializable {

    @FXML
    private ImageView bigProductImage;
    @FXML
    private ImageView sellerImage;
    @FXML
    private Label sellerName;
    @FXML
    private Label productTitle;
    @FXML
    private Label productStatus;
    @FXML
    private Label countdownCurrentPrice;
    @FXML
    private Label countdownAvailableUnits;
    @FXML
    private TextArea productDescription;
    @FXML
    private TextArea auctionDescription;
    @FXML
    private Button closeButton;
    @FXML
    private Button countdownBuyBtn;
    @FXML
    private TextField txtUnitstoBuy;
    @FXML
    private ScrollPane imagesPane;
    @FXML
    private ScrollPane recentPurchasesPane;

    Countdown countdownAuction;
    Direct directAuction;
    Standard standardAuction;
    private User loggedInUser;
    private Grand_Exchange GX;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUp(Auction auction, Grand_Exchange GX) {
        this.GX = GX;
        productTitle.setText(auction.getProduct().getName());
        productDescription.setText(auction.getProduct().getDescription());
        auctionDescription.setText(auction.getDescription());
        productStatus.setText(setStatus(auction.getStatus()));
        int i = 0;
        Pane imagePane = new Pane();
        imagePane.setPrefWidth(85 * auction.getImageURLs().length);
        imagePane.setPrefHeight(70);
        for (String URL : auction.getImageURLs()) {
            ImageView image = new ImageView(new Image(URL));
            image.setFitWidth(80);
            image.setFitHeight(60);
            image.relocate(85 * i, 5);
            image.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    ImageView i = (ImageView) e.getSource();
                    bigProductImage.setImage(i.getImage());
                }
            });
            bigProductImage.setImage(new Image(auction.getImageURLs()[0]));
            imagePane.getChildren().add(image);
            i++;
        }

        sellerImage.setImage(new Image(auction.getSeller().getImageURL()));
        sellerName.setText(auction.getSeller().getUsername());
        imagesPane.setContent(imagePane);
        if (auction instanceof Countdown) {
            countdownAuction = (Countdown) auction;
            countdownCurrentPrice.setText("€" + auction.getCurrentPrice());
            if (auction.getProductQuantity() > 1) {
                countdownAvailableUnits.setText("There are " + auction.getProductQuantity() + " units available");
            } else if (auction.getProductQuantity() == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (auction.getProductQuantity() == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }
            setCountdownBuys(auction);

        }

        txtUnitstoBuy.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtUnitstoBuy.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public String setStatus(StatusEnum status) {
        switch (status) {
            case New:
                return "New";
            case GoodAsNew:
                return "As good as new";
            case Refurbished:
                return "Refurbished";
            case Used_Good:
                return "little damage(small dents)";
            case Used_Bad:
                return "annoying damage(Cracks)";
            case Broken:
                return "not fully functional or Broken";
            default:
                return "";
        }
    }

    public void countdownBuyButtonClick() {
        if (Integer.parseInt(txtUnitstoBuy.getText()) <= countdownAuction.getProductQuantity() && Integer.parseInt(txtUnitstoBuy.getText()) > 0) {
            double totalPrice = Double.parseDouble(txtUnitstoBuy.getText()) * countdownAuction.getCurrentPrice();

            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy " + txtUnitstoBuy.getText() + "\nitems with the price of: €" + countdownAuction.getCurrentPrice() + " a item \nand a total of: €" + totalPrice, "Are you sure?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                countdownAuction.addBid(new Bid(GX.loggedInUser, countdownAuction.getCurrentPrice()));
                countdownAuction.setProductQuantity(Integer.parseInt(txtUnitstoBuy.getText()));
                setCountdownBuys(countdownAuction);
                if (countdownAuction.getProductQuantity() > 1) {
                    countdownAvailableUnits.setText("There are " + countdownAuction.getProductQuantity() + " units available");
                } else if (countdownAuction.getProductQuantity() == 1) {
                    countdownAvailableUnits.setText("There is just 1 item left");
                } else if (countdownAuction.getProductQuantity() == 0) {
                    countdownAvailableUnits.setText("There are no items left, you missed it");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Canceled");
            }
        } else if (Integer.parseInt(txtUnitstoBuy.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "You can't buy less than 1 object");
        } else {
            JOptionPane.showMessageDialog(null, "You can't buy more objects than there are available");
        }
    }

    public void setCountdownBuys(Auction auction) {
        Pane BuyPane = new Pane();
        BuyPane.setPrefWidth(371);
        BuyPane.setPrefHeight(75 * auction.getBids().size());
        int a = 0;
        for (Bid b : auction.getBids()) {
            Pane p = new Pane();
            p.setPrefHeight(75);
            p.setPrefWidth(371);
            p.relocate(0, a * 75);
            if ((a % 2) == 0) {
                p.setStyle("-fx-background-color: lightgrey ");
            }
            Label name = new Label();
            name.setText(b.getPlacerUsername());
            name.setFont(new Font("Arial", 17));
            Label price = new Label();
            price.setText("Bought at a price of: €" + b.getAmount());
            price.setFont(new Font("Arial", 14));
            name.relocate(10, 15);
            price.relocate(10, 45);
            p.getChildren().addAll(price, name);
            BuyPane.getChildren().add(p);
            a++;
        }
        recentPurchasesPane.setContent(BuyPane);
    }

    @FXML
    private void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
