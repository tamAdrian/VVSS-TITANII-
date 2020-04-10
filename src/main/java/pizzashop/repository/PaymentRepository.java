package pizzashop.repository;

import java.util.logging.Level;
import java.util.logging.Logger;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private String filename = "/data/payments.txt";
    private List<Payment> paymentList;
    private static final Logger logger = Logger.getAnonymousLogger();

    public PaymentRepository() {
        this.paymentList = new ArrayList<>();
        readPayments();
    }

    public PaymentRepository(String filename) {
        this.filename = filename;
        readPayments();
    }

    private void readPayments() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(PaymentRepository.class.getResource(filename).toURI())))) {
            this.paymentList = new ArrayList<>();
            String line = null;
            while ((line = br.readLine()) != null) {
                Payment payment = getPayment(line);
                paymentList.add(payment);
            }
        } catch (URISyntaxException | NullPointerException | IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private Payment getPayment(String line) {
        Payment item = null;
        if (line == null || line.equals("")) return null;
        StringTokenizer st = new StringTokenizer(line, ",");
        int tableNumber = Integer.parseInt(st.nextToken());
        String type = st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment) {
        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll() {
        return paymentList;
    }

    public void writeAll() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PaymentRepository.class.getResource(filename).toURI())))) {
            for (Payment p : paymentList) {
                String logInfo = p.toString();
                logger.log(Level.INFO, logInfo);
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (URISyntaxException | IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

}
