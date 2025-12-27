Problem Statement

Design an IDE where:
Plugins can be added dynamically
Each plugin performs a specific action
IDE reacts to plugin events

Patterns Used
Factory → Plugin creation
Strategy → Plugin behavior
Observer → IDE event system

                    <<interface>>
                  PluginStrategy
                  ----------------
                  + plugin(String)
                          ▲
        ┌─────────────────┼──────────────────┐
        │                 │                  │
 ┌─────────────┐   ┌─────────────┐   ┌──────────────────┐
 │  JavaPlugin │   │  CPPPlugin  │   │  PythonPlugin    │
 ├─────────────┤   ├─────────────┤   ├──────────────────┤
 │ + plugin()  │   │ + plugin()  │   │ + plugin()       │
 └─────────────┘   └─────────────┘   └──────────────────┘
                               
                    ┌──────────────────┐
                    │ GitVersionControl│
                    ├──────────────────┤
                    │ + plugin()       │
                    └──────────────────┘


                  ┌─────────────────────┐
                  │    PluginFactory    │
                  ├─────────────────────┤
                  │ + getPluginMethod() │
                  └─────────────────────┘
                             |
                             | creates
                             ▼
                     PluginStrategy


==============================================================


                    <<interface>>
                       Observer
                  --------------------
                  + update(plugin, status)
                          ▲
            ┌─────────────┼──────────────┐
            │             │              │
      ┌──────────┐  ┌──────────┐  ┌──────────┐
      │ Terminal │  │    UI    │  │  Email   │
      ├──────────┤  ├──────────┤  ├──────────┤
      │ + update │  │ + update │  │ + update │
      └──────────┘  └──────────┘  └──────────┘


                    <<interface>>
                       Subject
                  --------------------
                  + addObserver()
                  + removeObserver()
                  + notifyObserver()
                          ▲
                          │
                  ┌─────────────────────┐
                  │     IDEObserver     │
                  ├─────────────────────┤
                  │ - observers: List   │
                  │ - plugin: String    │
                  │ - status: String    │
                  ├─────────────────────┤
                  │ + addObserver()     │
                  │ + removeObserver()  │
                  │ + notifyObserver()  │
                  │ + updateObserver()  │
                  └─────────────────────┘


import java.util.*;

// ================= STRATEGY INTERFACE =================
// Defines a common contract for all plugins
interface PluginStrategy {
    void plugin(String plug);
}

// ================= CONCRETE STRATEGIES =================
class JavaPlugin implements PluginStrategy {
    @Override
    public void plugin(String plug) {
        System.out.println("Java plugin is initiated...");
    }
}

class CPPPlugin implements PluginStrategy {
    @Override
    public void plugin(String plug) {
        System.out.println("C++ plugin is initiated...");
    }
}

class PythonPlugin implements PluginStrategy {
    @Override
    public void plugin(String plug) {
        System.out.println("Python plugin is initiated...");
    }
}

class GitVersionControl implements PluginStrategy {
    @Override
    public void plugin(String plug) {
        System.out.println("Git plugin is initiated...");
    }
}

// ================= FACTORY METHOD =================
// Centralizes plugin object creation
class PluginFactory {

    public PluginStrategy getPluginMethod(String plug) {

        if (plug.equalsIgnoreCase("JAVA")) {
            return new JavaPlugin();
        } 
        else if (plug.equalsIgnoreCase("CPP")) {
            return new CPPPlugin();
        } 
        else if (plug.equalsIgnoreCase("PYTHON")) {
            return new PythonPlugin();
        } 
        else if (plug.equalsIgnoreCase("GIT")) {
            return new GitVersionControl();
        } 
        else {
            throw new IllegalArgumentException("Invalid plugin. Please install the plugin.");
        }
    }
}

// ================= OBSERVER PATTERN =================
interface Observer {
    void update(String plugin, String status);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}

// Concrete Observers
class Terminal implements Observer {
    @Override
    public void update(String plugin, String status) {
        System.out.println("[Terminal] Plugin -> " + plugin + ", Status -> " + status);
    }
}

class UI implements Observer {
    @Override
    public void update(String plugin, String status) {
        System.out.println("[UI] Plugin -> " + plugin + ", Status -> " + status);
    }
}

class Email implements Observer {
    @Override
    public void update(String plugin, String status) {
        System.out.println("[Email] Plugin -> " + plugin + ", Status -> " + status);
    }
}

// ================= SUBJECT =================
class IDEObserver implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private String plugin;
    private String status;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(plugin, status);
        }
    }

    // Updates plugin state and notifies observers
    public void updateObserver(String plugin, String status) {
        this.plugin = plugin;
        this.status = status;
        System.out.println("\n[IDE] Plugin Status Updated");
        notifyObserver();
    }
}

// ================= MAIN CLIENT =================
public class Main {
    public static void main(String[] args) {

        // Factory + Strategy usage
        PluginFactory factory = new PluginFactory();
        PluginStrategy plugin = factory.getPluginMethod("JAVA");
        plugin.plugin("JAVA");

        // Observer setup
        IDEObserver ide = new IDEObserver();
        ide.addObserver(new Terminal());
        ide.addObserver(new UI());
        ide.addObserver(new Email());

        // Notify observers about plugin state
        ide.updateObserver("JAVA", "Initialized Successfully");
    }
}
