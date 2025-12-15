//example of Factory Design Pattern

interface deliveryVehicle{
    void delivery();
}

class Trucks implements deliveryVehicle{
    @Override
    public void delivery(){
        System.out.println("Delivery is done via Truck");
    }
}

class Ships implements deliveryVehicle{
    @Override
    public void delivery(){
        System.out.println("Delivery is done via Ships");
    }
}

class Aeroplane implements deliveryVehicle{
    @Override
    public void delivery(){
        System.out.println("Delivery is done via Aeroplane");
    }
}

class FactoryMethod{
    public static deliveryVehicle getVehicle(String vehicleType){
        if(vehicleType.equals("Trucks")){
            return new Trucks();
        }
        else if(vehicleType.equals("Ships")){
            return new Ships();
        }
        else if(vehicleType.equals("Aeroplane")){
            return new Aeroplane();
        }
        else{
            throw new IllegalArgumentException("Invalid delivery vehicle type");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        deliveryVehicle vehicle1 = FactoryMethod.getVehicle("Trucks");
        vehicle1.delivery();
        
        deliveryVehicle vehicle2 = FactoryMethod.getVehicle("Ships");
        vehicle2.delivery();
        
        deliveryVehicle vehicle3 = FactoryMethod.getVehicle("Aeroplane");
        vehicle3.delivery();
    }
}
