package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PizzaServiceTestIntegrationStep3 {

    private String fileName = "/data/payments_test.txt";

    @Mock
    private MenuRepository menuRepository;

    private PaymentRepository paymentRepository;

    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PaymentRepository.class.getResource(fileName).toURI())))) {
            bw.write("");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        MockitoAnnotations.initMocks(this);

        paymentRepository = new PaymentRepository(fileName);

        pizzaService = new PizzaService(menuRepository, paymentRepository);
        try {
            pizzaService.addPayment(1, PaymentType.CARD, 10.0);
            pizzaService.addPayment(1, PaymentType.CASH, 10.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPayments() {
        assertEquals(pizzaService.getPayments().size(), 2);
    }

    @Test
    void getTotalAmount() {
        assertEquals(pizzaService.getTotalAmount(PaymentType.CARD), 10.0);
    }
}