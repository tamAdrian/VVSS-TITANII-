package pizzashop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.*;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;


class PizzaServiceGetTotalAmountTest {

    private String fileName = "/data/payments_test.txt";
    private MenuRepository menuRepository = new MenuRepository();
    private PaymentRepository paymentRepository;
    private PaymentType paymentType = PaymentType.CARD;
    private PizzaService service;

    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PaymentRepository.class.getResource(fileName).toURI())))) {
            bw.write("");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @DisplayName("test P01: 1 - 2T - 3 - 10")
    @Test
    void testP01() {
        paymentRepository = new PaymentRepository("ds.txt");
        service = new PizzaService(menuRepository, paymentRepository);
        assertEquals(service.getTotalAmount(paymentType), 0.0f);
    }

    @DisplayName("test P02: 1 - 2F - 4T - 5 - 10")
    @Test
    void testP02() {
        paymentRepository = new PaymentRepository(fileName);
        service = new PizzaService(menuRepository, paymentRepository);
        assertEquals(service.getTotalAmount(paymentType), 0.0f);
    }

    private void initService() {
        paymentRepository = new PaymentRepository(fileName);
        service = new PizzaService(menuRepository, paymentRepository);
    }

    @DisplayName("test P04: 1 - 2F - 4F - 6T - 7F - 6F- 9 - 10")
    @Test
    void testP03() {

        try {
            initService();
            service.addPayment(1, PaymentType.CASH, 10);
            assertEquals(service.getTotalAmount(paymentType), 0.0f);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("test P05: 1 - 2F - 4F - 6T - 7T - 8 - 6F - 9 - 10")
    @Test
    void testP05() {
        initService();
        try {
            service.addPayment(1, PaymentType.CASH, 10);
            service.addPayment(1, PaymentType.CARD, 10);
            assertEquals(service.getTotalAmount(paymentType), 10);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}