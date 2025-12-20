// Strategy Interface
// Defines a common contract for all payment methods
interface PaymentStrategy {
    void payment();
}

// Concrete Strategy 1
// Implements payment using Credit Card
class CreditCardPayment implements PaymentStrategy {
    @Override
    public void payment() {
        System.out.println("Payment is done via Credit Card..");
    }
}

// Concrete Strategy 2
// Implements payment using UPI
class UPIPayment implements PaymentStrategy {
    @Override
    public void payment() {
        System.out.println("Payment is done via UPI payment interface..");
    }
}

// Concrete Strategy 3
// Implements payment using Bitcoin
class BitCoinPayment implements PaymentStrategy {
    @Override
    public void payment() {
        System.out.println("Payment is done via BitCoin..");
    }
}

// Concrete Strategy 4
// Implements payment using Stripe
class StripePayment implements PaymentStrategy {
    @Override
    public void payment() {
        System.out.println("Payment is done via Stripe Interface....");
    }
}

// Context Class
// Uses a PaymentStrategy to perform payment
// The strategy can be changed at runtime
class PaymentProcessor {

    // Reference to the strategy interface
    private PaymentStrategy paymentStrategy;

    // Constructor injection of strategy
    public PaymentProcessor(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Executes the payment using the current strategy
    public void processPayment() {
        paymentStrategy.payment();
    }

    // Allows changing the strategy dynamically at runtime
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}

// Client Code
// Demonstrates how different strategies are selected and used
public class Main {
    public static void main(String[] args) {

        // Using Credit Card payment strategy
        PaymentProcessor processor =
                new PaymentProcessor(new CreditCardPayment());
        processor.processPayment();

        // Switching strategy to UPI at runtime
        processor.setPaymentStrategy(new UPIPayment());
        processor.processPayment();

        // Switching strategy to Bitcoin at runtime
        processor.setPaymentStrategy(new BitCoinPayment());
        processor.processPayment();
    }
}
