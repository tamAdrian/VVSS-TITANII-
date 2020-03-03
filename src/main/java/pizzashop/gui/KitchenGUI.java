package pizzashop.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KitchenGUI {

    private static final Logger logger = Logger.getAnonymousLogger();

    public void kitchenGUI() {
        VBox vBoxKitchen = null;

        try {
            vBoxKitchen = FXMLLoader.load(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));
        } catch (IOException e) {
            logger.log(Level.INFO, e.getMessage());
        }

        Stage stage = new Stage();
        stage.setTitle("Kitchen");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit Kitchen window?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                stage.close();
            }
            // consume event
            else if (result.isPresent() && result.get() == ButtonType.NO) {
                event.consume();
            } else {
                event.consume();
            }
        });
        stage.setAlwaysOnTop(false);
        stage.setScene(new Scene(vBoxKitchen));
        stage.show();
        stage.toBack();
    }
}

