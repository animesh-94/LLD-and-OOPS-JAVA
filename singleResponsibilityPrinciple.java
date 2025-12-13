//example of Single Responsibility Principle 
//According to this principle one class should be responsible for only one task or purpose not more than one

//Commented Code below gives an overview of how a class looks like before segregating the classes 

// class UserResgistration{
    
//     void addUser(String name, String cellNumber, String email, String setPassword){
//         if(name == null || name.isEmpty()){
//             System.out.println("Name is Empty");
//             return;
//         }
//         else if(!email.contains("@")){
//             System.out.println("Email is invalid");
//             return;
//         }
//         else if(cellNumber.length() < 10){
//             System.out.println("Number is invalid");
//             return;
//         }
//         else if(setPassword.length() < 6){
//             System.out.println("Password is too short");
//             return;
//         }
        
//         System.out.println("Welcome to the personalized Dashboard.......");
//         System.out.println("Name: " + name);
//         System.out.println("Email: " + email);
//         System.out.println("Phone number: " + cellNumber);
//     }
// }

//Clean code using Single Responsibility Principle
class NameValidator {
    boolean isValid(String name) {
        return name != null && !name.isEmpty();
    }
}

class EmailValidator {
    boolean isValid(String email) {
        return email != null && email.contains("@");
    }
}

class PhoneValidator {
    boolean isValid(String number) {
        return number != null && number.length() == 10;
    }
}

class PasswordValidator {
    boolean isValid(String password) {
        return password != null && password.length() >= 6;
    }
}


class UserRegistration {

    private NameValidator nameValidator = new NameValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private PhoneValidator phoneValidator = new PhoneValidator();
    private PasswordValidator passwordValidator = new PasswordValidator();

    void register(String name, String email, String phone, String password) {

        if (!nameValidator.isValid(name)) {
            System.out.println("Invalid name");
            return;
        }

        if (!emailValidator.isValid(email)) {
            System.out.println("Invalid email");
            return;
        }

        if (!phoneValidator.isValid(phone)) {
            System.out.println("Invalid phone number");
            return;
        }

        if (!passwordValidator.isValid(password)) {
            System.out.println("Invalid password");
            return;
        }

        System.out.println("User registered successfully!");
    }
}


public class Main {
    public static void main(String[] args) {

        UserRegistration registration = new UserRegistration();

        registration.register(
            "Animesh Yadav",
            "animeshyadav132@gmail.com",
            "8452068978",
            "@nimesh"
        );
    }
}

