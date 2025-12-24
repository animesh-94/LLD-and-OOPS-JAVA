Design a TV Remote Control System where:

The remote control should be able to perform different actions like:
Turn ON / OFF the TV
Change volume
Change channel
The remote should not be tightly coupled to the TV’s internal implementation

New commands (Mute, HDMI switch, Screenshot, etc.) should be added without modifying the remote

Actions should be encapsulated as objects
This problem demonstrates how to decouple the invoker (Remote) from the 
receiver (TV) using the Command Design Pattern.



// ===================== COMMAND INTERFACE =====================
// Declares a common interface for all commands
interface Command {
    void action();
}

// ===================== CONCRETE COMMANDS =====================
// Concrete Command to turn ON the TV
class TurnOnCommand implements Command {
    private Tv tv;

    public TurnOnCommand(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void action() {
        tv.turnOn();
    }
}

// Concrete Command to turn OFF the TV
class TurnOffCommand implements Command {
    private Tv tv;

    public TurnOffCommand(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void action() {
        tv.turnOff();
    }
}

// Concrete Command to adjust TV volume
class VolumeButton implements Command {
    private Tv tv;
    private int volume;

    public VolumeButton(Tv tv, int volume) {
        this.tv = tv;
        this.volume = volume;
    }

    @Override
    public void action() {
        tv.adjustVolume(volume);
    }
}

// Concrete Command to change TV channel
class ChannelButton implements Command {
    private Tv tv;
    private int channel;

    public ChannelButton(Tv tv, int channel) {
        this.tv = tv;
        this.channel = channel;
    }

    @Override
    public void action() {
        tv.adjustChannel(channel);
    }
}

// ===================== INVOKER =====================
// RemoteControl triggers commands without knowing their implementation
class RemoteControl {
    private Command onCommand;
    private Command offCommand;

    public void setOnCommand(Command onCommand) {
        this.onCommand = onCommand;
    }

    public void setOffCommand(Command offCommand) {
        this.offCommand = offCommand;
    }

    public void pressOnButton() {
        onCommand.action();
    }

    public void pressOffButton() {
        offCommand.action();
    }
}

// ===================== RECEIVER =====================
// TV knows how to perform the actual operations
class Tv {

    public void turnOn() {
        System.out.println("Television is turned ON");
    }

    public void turnOff() {
        System.out.println("Television is turned OFF");
    }

    public void adjustVolume(int volume) {
        System.out.println("Volume changed to: " + volume);
    }

    public void adjustChannel(int channel) {
        System.out.println("Channel changed to: " + channel);
    }
}

// ===================== CLIENT =====================
public class Main {

    public static void main(String[] args) {

        // Receiver
        Tv tv = new Tv();

        // Commands
        Command turnOn = new TurnOnCommand(tv);
        Command turnOff = new TurnOffCommand(tv);
        Command volumeUp = new VolumeButton(tv, 69);
        Command changeChannel = new ChannelButton(tv, 70);

        // Invoker
        RemoteControl remote = new RemoteControl();

        // Client configures commands
        remote.setOnCommand(turnOn);
        remote.pressOnButton();

        changeChannel.action();
        volumeUp.action();

        remote.setOffCommand(turnOff);
        remote.pressOffButton();
    }
}
