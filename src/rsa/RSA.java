
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
