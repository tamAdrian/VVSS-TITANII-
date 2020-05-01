package pizzashop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTestIntegrationStep2 {

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

        Payment payment1 = mock(Payment.class);
        Payment payment2 = mock(Payment.class);
        Mockito.when(payment1.getType()).thenReturn(PaymentType.CASH);
        Mockito.when(payment2.getAmount()).thenReturn(10.0);
        Mockito.when(payment2.getType()).thenReturn(PaymentType.CARD);
        paymentRepository.add(payment1);
        paymentRepository.add(payment2);

        pizzaService = new PizzaService(menuRepository, paymentRepository);
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