//Implemented the code using SRP,OCP and LSP principles and it also follows ISP principle

import java.util.ArrayList;
import java.util.List;

abstract class Bird{
    abstract void eats();
    abstract void sleeps();
}

interface birdThatFlies{
    void fly();
}

class Sparow extends Bird implements birdThatFlies{
    @Override
    void eats(){
        System.out.println("Sparow eats earthworms");
    }
    
    @Override
    void sleeps(){
        System.out.println("Sparow sleeps in the night");
    }
    
    @Override
    public void fly(){
        System.out.println("Sparrow flies in the sky");
    }
}

class Penguins extends Bird{
    @Override
    void eats(){
        System.out.println("Penguin eats fish");
    }
    
    @Override
    void sleeps(){
        System.out.println("Penguin sleeps in the night");
    }
}

public class Main {
    public static void main(String[] args) {
        Bird sparow = new Sparow();
        Bird penguin = new Penguins();

        sparow.eats();
        penguin.eats();

        birdThatFlies flyingBird = new Sparow();
        flyingBird.fly();
    }
}
