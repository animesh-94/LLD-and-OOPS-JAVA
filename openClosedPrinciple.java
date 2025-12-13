//example of Open/Closed Principle
//Open/Closed Principle states that a class should be open for extension but closed for modification
//Here an abstract class is defiend which will help us to add more classes that will extend this abstract class and abstract method

abstract class Shape{
    abstract void calculateArea();
}

class Circle extends Shape{
    private double radius;
    
    Circle(double radius){
        this.radius = radius;
    }
    
    public double getRadius(){
        return radius;
    }
    
    void setRadius(double radius){
        this.radius = radius;
    }
    
    @Override
    void calculateArea(){
        double result = 3.142*radius*radius;
        System.out.println("Area of Circle: " + result);
    }
}

class Rectangle extends Shape{
    private double length;
    private double breadth;
    
    Rectangle(double length, double breadth){
        this.length = length;
        this.breadth = breadth;
    }
    
    public double getLength(){
        return length;
    }
    
    void setLength(double length){
        this.length = length;
    }
    
    public double getBreadth(){
        return breadth;
    }
    
    void setBreadth(double breadth){
        this.breadth = breadth;
    }
    
    @Override
    void calculateArea(){
        double result = length*breadth;
        System.out.println("Area of Rectangle: " + result);
    }
}

// I can add more classes without violating the Open/Closed Principle

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 5);
        
        circle.calculateArea();
        rectangle.calculateArea();
    }
}
