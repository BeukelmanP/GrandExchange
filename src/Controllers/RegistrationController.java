/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.Connection;
import static java.awt.SystemColor.text;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Gebruiker
 */
public class RegistrationController implements Initializable {

    private Database.Connection conn = new Database.Connection();
    @FXML AnchorPane currentPane;
    
    @FXML TextField textfield_bsn;
    @FXML TextField textfield_username;
    @FXML TextField textfield_password;
    @FXML TextField textfield_email;
    @FXML TextField textfield_alias;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML public void button_registerUser()
    {   
        try
        {
            int bsn = Integer.parseInt(textfield_bsn.getText().trim());
            if (bsn > 999999)
            {
                System.out.println("BSN must be exactly 6 characters long");
            }
            else
            {
                conn.setUser_REGISTER(bsn, textfield_username.getText(), textfield_password.getText(), textfield_alias.getText(), textfield_email.getText(), 0);
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("BSN field must constain a number");
        }
    }
}
