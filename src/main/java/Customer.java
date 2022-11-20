import java.util.ArrayList;
import java.util.List;

// Customer POJO class
public class Customer {
    int accNum;
    String accHolder;
    int pin, accBalance;

    List<Transaction> transactionList = new ArrayList<>();

    public Customer(){

    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Customer(int accNum, String accHolder, int pin, int accBalance) {
        this.accNum = accNum;
        this.accHolder = accHolder;
        this.pin = pin;
        this.accBalance = accBalance;
    }

    public int getAccNum() {
        return accNum;
    }

    public String getAccHolder() {
        return accHolder;
    }

    public void setAccHolder(String accHolder) {
        this.accHolder = accHolder;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(int accBalance) {
        this.accBalance = accBalance;
    }
}
