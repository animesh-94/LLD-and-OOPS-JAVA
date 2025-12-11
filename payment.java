interface PaymentMethod {
    void pay(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private String cardNumber; // full card number

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        System.out.println("Charging $" + amount + " to Credit Card ending in " + last4);
    }
}

class PayPalPayment implements PaymentMethod {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Sending $" + amount + " via PayPal account: " + email);
    }
}

class PaymentProcessor {
    public void checkout(double amount, PaymentMethod method) {
        method.pay(amount);  // Polymorphic call
    }
}

public class Main {
    public static void main(String[] args) {

        PaymentMethod cc = new CreditCardPayment("1234567890123456");
        PaymentMethod pp = new PayPalPayment("user@example.com");

        PaymentProcessor processor = new PaymentProcessor();

        System.out.println("---- CREDIT CARD PAYMENT ----");
        processor.checkout(1500.75, cc);

        System.out.println("\n---- PAYPAL PAYMENT ----");
        processor.checkout(800.50, pp);
    }
}
