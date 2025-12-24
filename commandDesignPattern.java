Design a Smart Home Automation System where a central controller 
can operate multiple home devices such as Lights, Fan, and AC.

The system must:
Decouple the controller from device implementations
Treat each user action as an object
Support Macro Commands (e.g., Good Morning, Good Night)
Support Undo of the last executed command
Follow SOLID principles and clean LLD practices

Constraints:
The controller should not directly call device methods
New devices or commands should be addable without modifying existing code
Macro commands should behave like normal commands

┌──────────────┐
│     User     │
└──────┬───────┘
       │ Press Button
       ▼
┌──────────────────┐
│   Controller     │  (Invoker)
│ (Remote / App)   │
└──────┬───────────┘
       │ execute()
       ▼
┌──────────────────┐
│     Command      │  (Interface)
│  execute / undo  │
└──────┬───────────┘
       │
       │ (If Single Command)
       │
       ▼
┌──────────────────┐
│ ConcreteCommand  │
│ (LightOn, FanOn) │
└──────┬───────────┘
       │ delegates
       ▼
┌──────────────────┐
│      Home        │  (Receiver)
│ (Lights, Fan, AC)│
└──────────────────┘


import java.util.*;

// ===================== COMMAND =====================
interface Command {
    void execute();
    void undo();
}

// ===================== RECEIVER =====================
class Home {

    public void lightOn() {
        System.out.println("Lights ON");
    }

    public void lightOff() {
        System.out.println("Lights OFF");
    }

    public void fanOn() {
        System.out.println("Fan ON");
    }

    public void fanOff() {
        System.out.println("Fan OFF");
    }

    public void acOn() {
        System.out.println("AC ON");
    }

    public void acOff() {
        System.out.println("AC OFF");
    }
}

// ===================== CONCRETE COMMANDS =====================
class LightOn implements Command {
    private Home home;

    public LightOn(Home home) {
        this.home = home;
    }

    public void execute() {
        home.lightOn();
    }

    public void undo() {
        home.lightOff();
    }
}

class LightOff implements Command {
    private Home home;

    public LightOff(Home home) {
        this.home = home;
    }

    public void execute() {
        home.lightOff();
    }

    public void undo() {
        home.lightOn();
    }
}

class FanOn implements Command {
    private Home home;

    public FanOn(Home home) {
        this.home = home;
    }

    public void execute() {
        home.fanOn();
    }

    public void undo() {
        home.fanOff();
    }
}

class FanOff implements Command {
    private Home home;

    public FanOff(Home home) {
        this.home = home;
    }

    public void execute() {
        home.fanOff();
    }

    public void undo() {
        home.fanOn();
    }
}

class AcOn implements Command {
    private Home home;

    public AcOn(Home home) {
        this.home = home;
    }

    public void execute() {
        home.acOn();
    }

    public void undo() {
        home.acOff();
    }
}

class AcOff implements Command {
    private Home home;

    public AcOff(Home home) {
        this.home = home;
    }

    public void execute() {
        home.acOff();
    }

    public void undo() {
        home.acOn();
    }
}

// ===================== MACRO COMMAND =====================
class MacroCommand implements Command {
    private List<Command> commands;

    public MacroCommand(List<Command> commands) {
        this.commands = commands;
    }

    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}

// ===================== INVOKER =====================
class Controller {
    private Stack<Command> history = new Stack<>();

    public void press(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}

// ===================== CLIENT =====================
public class Main {
    public static void main(String[] args) {

        Home home = new Home();

        // Individual commands
        Command lightOn = new LightOn(home);
        Command fanOff = new FanOff(home);
        Command acOff = new AcOff(home);

        // Macro Commands
        Command goodMorning = new MacroCommand(
                Arrays.asList(lightOn, fanOff, acOff)
        );

        Command goodNight = new MacroCommand(
                Arrays.asList(
                        new LightOff(home),
                        new FanOn(home),
                        new AcOn(home)
                )
        );

        Controller controller = new Controller();

        System.out.println("=== Good Morning Mode ===");
        controller.press(goodMorning);

        System.out.println("\nUndo Good Morning");
        controller.undo();

        System.out.println("\n=== Good Night Mode ===");
        controller.press(goodNight);

        System.out.println("\nUndo Good Night");
        controller.undo();
    }
}
