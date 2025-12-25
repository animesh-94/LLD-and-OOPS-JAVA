E-Commerce Checkout System (Builder + Strategy + Command)
Problem Statement
Design a checkout system where:
Orders have many optional fields
Multiple discount strategies exist
Checkout steps can be executed and rolled back

Patterns Used
Builder → Order creation
Strategy → Discount calculation
Command → Checkout steps with undo


+-----------------------------+
|            Order            |
+-----------------------------+
| - orderId: String           |
| - userId: String            |
| - items: List<String>       |
| - amount: int               |
| - shippingAddress: String   |
| - billingAddress: String    |
| - couponCode: String        |
| - giftWrap: boolean         |
| - paymentMethod: String     |
| - currency: String          |
+-----------------------------+
| + getOrderId(): String      |
| + getAmount(): int          |
| + getPaymentMethod():String|
+-----------------------------+
              ▲
              |
              |
+-----------------------------+
|        OrderBuilder         |
+-----------------------------+
| - orderId: String           |
| - userId: String            |
| - items: List<String>       |
| - amount: int               |
| - shippingAddress: String   |
| - billingAddress: String    |
| - couponCode: String        |
| - giftWrap: boolean         |
| - paymentMethod: String     |
| - currency: String          |
+-----------------------------+
| + setShippingAddress()      |
| + setBillingAddress()       |
| + setCouponCode()           |
| + setGiftWrap()             |
| + setPaymentMethod()        |
| + setCurrency()             |
| + build(): Order            |
+-----------------------------+


================== STRATEGY PATTERN ==================

+-------------------------------+
|      DiscountStrategy         |
+-------------------------------+
| + DiscountProcess(amount)    |
+-------------------------------+
              ▲
   -----------------------------------------
   |           |             |             |
+-----------+ +---------------+ +-----------+
|NoDiscount | |PercentDiscount| |FlatDiscount|
+-----------+ +---------------+ +-----------+

+-----------------------------+
|          Discount           |
+-----------------------------+
| - discountStrategy          |
+-----------------------------+
| + discount(amount)          |
+-----------------------------+


================== COMMAND PATTERN ==================

+-----------------------------+
|          Command            |
+-----------------------------+
| + execute()                 |
| + undo()                    |
+-----------------------------+
              ▲
      -------------------
      |                 |
+--------------------+  +-----------------------+
|ProcessPaymentCommand| |ApplyDiscountCommand   |
+--------------------+  +-----------------------+
| - order: Order     |  | - order: Order        |
|                    |  | - discount: Discount  |
+--------------------+  +-----------------------+
| + execute()        |  | + execute()           |
| + undo()           |  | + undo()              |
+--------------------+  +-----------------------+

              ▲
              |
+-----------------------------+
|     CheckoutProcessor       |
+-----------------------------+
| - history: Stack<Command>   |
+-----------------------------+
| + executeCommand(cmd)       |
| + rollback()                |
+-----------------------------+


================== CLIENT ==================

+-----------------------------+
|            Main             |
+-----------------------------+
| + main(args)                |
+-----------------------------+



import java.util.*;

// ======================= ORDER (BUILDER PATTERN) =======================
class Order {
    private final String orderId;
    private final String userId;
    private final List<String> items;
    private final int amount;

    private final String shippingAddress;
    private final String billingAddress;
    private final String couponCode;
    private final boolean giftWrap;
    private final String paymentMethod;
    private final String currency;

    private Order(OrderBuilder builder) {
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.items = builder.items;
        this.amount = builder.amount;
        this.shippingAddress = builder.shippingAddress;
        this.billingAddress = builder.billingAddress;
        this.couponCode = builder.couponCode;
        this.giftWrap = builder.giftWrap;
        this.paymentMethod = builder.paymentMethod;
        this.currency = builder.currency;
    }

    public String getOrderId() { return orderId; }
    public int getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }

    // ---------- BUILDER ----------
    static class OrderBuilder {
        private final String orderId;
        private final String userId;
        private final List<String> items;
        private final int amount;

        private String shippingAddress;
        private String billingAddress;
        private String couponCode;
        private boolean giftWrap;
        private String paymentMethod;
        private String currency;

        OrderBuilder(String orderId, String userId, List<String> items, int amount) {
            if (orderId == null || orderId.isEmpty()){
                throw new IllegalArgumentException("Invalid OrderId");
            }
            
            if (userId == null || userId.isEmpty()){
                throw new IllegalArgumentException("Invalid UserId");
            }
            
            if (items == null || items.isEmpty()){
                throw new IllegalArgumentException("No items added");
            }
            
            if (amount <= 0){
                throw new IllegalArgumentException("Invalid amount");
            }

            this.orderId = orderId;
            this.userId = userId;
            this.items = items;
            this.amount = amount;
        }

        public OrderBuilder setShippingAddress(String val) {
            this.shippingAddress = val;
            return this;
        }

        public OrderBuilder setBillingAddress(String val) {
            this.billingAddress = val;
            return this;
        }

        public OrderBuilder setCouponCode(String val) {
            this.couponCode = val;
            return this;
        }

        public OrderBuilder setGiftWrap(boolean val) {
            this.giftWrap = val;
            return this;
        }

        public OrderBuilder setPaymentMethod(String val) {
            this.paymentMethod = val;
            return this;
        }

        public OrderBuilder setCurrency(String val) {
            this.currency = val;
            return this;
        }

        public Order build() {
            if (shippingAddress == null || billingAddress == null)
                throw new IllegalArgumentException("Address missing");
            if (paymentMethod == null || currency == null)
                throw new IllegalArgumentException("Payment/Currency missing");

            return new Order(this);
        }
    }
}

// ======================= STRATEGY (UNCHANGED LOGIC) =======================
interface DiscountStrategy {
    void DiscountProcess(double Amount);
}

class NoDiscount implements DiscountStrategy {
    public void DiscountProcess(double Amount) {
        System.out.println("No discount added...your total is: " + Amount);
    }
}

class PercentDiscount implements DiscountStrategy {
    public void DiscountProcess(double Amount) {
        double res;
        if (Amount >= 500 && Amount < 1000) {
            double per = 5;
            res = (Amount * per) / 100;
            System.out.println("Discount of 5% is offered....your total is: " + (Amount - res));
        } 
        else if (Amount >= 1000 && Amount < 1500) {
            double per = 6.5;
            res = (Amount * per) / 100;
            System.out.println("Discount of 6.5% is offered....your total is: " + (Amount - res));
        } 
        else if (Amount >= 1500 && Amount < 2500) {
            double per = 7.5;
            res = (Amount * per) / 100;
            System.out.println("Discount of 7.5% is offered....your total is: " + (Amount - res));
        } 
        else {
            System.out.println("No Discount included...your total is: " + Amount);
        }
    }
}

class FlatDiscount implements DiscountStrategy {
    public void DiscountProcess(double Amount) {
        if (Amount - 100 > 0) {
            System.out.println("The discount is avail and new price is: " + (Amount - 100));
        } 
        else {
            System.out.println("Add item worth " + Math.abs(Amount - 100 + 1) + " to the cart");
        }
    }
}

class Discount {
    private DiscountStrategy discountStrategy;

    Discount(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void discount(double Amount) {
        discountStrategy.DiscountProcess(Amount);
    }
}

// ======================= COMMAND PATTERN =======================
interface Command {
    void execute();
    void undo();
}

// Receiver = Order
class ProcessPaymentCommand implements Command {
    private Order order;

    ProcessPaymentCommand(Order order) {
        this.order = order;
    }

    public void execute() {
        System.out.println("Payment processed for Order ID: " + order.getOrderId());
    }

    public void undo() {
        System.out.println(
            "Order cancelled for Order ID: " + order.getOrderId() +
            ". Refund initiated via " + order.getPaymentMethod()
        );
    }
}

class ApplyDiscountCommand implements Command {
    private Order order;
    private Discount discount;

    ApplyDiscountCommand(Order order, Discount discount) {
        this.order = order;
        this.discount = discount;
    }

    public void execute() {
        discount.discount(order.getAmount());
    }

    public void undo() {
        System.out.println("Discount reverted for Order ID: " + order.getOrderId());
    }
}

// Invoker
class CheckoutProcessor {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    public void rollback() {
        while (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}

// ======================= MAIN =======================
public class Main {
    public static void main(String[] args) {

        Order order = new Order.OrderBuilder(
                "ORD101",
                "USER42",
                List.of("Laptop", "Mouse"),
                1200
        )
        .setShippingAddress("Pune")
        .setBillingAddress("Pune")
        .setPaymentMethod("UPI")
        .setCurrency("INR")
        .build();

        Discount discount = new Discount(new PercentDiscount());

        CheckoutProcessor checkout = new CheckoutProcessor();

        checkout.executeCommand(new ApplyDiscountCommand(order, discount));
        checkout.executeCommand(new ProcessPaymentCommand(order));

        System.out.println("\n--- Rolling back checkout ---");
        checkout.rollback();
    }
}
