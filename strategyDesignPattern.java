// Strategy Interface
interface DiscountStrategy {
    void discountProcess(int amount);
}

// Concrete Strategy 1
class RegularCustomer implements DiscountStrategy {
    @Override
    public void discountProcess(int amount) {
        int discount = (amount * 5) / 100;
        System.out.println("Discount offered to Regular Customer: " + discount);
    }
}

// Concrete Strategy 2
class PremiumCustomer implements DiscountStrategy {
    @Override
    public void discountProcess(int amount) {
        int discount = (amount * 20) / 100;
        System.out.println("Discount offered to Premium Customer: " + discount);
    }
}

// Concrete Strategy 3
class FestiveSeason implements DiscountStrategy {
    @Override
    public void discountProcess(int amount) {
        int discount = 500; // flat festive discount
        System.out.println("Festive Season Discount: " + discount);
    }
}

// Context Class
class DiscountProcessor {

    private DiscountStrategy discountStrategy;

    public DiscountProcessor(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void processDiscount(int amount) {
        discountStrategy.discountProcess(amount);
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
}

// Client Code
public class Main {
    public static void main(String[] args) {

        int billAmount = 5000;

        DiscountProcessor processor =
                new DiscountProcessor(new RegularCustomer());
        processor.processDiscount(billAmount);

        processor.setDiscountStrategy(new PremiumCustomer());
        processor.processDiscount(billAmount);

        processor.setDiscountStrategy(new FestiveSeason());
        processor.processDiscount(billAmount);
    }
}
