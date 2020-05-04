package pizzashop;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {

    private PaymentType paymentType;

    private PizzaService service;

    @BeforeEach
    void setUp() {
        paymentType = PaymentType.CARD;
        MenuRepository menuRepository = new MenuRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        service = new PizzaService(menuRepository, paymentRepository);
    }

    @DisplayName("ECP - valid table number")
    @Test
    void addPaymentTableValidECP() {
        int initPayments = service.getPayments().size();
        try {
            service.addPayment(2, paymentType, 10);
            assertEquals(initPayments + 1, service.getPayments().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("disabled")
    @Disabled
    void addPaymentDisabled() {
//        service.addPayment("wef", paymentType, 10);
    }

    @DisplayName("ECP - valid amount")
    @ParameterizedTest
    @ValueSource(doubles = {10, 20, 30})
    void addPaymentAmountValidECP(double amount) {
        int initPayments = service.getPayments().size();
        try {
            service.addPayment(2, paymentType, amount);
            assertEquals(initPayments + 1, service.getPayments().size());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("ECP - invalid table number")
    void addPaymentTableInvalidECP() {
        try {
            service.addPayment(-1, paymentType, 10);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("ECP - invalid amount")
    void addPaymentAmountInvalidECP() {
        try {
            service.addPayment(1, paymentType, -10);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @DisplayName("BVA - valid table number")
    @RepeatedTest(3)
    void addPaymentTableValidBVA() {
        int initPayments = service.getPayments().size();
        try {
            service.addPayment(1, paymentType, 1);
            assertEquals(initPayments + 1, service.getPayments().size());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("BVA - invalid table number")
    void addPaymentTableInvalidBVA() {
        try {
            service.addPayment(0, paymentType, 10);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @DisplayName("BVA - valid amount")
    @ParameterizedTest
    @ValueSource(doubles = {1, Double.MAX_VALUE})
    void addPaymentAmountValidBVA(double amount) {
        int initPayments = service.getPayments().size();
        try {
            service.addPayment(2, paymentType, amount);
            assertEquals(initPayments + 1, service.getPayments().size());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @DisplayName("BVA - invalid amount")
    @ParameterizedTest
    @ValueSource(doubles = {0})
    void addPaymentAmountInvalidBVA(double amount) {
        try {
            service.addPayment(1, paymentType, amount);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

}