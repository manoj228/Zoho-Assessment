import java.util.HashMap;
import java.util.LinkedHashMap;

public class Constants {

    public HashMap<Integer, Integer> addCash()
    {
        HashMap<Integer, Integer> curToDenom = new LinkedHashMap<>();

        // add cash
        curToDenom.put(1000, 20);
        curToDenom.put(500, 100);
        curToDenom.put(100, 300);

        return curToDenom;
    }

    public HashMap<Integer, Customer> addCustomer(){

        HashMap<Integer, Customer> cusMap = new LinkedHashMap<>();

        // add customer
        cusMap.put(101, new Customer(101,"Suresh", 2343, 25234));
        cusMap.put(102, new Customer(102,"Ganesh", 5432, 34123));
        cusMap.put(103, new Customer(103,"Magesh", 7854, 26100));
        cusMap.put(104, new Customer(104,"Naresh", 2345, 80000));
        cusMap.put(105, new Customer(105,"Harish", 1907, 103400));

        return cusMap;
    }
}
