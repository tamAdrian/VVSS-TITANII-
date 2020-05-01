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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockingDetails;

class PizzaServiceTestMock {

    private String fileName = "/data/payments_test.txt";

    @Mock
    private MenuRepository menuRepository;

    @Mock
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

        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(1, PaymentType.CARD, 10.0));
        Mockito.when(paymentRepository.getAll())
                .thenReturn(payments);

        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @Test
    void getPayments() {
        try {
            assertEquals(pizzaService.getPayments().size(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTotalAmount() {
        assertEquals(pizzaService.getTotalAmount(PaymentType.CARD), 10.0);
    }
}