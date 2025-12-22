# Observer Design Pattern – Order Management System

## Problem Statement
Design an order system where multiple services react to order status changes
without tightly coupling the services.

## Design Pattern Used
Observer Design Pattern (Behavioral)


#UML Design:

┌──────────────────────┐
│   OrderSubject       │
│──────────────────────│
│ +registerObserver()  │
│ +removeObserver()    │
│ +notifyObserver()    │
└─────────▲────────────┘
          │
          │ implements
┌──────────────────────────┐
│         Order            │
│──────────────────────────│
│ - orderId : String       │
│ - status  : OrderStatus  │
│ - observers : List<>     │
│──────────────────────────│
│ +setOrderStatus()        │
│ +registerObserver()      │
│ +removeObserver()        │
│ +notifyObserver()        │
└─────────┬───────────────┘
          │ notifies
          │
┌─────────▼─────────────────────────────┐
│            OrderObserver               │
│──────────────────────────────────────│
│ +update(orderId, status)              │
└─────────▲─────────▲─────────▲─────────┘
          │         │         │
          │         │         │
┌─────────┴───┐ ┌───┴────────┐ ┌────────┴────────┐ ┌────────┴────────┐
│ CustomerApp │ │ EmailService│ │ WarehouseService │ │ PaymentService  │
└─────────────┘ └─────────────┘ └─────────────────┘ └─────────────────┘



#Code Implementation

import java.util.ArrayList;
import java.util.List;

/*
 * Enum representing all possible states of an Order
 */
enum OrderStatus {
    PLACED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

/*
 * Observer interface
 * Any service that wants to get order updates must implement this
 */
interface OrderObserver {
    void update(String orderId, OrderStatus status);
}

/*
 * Subject interface
 * The Order (subject) manages and notifies observers
 */
interface OrderSubject {
    void registerObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObserver();
}

/*
 * Order class acts as the Subject
 * It notifies all registered observers whenever its state changes
 */
class Order implements OrderSubject {

    private List<OrderObserver> observers = new ArrayList<>();
    private String orderId;
    private OrderStatus status;

    // Constructor to initialize Order ID
    public Order(String orderId) {
        this.orderId = orderId;
    }

    // Register an observer
    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    // Remove an observer
    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers about state change
    @Override
    public void notifyObserver() {
        for (OrderObserver observer : observers) {
            observer.update(orderId, status);
        }
    }

    // Change order status and notify observers
    public void setOrderStatus(OrderStatus newStatus) {
        this.status = newStatus;
        System.out.println("\nOrder " + orderId + " status changed to " + status);
        notifyObserver();
    }
}

/*
 * Customer App Observer
 * Receives updates for ALL order states
 */
class CustomerApp implements OrderObserver {
    @Override
    public void update(String orderId, OrderStatus status) {
        System.out.println("Customer App: Order " + orderId + " -> " + status);
    }
}

/*
 * Email Service Observer
 * Sends email notifications for ALL order states
 */
class EmailService implements OrderObserver {
    @Override
    public void update(String orderId, OrderStatus status) {
        System.out.println("Email Service: Order " + orderId + " -> " + status);
    }
}

/*
 * Warehouse Service Observer
 * Reacts only when warehouse action is required
 * (Confirmed, Shipped, Cancelled)
 */
class WarehouseService implements OrderObserver {
    @Override
    public void update(String orderId, OrderStatus status) {
        if (status == OrderStatus.CONFIRMED ||
            status == OrderStatus.SHIPPED ||
            status == OrderStatus.CANCELLED) {

            System.out.println("Warehouse Service: Processing Order " +
                    orderId + " -> " + status);
        }
    }
}

/*
 * Payment Service Observer
 * Reacts ONLY when order is cancelled (refund)
 */
class PaymentService implements OrderObserver {
    @Override
    public void update(String orderId, OrderStatus status) {
        if (status == OrderStatus.CANCELLED) {
            System.out.println("Payment Service: Initiating refund for Order " + orderId);
        }
        else{
            System.out.println("Payment Service: Payment is done successfully for Order " + orderId);
        }
    }
}

/*
 * Main class
 * Demonstrates Observer Pattern in action
 */
public class Main {
    public static void main(String[] args) {

        // Create Order (Subject)
        Order order = new Order("ORD-1001");

        // Create Observers
        OrderObserver customerApp = new CustomerApp();
        OrderObserver emailService = new EmailService();
        OrderObserver warehouseService = new WarehouseService();
        OrderObserver paymentService = new PaymentService();

        // Register observers
        order.registerObserver(customerApp);
        order.registerObserver(emailService);
        order.registerObserver(warehouseService);
        order.registerObserver(paymentService);

        // Change order states
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setOrderStatus(OrderStatus.CANCELLED);
    }
}
