//example of Single Responsibility Principle 
//According to this principle one class should be responsible for only one task or purpose not more than one


//bakers makes the bread it showcases only one responsibility
class bakeBakers{
    void makingBreads(){
        System.out.println("Making the breads.....");
    }
}

class inventoryManager{
    void manageInventory(){
        System.out.println("Managing the inventory.....");
    }
}

class deliveryPartner{
    void manageDelivery(){
        System.out.println("Managing the delivery.....");
    }
}

public class Main {
    public static void main(String[] args) {
        bakeBakers Bakers = new bakeBakers();
        inventoryManager Manager = new inventoryManager();
        deliveryPartner DeliveryAgent = new deliveryPartner();
        
        Bakers.makingBreads();
        Manager.manageInventory();
        DeliveryAgent.manageDelivery();
    }
}
