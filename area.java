import java.util.*;

abstract class Shape {
    abstract double calculateArea();
}

class Circle extends Shape {
    private int radius;
    double pi = 3.142;

    Circle(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return pi * radius * radius;
    }
}

class Rectangle extends Shape {
    private int length;
    private int breadth;

    Rectangle(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public int getLength() {
        return length;
    }

    public int getBreadth() {
        return breadth;
    }

    @Override
    double calculateArea() {
        return length * breadth;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape nCircle = new Circle(5);
        Shape nRectangle = new Rectangle(5, 6);

        List<Shape> shapes = new ArrayList<>();

        shapes.add(nCircle);
        shapes.add(nRectangle);

        for (Shape s : shapes) {
            System.out.println("Area: " + s.calculateArea());
        }
    }
}
