// Car class represents the product that we want to build
class Car {
    
    // Mandatory / optional attributes of Car
    private String engine;
    private int wheels;
    private String color;
    private int seat;
    private boolean sunroof;
    private boolean infoScreen;

    // Private constructor that takes Builder object
    // This ensures Car objects are created only using CarBuilder
    Car(CarBuilder builder) {
        this.engine = builder.engine;
        this.wheels = builder.wheels;
        this.color = builder.color;
        this.seat = builder.seat;
        this.sunroof = builder.sunroof;
        this.infoScreen = builder.infoScreen;
    }

    // Getter methods
    public String GetEngine() {
        return engine;
    }

    public int GetWheels() {
        return wheels;
    }

    public String GetColor() {
        return color;
    }

    public int GetSeat() {
        return seat;
    }

    public boolean GetSunroof() {
        return sunroof;
    }

    public boolean GetInfoScreen() {
        return infoScreen;
    }

    // Overriding toString() to print Car object details
    @Override
    public String toString() {
        return "Car [engine=" + engine + ", wheels=" + wheels + ", seats=" + seat
                + ", color=" + color + ", sunroof=" + sunroof
                + ", infoScreen=" + infoScreen + "]";
    }

    // Static inner Builder class
    // Responsible for step-by-step construction of Car object
    public static class CarBuilder {

        // Same fields as Car
        private String engine;
        private int wheels = 4;        // Default value
        private String color = "RED";  // Default value
        private int seat = 5;          // Default value
        private boolean sunroof = false;
        private boolean infoScreen = false;

        // Setter methods return the same Builder object
        // This enables method chaining
        public CarBuilder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public CarBuilder setWheel(int wheels) {
            this.wheels = wheels;
            return this;
        }

        public CarBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public CarBuilder setSeat(int seat) {
            this.seat = seat;
            return this;
        }

        public CarBuilder setSunroof(boolean sunroof) {
            this.sunroof = sunroof;
            return this;
        }

        public CarBuilder setInfoScreen(boolean infoScreen) {
            this.infoScreen = infoScreen;
            return this;
        }

        // Final method to create Car object
        public Car build() {
            return new Car(this);
        }
    }
}

// Main class to test Builder Pattern
public class Main {
    public static void main(String[] args) {

        // Create a Builder object
        Car.CarBuilder builder = new Car.CarBuilder();

        // Build first car with selected features
        Car car1 = builder.setEngine("V8")
                           .setColor("Red")
                           .setSeat(5)
                           .setSunroof(true)
                           .build();
        System.out.println(car1);

        // Build second car with different configuration
        Car car2 = builder.setEngine("V6")
                           .setColor("Blue")
                           .setSeat(4)
                           .build();
        System.out.println(car2);
    }
}
