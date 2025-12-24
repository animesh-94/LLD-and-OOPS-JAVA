Problem Statement:

In many applications, logging is a cross-cutting concern used across multiple components.
Creating multiple logger instances can lead to:
Inconsistent logs
Resource wastage
Synchronization issues in multi-threaded environments

✅ Requirement
Ensure only one Logger instance exists throughout the application
Provide global access
Make it thread-safe
Avoid unnecessary synchronization overhead

Solution:

Singleton Design Pattern
Ensures a class has only one instance and provides a global point of access to it.

UML:

+--------------------+
|      Logger        |
+--------------------+
| - instance: Logger |
|   (static, volatile)|
+--------------------+
| - Logger()         |
|   (private)        |
+--------------------+
| + getInstance():   |
|   Logger (static)  |
| + log(message):    |
|   void             |
+--------------------+

          ▲
          |
          |
+--------------------+
|   Application      |
+--------------------+
| + run(): void      |
+--------------------+

+--------------------+
|      Main          |
+--------------------+
| + main(args): void |
+--------------------+


public class Logger {

    // Volatile ensures visibility across threads
    private static volatile Logger instance;

    // Private constructor prevents object creation from outside
    private Logger() { }

    public static Logger getInstance() {
        if (instance == null) {                  // First check (no locking)
            synchronized (Logger.class) {         // Lock
                if (instance == null) {          // Second check (with lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("Log: " + message);
    }
}

class Application {

    public void run() {
        Logger logger = Logger.getInstance();
        logger.log("Application started");
    }
}

public class Main {
    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("First log");
        logger2.log("Second log");

        // Verifying Singleton behavior
        System.out.println(logger1 == logger2); // true
    }
}
