package pizzashop.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pizzashop.model.PaymentType;
import pizzashop.service.PizzaService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentAlert implements PaymentOperation {
    private PizzaService service;
    private static final String SEPARATOR = "--------------------------";
    private static final Logger logger = Logger.getAnonymousLogger();

    public PaymentAlert(PizzaService service) {
        this.service = service;
        logger.setLevel(Level.INFO);
    }

    @Override
    public void cardPayment() {
        logger.log(Level.INFO, SEPARATOR);
        logger.log(Level.INFO, "Paying by card...");
        logger.log(Level.INFO, "Please insert your card!");
        logger.log(Level.INFO, SEPARATOR);
    }

    @Override
    public void cashPayment() {
        logger.log(Level.INFO, SEPARATOR);
        logger.log(Level.INFO, "Paying cash...");
        logger.log(Level.INFO, "Please show the cash...!");
        logger.log(Level.INFO, SEPARATOR);
    }

    @Override
    public void cancelPayment() {
        logger.log(Level.INFO, SEPARATOR);
        logger.log(Level.INFO, "Payment choice needed...");
        logger.log(Level.INFO, SEPARATOR);
    }

    public void showPaymentAlert(int tableNumber, double totalAmount) throws Exception {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table " + tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent() && result.get() == cardPayment) {
            cardPayment();
            service.addPayment(tableNumber, PaymentType.CARD, totalAmount);
        } else if (result.isPresent() && result.get() == cashPayment) {
            cashPayment();
            service.addPayment(tableNumber, PaymentType.CASH, totalAmount);
        } else {
            cancelPayment();
        }
    }
}
