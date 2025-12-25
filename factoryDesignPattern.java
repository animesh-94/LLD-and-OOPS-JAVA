Problem:
An application needs to create and render different types of buttons (Windows, Mac, Linux) depending on the operating system.
The client code should not depend on concrete button classes, and adding a new OS button in the future should require minimal changes.

Challenges:
Avoid tight coupling between client code and button implementations.
Centralize object creation logic.
Follow Open–Closed Principle (open for extension, closed for modification).

Solution:
Use the Factory Design Pattern to encapsulate object creation logic and return button objects through a common interface.

UML Design

                <<interface>>
          ButtonFunctionService
          ---------------------
          + render()
          + onClick()
                  ▲
        ┌─────────┼───────────┐
        │         │           │
┌────────────┐ ┌──────────┐ ┌────────────┐
│WindowButton│ │MacButton │ │LinuxButton │
├────────────┤ ├──────────┤ ├────────────┤
│+ render()  │ │+ render()│ │+ render()  │
│+ onClick() │ │+onClick()│ │+ onClick() │
└────────────┘ └──────────┘ └────────────┘

               ┌──────────────────────────┐
               │   ButtonFactoryMethod    │
               ├──────────────────────────┤
               │ + getButton(String)      │
               └──────────────────────────┘

               ┌────────────┐
               │    Main    │
               ├────────────┤
               │uses Factory│
               └────────────┘


// Common interface defining button behavior
// This ensures all button types follow the same contract
interface ButtonFunctionService {
    void render();
    void onClick();
}

// Concrete implementation for Windows button
class WindowsButton implements ButtonFunctionService {

    @Override
    public void onClick() {
        // Handles Windows-specific click behavior
        System.out.println("Clicked the Windows button....Initializing the rendering.....");
    }

    @Override
    public void render() {
        // Handles Windows-specific UI rendering
        System.out.println("Rendering the Windows interface");
    }
}

// Concrete implementation for Mac button
class MacButton implements ButtonFunctionService {

    @Override
    public void onClick() {
        // Handles Mac-specific click behavior
        System.out.println("Clicked the Mac button....Initializing the rendering.....");
    }

    @Override
    public void render() {
        // Handles Mac-specific UI rendering
        System.out.println("Rendering the Mac interface");
    }
}

// Concrete implementation for Linux button
class LinuxButton implements ButtonFunctionService {

    @Override
    public void onClick() {
        // Handles Linux-specific click behavior
        System.out.println("Clicked the Linux button....Initializing the rendering.....");
    }

    @Override
    public void render() {
        // Handles Linux-specific UI rendering
        System.out.println("Rendering the Linux interface");
    }
}

// Factory class responsible for creating button objects
// Encapsulates object creation logic
class ButtonFactoryMethod {

    // Factory method that returns the appropriate button
    public static ButtonFunctionService getButton(String button) {

        if (button.equals("Windows")) {
            return new WindowsButton();
        } 
        else if (button.equals("Mac")) {
            return new MacButton();
        } 
        else if (button.equals("Linux")) {
            return new LinuxButton();
        } 
        else {
            // Fail fast for invalid input
            throw new IllegalArgumentException("Invalid button input");
        }
    }
}

// Client code
// Depends only on the interface, not concrete implementations
public class Main {
    public static void main(String[] args) {

        ButtonFunctionService button1 = ButtonFactoryMethod.getButton("Windows");
        button1.onClick();
        button1.render();

        ButtonFunctionService button2 = ButtonFactoryMethod.getButton("Mac");
        button2.onClick();
        button2.render();

        ButtonFunctionService button3 = ButtonFactoryMethod.getButton("Linux");
        button3.onClick();
        button3.render();
    }
}

