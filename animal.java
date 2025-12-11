class Animal {
    String name;
    String species;
    int age;
    
    public Animal(String name, String species, int age){
        this.name = name;
        this.species = species;
        this.age = age;
    }
    
    void makeSound(){
        System.out.println("Animal makes the sound.....");
    }
}

class Dog extends Animal{
    String breed;
    
    public Dog(String name, String breed, int age){
        super(name, breed, age);
        this.breed = breed;
    }
    
    @Override
    void makeSound(){
        System.out.println("barks.....");
    }
}

class Cat extends Animal{
    boolean hasClaws;
    
    public Cat(String name, int age, boolean hasClaws){
        super(name, "Cat", age);
        this.hasClaws = hasClaws;
    }
    
    @Override
    void makeSound(){
        System.out.println("meows.....");
    }
}

public class Main
{
	public static void main(String[] args) {
	    Animal a1 = new Animal("Generic animal", "breed undefined", 0);
	    Dog d1 = new Dog("Fluffy", "Labrador", 3);
	    Cat c1 = new Cat("Pussy", 2, true);
	    
	    Animal[] animals = {a1, d1, c1};
	    
	    for(Animal a: animals){
	        System.out.print(a.species + " says ");
	        a.makeSound();
	    }
	}
}
