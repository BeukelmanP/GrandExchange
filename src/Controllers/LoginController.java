/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author piete
 */
public class LoginController implements Initializable {



        private Database.Connection conn = new Database.Connection();
        @FXML
        AnchorPane currentPane;

        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // TODO
        }

        @FXML
        TextField textfield_username;
        @FXML
        TextField textfield_password;

        @FXML
        public void button_loginUser() throws IOException {
            if (conn.getUser(textfield_username.getText(), textfield_password.getText()) != null) {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Welcome.fxml"));
                Parent root = loader.load();
                newStage.setScene(new Scene(root));
                newStage.show();

                Stage currentStage = (Stage) currentPane.getScene().getWindow();
                currentStage.close();
            }
        }

        @FXML
        public void button_registerUser() throws IOException {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Registration.fxml"));
            Parent root = loader.load();
            newStage.setScene(new Scene(root));
            newStage.showAndWait();

            //Stage currentStage = (Stage)currentPane.getScene().getWindow();
            //currentStage.close();
        }
    }
