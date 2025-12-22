import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(String weather);
}

// Subject interface
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}

// Concrete Subject
class WeatherStation implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private String weather;

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
            observer.update(weather);
        }
    }

    // State change method
    public void setWeather(String newWeather) {
        this.weather = newWeather;
        notifyObserver(); // Automatically notify observers
    }
}

// Concrete Observer 1
class SMSService implements Observer {

    @Override
    public void update(String weather) {
        System.out.println("SMS: Weather is updated to " + weather);
    }
}

// Concrete Observer 2
class EmailService implements Observer {

    @Override
    public void update(String weather) {
        System.out.println("Email: Weather is updated to " + weather);
    }
}

// Client code
public class Main {
    public static void main(String[] args) {

        WeatherStation weatherStation = new WeatherStation();

        Observer smsService = new SMSService();
        Observer emailService = new EmailService();

        weatherStation.addObserver(smsService);
        weatherStation.addObserver(emailService);

        weatherStation.setWeather("Sunny");
        weatherStation.setWeather("Rainy");
    }
}
