// ===== ABSTRACT FACTORY DESIGN PATTERN =====

// -------- Product Interface --------
interface Vehicle {
    void start();
    void stop();
}

// -------- Honda Vehicles --------
class Civic implements Vehicle {
    public void start() {
        System.out.println("Starting Honda Civic");
    }

    public void stop() {
        System.out.println("Stopping Honda Civic");
    }
}

class City implements Vehicle {
    public void start() {
        System.out.println("Starting Honda City");
    }

    public void stop() {
        System.out.println("Stopping Honda City");
    }
}

// -------- BMW Vehicles --------
class BMWX1 implements Vehicle {
    public void start() {
        System.out.println("Starting BMW X1");
    }

    public void stop() {
        System.out.println("Stopping BMW X1");
    }
}

class BMWX5 implements Vehicle {
    public void start() {
        System.out.println("Starting BMW X5");
    }

    public void stop() {
        System.out.println("Stopping BMW X5");
    }
}

// -------- Toyota Vehicles --------
class Corolla implements Vehicle {
    public void start() {
        System.out.println("Starting Toyota Corolla");
    }

    public void stop() {
        System.out.println("Stopping Toyota Corolla");
    }
}

class Camry implements Vehicle {
    public void start() {
        System.out.println("Starting Toyota Camry");
    }

    public void stop() {
        System.out.println("Stopping Toyota Camry");
    }
}

// -------- Abstract Factory --------
interface VehicleFactory {
    Vehicle createSedan();
    Vehicle createSUV();
}

// -------- Concrete Factories --------
class HondaFactory implements VehicleFactory {
    public Vehicle createSedan() {
        return new City();
    }

    public Vehicle createSUV() {
        return new Civic();
    }
}

class BMWFactory implements VehicleFactory {
    public Vehicle createSedan() {
        return new BMWX1();
    }

    public Vehicle createSUV() {
        return new BMWX5();
    }
}

class ToyotaFactory implements VehicleFactory {
    public Vehicle createSedan() {
        return new Corolla();
    }

    public Vehicle createSUV() {
        return new Camry();
    }
}

// -------- Client --------
public class Main {
    public static void main(String[] args) {

        VehicleFactory factory;

        factory = new HondaFactory();
        Vehicle hondaSedan = factory.createSedan();
        hondaSedan.start();
        hondaSedan.stop();

        factory = new BMWFactory();
        Vehicle bmwSUV = factory.createSUV();
        bmwSUV.start();
        bmwSUV.stop();

        factory = new ToyotaFactory();
        Vehicle toyotaSedan = factory.createSedan();
        toyotaSedan.start();
        toyotaSedan.stop();
    }
}
