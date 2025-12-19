// House class represents the final immutable object
class House {

    // Mandatory attributes
    private final int floors;
    private final int area;

    // Optional attributes
    private final boolean garage;
    private final boolean swimmingPool;
    private final boolean garden;
    private final boolean securitySystem;

    // Private constructor that takes Builder object
    // This ensures House objects are created only via the Builder
    House(HouseBuilder builder) {
        this.floors = builder.floors;
        this.area = builder.area;
        this.garage = builder.garage;
        this.swimmingPool = builder.swimmingPool;
        this.garden = builder.garden;
        this.securitySystem = builder.securitySystem;
    }

    // Getter methods (no setters to keep object immutable)
    public int getFloor() {
        return floors;
    }

    public int getArea() {
        return area;
    }

    public boolean getGarage() {
        return garage;
    }

    public boolean getSwimmingPool() {
        return swimmingPool;
    }

    public boolean getGarden() {
        return garden;
    }

    public boolean getSecuritySystem() {
        return securitySystem;
    }

    // Overriding toString() for easy object representation
    @Override
    public String toString() {
        return "House{" +
                "floors=" + floors +
                ", area=" + area +
                ", garage=" + garage +
                ", swimmingPool=" + swimmingPool +
                ", garden=" + garden +
                ", securitySystem=" + securitySystem +
                '}';
    }

    // Static nested Builder class
    public static class HouseBuilder {

        // Required parameters
        private final int floors;
        private final int area;

        // Optional parameters with default values
        private boolean garage = false;
        private boolean swimmingPool = false;
        private boolean garden = false;
        private boolean securitySystem = false;

        // Builder constructor enforces mandatory fields
        public HouseBuilder(int floors, int area) {
            if (floors <= 0) {
                throw new IllegalArgumentException("Invalid floor");
            }
            if (area <= 0) {
                throw new IllegalArgumentException("Invalid area");
            }
            this.floors = floors;
            this.area = area;
        }

        // Setter methods for optional parameters
        // Each method returns Builder to enable method chaining
        public HouseBuilder setGarage(boolean garage) {
            this.garage = garage;
            return this;
        }

        public HouseBuilder setSwimmingPool(boolean swimmingPool) {
            this.swimmingPool = swimmingPool;
            return this;
        }

        public HouseBuilder setGarden(boolean garden) {
            this.garden = garden;
            return this;
        }

        public HouseBuilder setSecuritySystem(boolean securitySystem) {
            this.securitySystem = securitySystem;
            return this;
        }

        // Final build method to create immutable House object
        public House build() {
            return new House(this);
        }
    }
}

// Main class to demonstrate Builder Pattern usage
public class Main {
    public static void main(String[] args) {

        // Creating House object using Builder pattern
        House house = new House.HouseBuilder(2, 2000)
                .setGarage(true)
                .setSwimmingPool(true)
                .setGarden(false)
                .setSecuritySystem(true)
                .build();

        // Printing house details
        System.out.println(house);
    }
}
