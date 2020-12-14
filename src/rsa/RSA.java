
package rsa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kennelra
 */
public class RSA extends Application {
    
    // Prime number checker
    public static boolean primeNumberChecker(int p) {
        // Check if the number is negative
        // We also know that 0 and 1 are not prime, so we include them
        // in the check here
        if (p <= 1) {
            return false;
        }
        // Check if number is 2 or 3
        if (p == 2) {
            return true;
        }
        if (p == 3) {
            return true;
        }
        // Otherwise, all prime numbers greater than 3 can be written in the
        // form 6n+1 or 6n-1, so we'll use that for the check
        // First, check if p can be put in the form 6n+1
        int checkValue = (6 * p) + 1;
        int LHS = p - 1;
        checkValue = LHS % 6;
        if (checkValue == 0) {
            return true;
        }
        // Check if p can be put in the form 6n-1
        checkValue = (6 * p) - 1;
        LHS = p + 1;
        checkValue = LHS % 6;
             
        if (checkValue == 0) {
            return true;
        }
        // If it can't, then it's not prime
        return false;
    }
    
    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RSAFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("RSA Encryption Key Generator");
        stage.setScene(scene);
        stage.show();
    }
    
    // main ----------------------------------
    public static void main(String[] args) {
        launch(args);
    } // end main
    
}
