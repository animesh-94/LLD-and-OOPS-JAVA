import java.util.*;

// ================= STRATEGY PATTERN =================

// Payment Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Payment Strategies
class UPIPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
    }
}

class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Card");
    }
}

class WalletPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Wallet");
    }
}

// ================= FACTORY PATTERN =================

// Factory to create payment strategy dynamically
class PaymentFactory {

    public static PaymentStrategy getPaymentMethod(String type) {
        switch (type.toUpperCase()) {
            case "UPI":
                return new UPIPayment();
            case "CARD":
                return new CardPayment();
            case "WALLET":
                return new WalletPayment();
            default:
                throw new IllegalArgumentException("Invalid payment method");
        }
    }
}

// ================= OBSERVER PATTERN =================

// Observer Interface
interface OrderObserver {
    void update(String orderId, String status);
}

// Subject Interface
interface OrderSubject {
    void addObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObservers();
}

// Concrete Observers
class User implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        System.out.println("User notified → Order " + orderId + " is " + status);
    }
}

class Restaurant implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        System.out.println("Restaurant notified → Order " + orderId + " is " + status);
    }
}

class DeliveryPartner implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        System.out.println("Delivery Partner notified → Order " + orderId + " is " + status);
    }
}

// ================= ORDER (SUBJECT) =================

class Order implements OrderSubject {

    private String orderId;
    private String status;
    private List<OrderObserver> observers = new ArrayList<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(orderId, status);
        }
    }

    public void updateStatus(String status) {
        this.status = status;
        System.out.println("\nOrder Status Updated → " + status);
        notifyObservers();
    }
}

// ================= MAIN =================

public class Main {
    public static void main(String[] args) {

        // Create Order
        Order order = new Order("ORD-101");

        // Register Observers
        order.addObserver(new User());
        order.addObserver(new Restaurant());
        order.addObserver(new DeliveryPartner());

        // Select payment method using Factory + Strategy
        PaymentStrategy payment = PaymentFactory.getPaymentMethod("UPI");
        payment.pay(499);

        // Order lifecycle updates
        order.updateStatus("ORDER PLACED");
        order.updateStatus("FOOD PREPARING");
        order.updateStatus("OUT FOR DELIVERY");
        order.updateStatus("DELIVERED");
    }
}
