package pizzashop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTestMock {

    private String fileName = "/data/payments_test.txt";
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PaymentRepository.class.getResource(fileName).toURI())))) {
            bw.write("");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        paymentRepository = new PaymentRepository(fileName);
    }

    @Test
    void add() {
        Payment payment = mock(Payment.class);

        paymentRepository.add(payment);

        assertEquals(paymentRepository.getAll().size(), 1);
    }

    @Test
    void getAll() {
        Payment payment1 = mock(Payment.class);
        Payment payment2 = mock(Payment.class);

        paymentRepository.add(payment1);
        paymentRepository.add(payment2);

        assertEquals(paymentRepository.getAll().size(), 2);
    }
}