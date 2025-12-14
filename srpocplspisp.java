//Implemented the code using SRP,OCP and LSP principles and it also follows ISP principle

import java.util.ArrayList;
import java.util.List;

interface saveData{
    void dataSave(String data);
}

interface loadData{
    void dataLoad();
}

interface StorageService extends saveData, loadData{
    
}

class localStorageService implements StorageService{
    @Override
    public void dataSave(String data){
        
    }
    
    @Override
    public void dataLoad(){
        
    }
}

class databaseStorageService implements StorageService{
    @Override
    public void dataSave(String data){
        
    }
    
    @Override
    public void dataLoad(){
        
    }
}

class ConfigurationManager {
    private final StorageService storage;

    public ConfigurationManager(StorageService storage) {
        this.storage = storage;
    }

    public void saveConfig(String config) {
        storage.dataSave(config);
    }

    public void loadConfig() {
        storage.dataLoad();
    }
}

public class Main {
    public static void main(String[] args) {
        StorageService localStorage = new LocalStorageService();
        StorageService databaseStorage = new DatabaseStorageService();

        ConfigurationManager manager1 = new ConfigurationManager(localStorage);
        ConfigurationManager manager2 = new ConfigurationManager(databaseStorage);

        manager1.saveConfig("theme=dark");
        manager2.saveConfig("theme=light");
    }
}

