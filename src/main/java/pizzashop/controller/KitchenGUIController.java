package pizzashop.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KitchenGUIController {
    @FXML
    private ListView kitchenOrdersList;
    @FXML
    public Button cook;
    @FXML
    public Button ready;

    private static final Logger logger = Logger.getAnonymousLogger();
    private static final String SEPARATOR = "--------------------------";

    public static final ObservableList<String> order = FXCollections.observableArrayList();
    private Object selectedOrder;
    private Calendar now = Calendar.getInstance();
    private String extractedTableNumberString;
    private int extractedTableNumberInteger;
    //thread for adding data to kitchenOrderList
    public Thread addOrders = new Thread(() -> {
        while (true) {
            Platform.runLater(() -> kitchenOrdersList.setItems(order));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });

    public void initialize() {
        //starting thread for adding data to kitchenOrderList
        addOrders.setDaemon(true);
        addOrders.start();
        //Controller for Cook Button
        cookAction();
        //Controller for Ready Button
        readyAction();
    }

    private void readyAction() {
        ready.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            kitchenOrdersList.getItems().remove(selectedOrder);
            extractedTableNumberString = selectedOrder.toString().subSequence(5, 6).toString();
            extractedTableNumberInteger = Integer.parseInt(extractedTableNumberString);
            logger.log(Level.INFO, SEPARATOR);
            String logInfo = "Table " + extractedTableNumberInteger + " ready at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE);
            logger.log(Level.INFO, logInfo);
            logger.log(Level.INFO, SEPARATOR);
        });
    }

    private void cookAction() {
        cook.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            kitchenOrdersList.getItems().remove(selectedOrder);
            kitchenOrdersList.getItems().add(selectedOrder.toString()
                     .concat(" Cooking started at: ").toUpperCase()
                     .concat(now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)));
        });
    }
}