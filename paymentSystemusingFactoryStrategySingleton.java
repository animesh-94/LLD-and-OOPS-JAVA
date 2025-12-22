This project demonstrates a Payment Processing System using 
Abstract Factory, Factory Method, Strategy, and Singleton design patterns 
to achieve loose coupling, scalability, and clean separation of concerns.

//UML
                +----------------------+
                |  PaymentSystemDemo   |
                |----------------------|
                | + main(String[])     |
                +----------+-----------+
                           |
                           v
                +----------------------+
                | PaymentFactoryProvider|
                |----------------------|
                | + getFactory(type)   |
                +----------+-----------+
                           |
                           v
                +----------------------+
                | <<interface>>        |
                |   PaymentFactory     |
                |----------------------|
                | + createPayment()    |
                +----------+-----------+
                   ^        ^        ^
                   |        |        |
   +----------------+  +---------------+  +----------------------+
   | UPIPaymentFactory| |CardPaymentFactory| |NetBankingPaymentFactory|
   +------------------+ +------------------+ +----------------------+
                   |
                   v
          +----------------------+
          | <<interface>>        |
          |  PaymentStrategy     |
          |----------------------|
          | + pay(amount)        |
          +----------+-----------+
              ^        ^        ^
              |        |        |
      +-----------+ +-------------+ +----------------+
      |UPIPayment | |CardPayment  | |NetBankingPayment|
      +-----------+ +-------------+ +----------------+
              |
              v
        +----------------------+
        |  PaymentGateway     |
        |----------------------|
        | - instance           |
        | + getInstance()      |
        | + processPayment()   |
        +----------------------+


//Execution flow

User selects payment type
        ↓
PaymentFactoryProvider
        ↓
Concrete PaymentFactory
        ↓
PaymentStrategy object
        ↓
Singleton PaymentGateway


// ===================== CLIENT =====================
public class Main {

    public static void main(String[] args) {

        double amount = 2500;

        // Client only knows abstract factory
        PaymentFactory factory =
                PaymentFactoryProvider.getFactory("UPI");

        PaymentStrategy payment = factory.createPayment();
        payment.pay(amount);


        // Another payment using CARD
        PaymentFactory cardFactory =
                PaymentFactoryProvider.getFactory("CARD");

        PaymentStrategy cardPayment = cardFactory.createPayment();
        cardPayment.pay(4000);
    }
}

// ===================== STRATEGY =====================
interface PaymentStrategy {
    void pay(double amount);
}

// ===================== CONCRETE STRATEGIES =====================
class UPIPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Initiating UPI payment...");
        PaymentGateway.getInstance().processPayment(amount);
        System.out.println("UPI payment successful\n");
    }
}

class CardPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Validating card details and OTP...");
        PaymentGateway.getInstance().processPayment(amount);
        System.out.println("Card payment successful\n");
    }
}

class NetBankingPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Redirecting to bank portal...");
        PaymentGateway.getInstance().processPayment(amount);
        System.out.println("Net Banking payment successful\n");
    }
}

// ===================== SINGLETON =====================
class PaymentGateway {

    private static PaymentGateway instance;

    private PaymentGateway() {
        System.out.println("Payment Gateway Initialized");
    }

    public static PaymentGateway getInstance() {
        if (instance == null) {
            synchronized(PaymentGateway.class){
                if(instance == null){
                    instance = new PaymentGateway();
                }
            }
        }
        return instance;
    }

    public void processPayment(double amount) {
        System.out.println("Processing payment of ₹" + amount);
    }
}

// ===================== ABSTRACT FACTORY =====================
interface PaymentFactory {
    PaymentStrategy createPayment();
}

// ===================== CONCRETE FACTORIES (FACTORY METHOD) =====================
class UPIPaymentFactory implements PaymentFactory {

    @Override
    public PaymentStrategy createPayment() {
        return new UPIPayment();
    }
}

class CardPaymentFactory implements PaymentFactory {

    @Override
    public PaymentStrategy createPayment() {
        return new CardPayment();
    }
}

class NetBankingPaymentFactory implements PaymentFactory {

    @Override
    public PaymentStrategy createPayment() {
        return new NetBankingPayment();
    }
}

// ===================== FACTORY PROVIDER =====================
class PaymentFactoryProvider {

    public static PaymentFactory getFactory(String type) {

        switch (type.toUpperCase()) {
            case "UPI":
                return new UPIPaymentFactory();

            case "CARD":
                return new CardPaymentFactory();

            case "NETBANKING":
                return new NetBankingPaymentFactory();

            default:
                throw new IllegalArgumentException("Invalid payment type");
        }
    }
}
