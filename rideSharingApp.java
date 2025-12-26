Problem Statement

Design a ride-sharing system where:
Fare calculation depends on ride type
Drivers and users receive ride status updates
A central pricing engine exists

Patterns Used
Strategy → Fare calculation (Economy, Premium, Pool)
Observer → Ride status updates
Singleton → Pricing Engine



                <<interface>>
               RideStrategy
               ----------------
               + calculateFare(distance) : double
                      ▲
        ---------------------------------------
        |                |                    |
     BikeRide          AutoRide            CarRide
     --------          --------            -------
     + calculateFare() + calculateFare()   + calculateFare()


                  <<Singleton>>
                PricingEngine
               ----------------
               - instance : PricingEngine
               ----------------
               + getInstance() : PricingEngine
               + getFare(strategy, distance) : double
                       |
                       | uses
                       v
                  RideStrategy


                <<interface>>
                   Observer
               ----------------
               + update(rideId, status)
                      ▲
              -------------------------
              |                       |
        DriverObserver           UserObserver
        --------------           ------------
        + update()               + update()


                <<interface>>
                 RideSubject
               ----------------
               + addObserver()
               + removeObserver()
               + notifyObservers()
                      ▲
                      |
                    Ride
               ----------------
               - rideId : String
               - status : String
               - observers : List<Observer>
               ----------------
               + updateStatus()


import java.util.*;

// ========================= STRATEGY PATTERN =========================
interface RideStrategy {
    double calculateFare(double distance);
}

class BikeRide implements RideStrategy {
    @Override
    public double calculateFare(double distance) {
        return distance * 10;
    }
}

class AutoRide implements RideStrategy {
    @Override
    public double calculateFare(double distance) {
        return distance * 15;
    }
}

class CarRide implements RideStrategy {
    @Override
    public double calculateFare(double distance) {
        return distance * 20;
    }
}

// ========================= SINGLETON PATTERN =========================
class PricingEngine {

    private static PricingEngine instance;

    private PricingEngine() {}

    public static synchronized PricingEngine getInstance() {
        if (instance == null) {
            instance = new PricingEngine();
        }
        return instance;
    }

    public double getFare(RideStrategy strategy, double distance) {
        return strategy.calculateFare(distance);
    }
}

// ========================= OBSERVER PATTERN =========================
interface Observer {
    void update(String rideId, String status);
}

interface RideSubject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

class DriverObserver implements Observer {
    @Override
    public void update(String rideId, String status) {
        System.out.println("Driver notified -> Ride: " + rideId + " | Status: " + status);
    }
}

class UserObserver implements Observer {
    @Override
    public void update(String rideId, String status) {
        System.out.println("User notified -> Ride: " + rideId + " | Status: " + status);
    }
}

// ========================= RIDE ENTITY =========================
class Ride implements RideSubject {

    private String rideId;
    private String status;
    private List<Observer> observers = new ArrayList<>();

    public Ride(String rideId) {
        this.rideId = rideId;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(rideId, status);
        }
    }

    public void updateStatus(String status) {
        this.status = status;
        System.out.println("\nRide Status Updated → " + status);
        notifyObservers();
    }
}

// ========================= CLIENT / MAIN =========================
public class RideSharingSystem {

    public static void main(String[] args) {

        // Observers
        Observer driver = new DriverObserver();
        Observer user = new UserObserver();

        // Ride creation
        Ride ride = new Ride("RIDE-101");
        ride.addObserver(driver);
        ride.addObserver(user);

        // Strategy selection
        RideStrategy strategy = new CarRide(); // BikeRide / AutoRide / CarRide

        // Singleton Pricing Engine
        PricingEngine pricingEngine = PricingEngine.getInstance();

        double distance = 12.5;
        double fare = pricingEngine.getFare(strategy, distance);

        System.out.println("Ride booked successfully!");
        System.out.println("Total Fare: ₹" + fare);

        // Ride lifecycle
        ride.updateStatus("DRIVER_ASSIGNED");
        ride.updateStatus("RIDE_STARTED");
        ride.updateStatus("RIDE_COMPLETED");
    }
}
