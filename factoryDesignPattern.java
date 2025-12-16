//example of Factory Design Pattern

interface ButtonFunctionService{
    void render();
    void onClick();
}

class WindowsButton implements ButtonFunctionService{
    @Override
    public void onClick(){
        System.out.println("Clicked the Windows button....Initializing the rendering.....");
    }
    @Override
    public void render(){
        System.out.println("Rendering the Windows interface");
    }
}

class MacButton implements ButtonFunctionService{
    @Override
    public void onClick(){
        System.out.println("Clicked the Mac button....Initializing the rendering.....");
    }
    @Override
    public void render(){
        System.out.println("Rendering the Mac interface");
    }
}

class LinuxButton implements ButtonFunctionService{
    @Override
    public void onClick(){
        System.out.println("Clicked the Linux button....Initializing the rendering.....");
    }
    @Override
    public void render(){
        System.out.println("Rendering the Linux interface");
    }
}

class ButtonFactoryMethod{
    public static ButtonFunctionService getButton(String button){
        if(button.equals("Windows")){
            return new WindowsButton();
        }
        else if(button.equals("Mac")){
            return new MacButton();
        }
        else if(button.equals("Linux")){
            return new LinuxButton();
        }
        else{
            throw new IllegalArgumentException("Invalid button input");
        }
    }
}

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
