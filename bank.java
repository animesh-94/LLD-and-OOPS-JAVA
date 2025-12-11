class AccountInfo{
    private String accountHolderName;
    private String accountHolderAccountNumber;
    private int balance;
    
    AccountInfo(String accountHolderName, String accountHolderAccountNumber, int balance){
        this.accountHolderName = accountHolderName;
        this.accountHolderAccountNumber = accountHolderAccountNumber;
        this.balance = balance;
    }
    
    public String getAccountHolderName(){
        return accountHolderName;
    }
    
    public String getAccountHolderAccountNumber(){
        return accountHolderAccountNumber;
    }
    
    public int getBalance(){
        return balance;
    }
    
    public void setBalance(int balance){
        this.balance = balance;
    }
    
    void deposit(int amount){
        balance += amount;
    }
    
    void withdraw(int amount){
        if(getBalance()-amount >= 0){
            System.out.println("Amount of " + amount + " is withdrawn successfully");
            setBalance(getBalance()-amount);
        }
        else{
            System.out.println("Insufficient Amount");
        }
    }
    
    void displayDetails(){
        System.out.println("Account Holder: " + getAccountHolderName());
        System.out.println("Account Number: " + getAccountHolderAccountNumber());
        System.out.println("Account balance: " + getBalance());
    }
}

public class Main
{
	public static void main(String[] args) {
		AccountInfo a1 = new AccountInfo("Animesh Yadav", "123456HXBC568", 1000000);
		a1.deposit(10000);
		a1.withdraw(5000);
		a1.displayDetails();
	}
}
