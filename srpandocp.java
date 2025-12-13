//Implemented the code using SRP and OCP principles

import java.util.ArrayList;
import java.util.List;

/* ===== Validator Interface (OCP foundation) ===== */
interface Validator {
    boolean isValid(String value);
    String errorMessage();
}

/* ===== Concrete Validators (SRP) ===== */
class NameValidator implements Validator {
    @Override
    public boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public String errorMessage() {
        return "Name cannot be empty";
    }
}

class EmailValidator implements Validator {
    @Override
    public boolean isValid(String value) {
        return value != null && value.contains("@");
    }

    @Override
    public String errorMessage() {
        return "Invalid email format";
    }
}

class PhoneValidator implements Validator {
    @Override
    public boolean isValid(String value) {
        return value != null && value.length() == 10;
    }

    @Override
    public String errorMessage() {
        return "Invalid phone number";
    }
}

class PasswordValidator implements Validator {
    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= 6;
    }

    @Override
    public String errorMessage() {
        return "Password must be at least 6 characters long";
    }
}

/* ===== User Registration (SRP + OCP) ===== */
class UserRegistration {

    private List<Validator> validators;

    public UserRegistration(List<Validator> validators) {
        this.validators = validators;
    }

    public void register(String name, String email, String phone, String password) {

        String[] inputs = { name, email, phone, password };

        for (int i = 0; i < validators.size(); i++) {
            if (!validators.get(i).isValid(inputs[i])) {
                System.out.println(validators.get(i).errorMessage());
                return;
            }
        }

        System.out.println("User registered successfully!");
    }
}

/* ===== Main Class ===== */
public class Main {
    public static void main(String[] args) {

        List<Validator> validators = new ArrayList<>();
        validators.add(new NameValidator());
        validators.add(new EmailValidator());
        validators.add(new PhoneValidator());
        validators.add(new PasswordValidator());

        UserRegistration registration = new UserRegistration(validators);

        registration.register(
            "Animesh Yadav",
            "animeshyadav132@gmail.com",
            "8452068978",
            "@nimesh"
        );
    }
}
