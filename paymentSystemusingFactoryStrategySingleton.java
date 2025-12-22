// Design a country-aware subscription billing and payment system for a SaaS platform.
// The system must support multiple payment methods, multiple payment gateways, and 
//different billing workflows, while remaining scalable, loosely coupled, and extensible

Client
 |
 v
CountryFactoryProvider
 |
 v
+----------------------------+
| <<interface>>              |
| CountryPaymentFactory      |
|----------------------------|
| + createPaymentMethod()    |
| + getGateway()             |
+-------------+--------------+
              |
     +--------+---------+
     |        |         |
+----------+ +----------+ +--------------+
| India... | | US...    | | Europe...    |
+----------+ +----------+ +--------------+

CountryPaymentFactory
  |
  v
+----------------------------+
| <<interface>>              |
| PaymentMethod              |
|----------------------------|
| + pay(amount, gateway)     |
+-------------+--------------+
      |          |           |
+---------+ +----------+ +----------+
|  UPI    | |  Card    | | Wallet   |
+---------+ +----------+ +----------+

PaymentMethod
 |
 v
+----------------------------+
| <<interface>>              |
| PaymentGateway             |
|----------------------------|
| + charge(amount)           |
+-------------+--------------+
      |          |           |
+----------+ +----------+ +----------+
| Razorpay | | Stripe   | | PayPal   |
+----------+ +----------+ +----------+

BillingFlow (Template Method)
 |
 +-- OneTimeBilling
 +-- RecurringBilling
 +-- TrialBilling

AmountCalculator (Decorator)
 |
 +-- BaseAmountCalculator
 +-- CouponDecorator
 +-- GSTDecorator


// ===================== CLIENT =====================
public class SubscriptionPaymentSystem {

    public static void main(String[] args) {

        double amount = 1000;

        // Step 1: Country-based factory
        CountryPaymentFactory factory =
                CountryFactoryProvider.getFactory("INDIA");

        // Step 2: Payment method (Strategy)
        PaymentMethod method =
                factory.createPaymentMethod("UPI");

        // Step 3: Gateway (Singleton)
        PaymentGateway gateway =
                factory.getGateway();

        // Step 4: Amount calculation (Decorator)
        AmountCalculator calculator =
                new GSTDecorator(
                        new CouponDecorator(
                                new BaseAmountCalculator(), 100));

        double finalAmount = calculator.calculate(amount);

        // Step 5: Billing Flow (Template Method)
        BillingFlow billing =
                new RecurringBilling(method, gateway);

        billing.execute(finalAmount);
    }
}

// ===================== STRATEGY =====================
interface PaymentMethod {
    void pay(double amount, PaymentGateway gateway);
}

// ===================== PAYMENT METHODS =====================
class UPIPayment implements PaymentMethod {
    public void pay(double amount, PaymentGateway gateway) {
        System.out.println("Paying via UPI");
        gateway.charge(amount);
    }
}

class CardPayment implements PaymentMethod {
    public void pay(double amount, PaymentGateway gateway) {
        System.out.println("Paying via Card");
        gateway.charge(amount);
    }
}

class WalletPayment implements PaymentMethod {
    public void pay(double amount, PaymentGateway gateway) {
        System.out.println("Paying via Wallet");
        gateway.charge(amount);
    }
}

// ===================== SINGLETON GATEWAYS =====================
interface PaymentGateway {
    void charge(double amount);
}

class RazorpayGateway implements PaymentGateway {

    private static volatile RazorpayGateway instance;

    private RazorpayGateway() {
        System.out.println("Razorpay Gateway Initialized");
    }

    public static RazorpayGateway getInstance() {
        if (instance == null) {
            synchronized (RazorpayGateway.class) {
                if (instance == null) {
                    instance = new RazorpayGateway();
                }
            }
        }
        return instance;
    }

    public void charge(double amount) {
        System.out.println("Razorpay charging ₹" + amount);
    }
}

class StripeGateway implements PaymentGateway {

    private static volatile StripeGateway instance;

    private StripeGateway() {
        System.out.println("Stripe Gateway Initialized");
    }

    public static StripeGateway getInstance() {
        if (instance == null) {
            synchronized (StripeGateway.class) {
                if (instance == null) {
                    instance = new StripeGateway();
                }
            }
        }
        return instance;
    }

    public void charge(double amount) {
        System.out.println("Stripe charging $" + amount);
    }
}

class PaypalGateway implements PaymentGateway {

    private static volatile PaypalGateway instance;

    private PaypalGateway() {
        System.out.println("PayPal Gateway Initialized");
    }

    public static PaypalGateway getInstance() {
        if (instance == null) {
            synchronized (PaypalGateway.class) {
                if (instance == null) {
                    instance = new PaypalGateway();
                }
            }
        }
        return instance;
    }

    public void charge(double amount) {
        System.out.println("PayPal charging €" + amount);
    }
}

// ===================== ABSTRACT FACTORY =====================
interface CountryPaymentFactory {
    PaymentMethod createPaymentMethod(String method);
    PaymentGateway getGateway();
}

// ===================== CONCRETE COUNTRY FACTORIES =====================
class IndiaPaymentFactory implements CountryPaymentFactory {

    public PaymentMethod createPaymentMethod(String method) {
        switch (method.toUpperCase()) {
            case "UPI": return new UPIPayment();
            case "CARD": return new CardPayment();
            case "WALLET": return new WalletPayment();
            default: throw new IllegalArgumentException("Invalid method");
        }
    }

    public PaymentGateway getGateway() {
        return RazorpayGateway.getInstance();
    }
}

class USPaymentFactory implements CountryPaymentFactory {

    public PaymentMethod createPaymentMethod(String method) {
        switch (method.toUpperCase()) {
            case "CARD": return new CardPayment();
            case "WALLET": return new WalletPayment();
            default: throw new IllegalArgumentException("Invalid method");
        }
    }

    public PaymentGateway getGateway() {
        return StripeGateway.getInstance();
    }
}

class EuropePaymentFactory implements CountryPaymentFactory {

    public PaymentMethod createPaymentMethod(String method) {
        switch (method.toUpperCase()) {
            case "CARD": return new CardPayment();
            case "WALLET": return new WalletPayment();
            default: throw new IllegalArgumentException("Invalid method");
        }
    }

    public PaymentGateway getGateway() {
        return PaypalGateway.getInstance();
    }
}

// ===================== FACTORY PROVIDER =====================
class CountryFactoryProvider {

    public static CountryPaymentFactory getFactory(String country) {

        switch (country.toUpperCase()) {
            case "INDIA": return new IndiaPaymentFactory();
            case "US": return new USPaymentFactory();
            case "EU": return new EuropePaymentFactory();
            default: throw new IllegalArgumentException("Invalid country");
        }
    }
}

// ===================== TEMPLATE METHOD =====================
abstract class BillingFlow {

    protected PaymentMethod method;
    protected PaymentGateway gateway;

    BillingFlow(PaymentMethod method, PaymentGateway gateway) {
        this.method = method;
        this.gateway = gateway;
    }

    // Template method
    public final void execute(double amount) {
        validate();
        processPayment(amount);
        postPayment();
    }

    protected void validate() {
        System.out.println("Validating billing details");
    }

    protected abstract void processPayment(double amount);

    protected void postPayment() {
        System.out.println("Payment successful\n");
    }
}

class OneTimeBilling extends BillingFlow {

    OneTimeBilling(PaymentMethod method, PaymentGateway gateway) {
        super(method, gateway);
    }

    protected void processPayment(double amount) {
        System.out.println("One-time billing");
        method.pay(amount, gateway);
    }
}

class RecurringBilling extends BillingFlow {

    RecurringBilling(PaymentMethod method, PaymentGateway gateway) {
        super(method, gateway);
    }

    protected void processPayment(double amount) {
        System.out.println("Recurring subscription billing");
        method.pay(amount, gateway);
    }
}

class TrialBilling extends BillingFlow {

    TrialBilling(PaymentMethod method, PaymentGateway gateway) {
        super(method, gateway);
    }

    protected void processPayment(double amount) {
        System.out.println("Trial ended → charging user");
        method.pay(amount, gateway);
    }
}

// ===================== DECORATOR =====================
interface AmountCalculator {
    double calculate(double amount);
}

class BaseAmountCalculator implements AmountCalculator {
    public double calculate(double amount) {
        return amount;
    }
}

abstract class AmountDecorator implements AmountCalculator {
    protected AmountCalculator calculator;

    AmountDecorator(AmountCalculator calculator) {
        this.calculator = calculator;
    }
}

class CouponDecorator extends AmountDecorator {

    private double discount;

    CouponDecorator(AmountCalculator calculator, double discount) {
        super(calculator);
        this.discount = discount;
    }

    public double calculate(double amount) {
        double result = calculator.calculate(amount) - discount;
        System.out.println("Coupon applied: -₹" + discount);
        return result;
    }
}

class GSTDecorator extends AmountDecorator {

    GSTDecorator(AmountCalculator calculator) {
        super(calculator);
    }

    public double calculate(double amount) {
        double result = calculator.calculate(amount) * 1.18;
        System.out.println("GST applied: +18%");
        return result;
    }
}
