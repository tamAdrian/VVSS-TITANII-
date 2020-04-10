package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo) {
        this.menuRepo = menuRepo;
        this.payRepo = payRepo;
    }

    public List<MenuDataModel> getMenuData() {
        return menuRepo.getMenu();
    }

    public List<Payment> getPayments() {
        return payRepo.getAll();
    }

    public void addPayment(int table, PaymentType type, double amount) throws Exception {
        if (table >= 1 && table <= 8 && amount > 0) {
            Payment payment = new Payment(table, type, amount);
            payRepo.add(payment);
        } else {
            throw  new Exception("invalid parameters");
        }
    }

    public double getTotalAmount(PaymentType type) {
        double total = 0.0f; //1
        List<Payment> payments = getPayments();//1
        if(payments == null) { //2
            return total; //3
        }
        if (payments.isEmpty())//4
            return total; //5
        for (Payment p : payments) { //6
            if (p.getType().equals(type)) //7
                total += p.getAmount(); //8
        }
        return total; //9
    } //10

}
