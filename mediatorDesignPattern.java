import java.util.*;

interface AuctionMediator{
    void registerBidder(Bidder bidder);
    void placeBid(Bidder bidder, double amount);
}

class Bidder{
    private String name;
    private AuctionMediator mediator;
    
    Bidder(String name, AuctionMediator mediator){
        this.name = name;
        this.mediator = mediator;
    }
    
    public String getName(){
        return name;
    }
    
    public void placeBid(double amount){
        mediator.placeBid(this, amount);
    }
    
    public void receiveBid(Bidder bidder, double amount){
        System.out.println(name + " is notified: " + bidder.getName() +
                       " placed a bid of " + amount);
    }
}

class AuctionHouse implements AuctionMediator{
    private List<Bidder> bidders = new ArrayList<>();
    @Override
    public void registerBidder(Bidder bidder){
        bidders.add(bidder);
    }
    
    @Override
    public void placeBid(Bidder bidder, double amount){
        System.out.println(bidder.getName() + " placed a bid of " + amount);
        for(Bidder b: bidders){
            if(b != bidder){
                b.receiveBid(bidder, amount);
            }
        }
    }
}

public class Main {
 public static void main(String[] args) {
    AuctionMediator auctionHouse = new AuctionHouse();
    Bidder bidder1 = new Bidder("Alice", auctionHouse);
    Bidder bidder2 = new Bidder("Bob", auctionHouse);
    Bidder bidder3 = new Bidder("Charlie", auctionHouse);
    auctionHouse.registerBidder(bidder1);
    auctionHouse.registerBidder(bidder2);
    auctionHouse.registerBidder(bidder3);
    bidder1.placeBid(100);
    bidder2.placeBid(150);
    bidder3.placeBid(200);
  }
}
