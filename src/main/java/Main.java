import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String canContinue;
        int operationNum, atmBalance = 0;
        HashMap<Integer, Integer> atmMap = new LinkedHashMap<>();
        HashMap<Integer, Customer> cusMap = new LinkedHashMap<>();

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("Welcome to ATM Process");
            System.out.println("ATM Operations");
            System.out.println("1) Load Cash To the ATM");
            System.out.println("2) Display Customer Information");
            System.out.println("3) Show ATM process");
            System.out.println("4) Add Customer");
            System.out.println("5) Add Cash");
            System.out.println("Enter any one number from (1 to 3) to perform above mentioned operation");
            Scanner scanner = new Scanner(System.in);
            operationNum = scanner.nextInt();
            Constants constants = new Constants();

            switch (operationNum) {
                case 1 -> {
                    HashMap<Integer, Integer> newAtmMap = constants.addCash();
                    atmBalance += loadCashToATM(newAtmMap, atmMap);
                    displayATMBalanceWithDenom(atmBalance, atmMap);
                }
                case 2 -> {
                    HashMap<Integer, Customer> newCusMap = constants.addCustomer();
                    displayAllCustomers(newCusMap, cusMap);
                }
                case 3 -> showATMProcess(atmBalance, atmMap, cusMap);

                case 4 -> addCustomer(cusMap);

                case 5 ->{
                    atmBalance += addCash(atmMap);
                    displayATMBalanceWithDenom(atmBalance,atmMap);
                }


                default -> System.out.println("Invalid Num!! Cannot able to perform action :(");
            }

            System.out.println("Enter (Y/y) option to continue");
            canContinue = scanner.next();

        } while (canContinue.equals("Y") | canContinue.equals("y"));

    }

    private static void addCustomer(HashMap<Integer, Customer> customerHashMap)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Customer Details to add");
        System.out.println("Enter Account number : ");
        int accNum = scanner.nextInt();

        System.out.println("Enter customerName : ");
        String customerName = scanner.next();

        System.out.println("Enter pin : ");
        int pin = scanner.nextInt();

        System.out.println("Enter balance : ");
        int balance = scanner.nextInt();

        customerHashMap.put(accNum, new Customer(accNum, customerName, pin, balance));
        System.out.println("Customer Details added Successfully");
    }

    private static int addCash(HashMap<Integer,Integer> atmMap)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Cash with Denominations to add");
        System.out.println("Enter Currency : ");
        int currency = scanner.nextInt();

        System.out.println("Enter denominations : ");
        int denominations = scanner.nextInt();

        atmMap.put(currency, atmMap.getOrDefault(currency, 0) + denominations);
        System.out.println("Cash Details added Successfully");

        return (currency * denominations);
    }

    private static void showATMProcess(int atmBalance, HashMap<Integer, Integer> atmMap, HashMap<Integer, Customer> cusMap) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter your Account number \t");
            int accNum = scanner.nextInt();

            if (isValidAcc(accNum, cusMap)) {
                System.out.println("Enter your pin \t");
                int pin = scanner.nextInt();

                if (isValidPin(accNum, pin, cusMap)) {
                    Customer customer = cusMap.get(accNum);
                    int operation = 1;
                    do {
                        System.out.println("----------------------------------------------");
                        System.out.println("1) Check Balance");
                        System.out.println("2) WithDraw Money");
                        System.out.println("3) Transfer Money");
                        System.out.println("4) Mini Statement");
                        System.out.println("5) Go Back");
                        System.out.println("Enter any one number from 1 to 4 to proceed ");
                        operation = scanner.nextInt();

                        switch (operation) {
                            case 1 -> checkBalance(customer);
                            case 2 -> atmBalance = withdraw(atmBalance, atmMap, customer, cusMap);
                            case 3 -> transferMoney(customer, cusMap);
                            case 4 -> miniStatement(cusMap);
                        }

                    } while (operation >= 1 && operation <= 4);
                } else {
                    System.out.println("Invalid Pin");
                }
            }
            else{
                System.out.println("OOPS!! Account number was wrong! Please Enter it correctly");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong! Please check account number or Pin");
        }

    }

    private static void miniStatement(HashMap<Integer, Customer> cusMap) {

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter Account number to check mini Statement\t");
            int accNum = scanner.nextInt();

            if (isValidAcc(accNum, cusMap)) {
                System.out.println("Enter your pin \t");
                int pin = scanner.nextInt();

                if (isValidPin(accNum, pin, cusMap)) {
                    Customer customer = cusMap.get(accNum);

                    System.out.println("=========================================");
                    System.out.println("Account Number " + accNum);
                    System.out.println("Account Holder " + customer.getAccHolder() + " account");
                    System.out.println("Balance " + customer.getAccBalance());

                    List<Transaction> transactionList = customer.getTransactionList();

                    System.out.println();
                    if(transactionList.size() > 0)
                        System.out.println("Transaction_ID\t\tTransaction_Remarks\t\t\tTransaction_Type\t\tTransaction_Amt");
                    else
                        System.out.println("No Transaction");

                    for (Transaction transaction : transactionList) {
                        System.out.println(transaction.getId() + "\t\t\t\t\t" + transaction.getTransactionRemarks() + "\t\t" +
                                transaction.getTransactionType() + "\t\t\t\t" + transaction.getTransactionAmount());
                    }

                    System.out.println();


                } else {
                    System.out.println("Invalid Pin");
                }
            }
            else{
                System.out.println("OOPS!! Invalid Account Number");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong! Please check account number or Pin");
        }


    }

    private static void transferMoney(Customer sender, HashMap<Integer, Customer> cusMap) {
        // transaction limit -> 1000 to 10,000
        Scanner scanner = new Scanner(System.in);

        try
        {
            System.out.println("Enter account to transfer money");
            int transferAcc = scanner.nextInt();

            // if valid acc to transfer
            if (transferAcc != sender.getAccNum() && isValidAcc(transferAcc, cusMap)) {
                Customer receiver = cusMap.get(transferAcc);

                System.out.println("Enter amount of money to be transferred (should be multiples of 1000/500/100)");
                System.out.println("Note: Transaction limit is between 1000 to 10,000");
                int moneyToSend = scanner.nextInt();

                if (moneyToSend >= 1000 && moneyToSend <= 10000) {
                    if (sender.getAccBalance() >= moneyToSend) {
                        int senderBalance = sender.getAccBalance();
                        int receiverBalance = receiver.getAccBalance();

                        int remSenderBalance = senderBalance - moneyToSend;
                        int newReceiverBalance = receiverBalance + moneyToSend;

                        // update balance for both sender and receiver
                        sender.setAccBalance(remSenderBalance);
                        receiver.setAccBalance(newReceiverBalance);

                        // update transaction for sender
                        Transaction senderTransaction = new Transaction(sender.getTransactionList().size() + 1, "Funds transfer to Acc " + receiver.getAccNum(), "Debit", moneyToSend);
                        sender.getTransactionList().add(senderTransaction);

                        // update transaction for receiver
                        Transaction receiverTransaction = new Transaction(receiver.getTransactionList().size() + 1, "Funds Transfer from Acc " + sender.getAccNum(), "Credit", moneyToSend);
                        receiver.getTransactionList().add(receiverTransaction);

                        System.out.println("Available Balance " + sender.getAccBalance());
//                        System.out.println("Receiver Balance " + receiver.getAccBalance());
                    } else {
                        System.out.println("Insufficient money to transfer");
                    }
                } else {
                    System.out.println("Transaction limit is between 1000 to 10,000");
                }

            }
            else if(transferAcc == sender.getAccNum())
            {
                System.out.println("Account number and Transfer Account number are same");
            }
            else {
                System.out.println("OOPS!! Invalid Account Number");
            }
        }
        catch (Exception e)
        {
            System.out.println("OOPS!! Invalid Account number");
        }
    }

    private static boolean isValidAcc(int accNum, HashMap<Integer, Customer> cusMap) {
        // account number should be present in map, then only it is valid
        return cusMap.containsKey(accNum);
    }

    private static boolean isValidPin(int accNum, int pin, HashMap<Integer, Customer> cusMap) {
        // invalid acc
        if (cusMap.get(accNum) == null)
            return false;

        // both pins are matching or not
        return cusMap.get(accNum).getPin() == pin;
    }

    private static int withdraw(int atmBalance, HashMap<Integer, Integer> atmMap, Customer customer, HashMap<Integer, Customer> customerHashMap) {

        // withdraw limit is 100 to 10,000 at a time
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter your pin to verify for account " + customer.getAccNum());
            int pin = scanner.nextInt();

            if (isValidPin(customer.getAccNum(), pin, customerHashMap)) {
                System.out.println("Enter the amount to be withdrawn (Should be multiples of 1000/500/100)");
                System.out.println("Note: Withdraw limit is 100 to 10,000 per transaction");
                int amountToBeWithdrawn = scanner.nextInt();

                if (amountToBeWithdrawn >= 100 && amountToBeWithdrawn <= 10000) {
                    if (atmBalance >= amountToBeWithdrawn) {
                        if (customer.getAccBalance() >= amountToBeWithdrawn) {
                            // withdraw money and update accordingly
                            atmBalance = withdrawMoney(atmBalance, atmMap, customer, amountToBeWithdrawn);
                        } else {
                            System.out.println("Insufficient Balance to withdraw!!");
                        }
                    } else {
                        System.out.println("Apologies!! ATM Doesn't have " + amountToBeWithdrawn + " to withdraw");
                        System.out.println("ATM balance " + atmBalance);
                    }
                } else {
                    System.out.println(amountToBeWithdrawn + " cannot be withdrawn!! Please Try again!!");
                }
            }
            else{
                System.out.println("OOPS!! Invalid Pin");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!! Please check your pin!!");
        }

        return atmBalance;
    }

    private static int withdrawMoney(int atmBalance, HashMap<Integer, Integer> atmMap, Customer customer, int amountToBeWithdrawn)
    {
        // to check atm balance
//        System.out.println("Before Withdrawn");
//        displayATMBalanceWithDenom(atmBalance, atmMap);

        // update customer balance
        int customerBalance = customer.getAccBalance() - amountToBeWithdrawn;
        int withdrawalAmt = amountToBeWithdrawn;
        customer.setAccBalance(customerBalance);

        if(amountToBeWithdrawn >= 100 && amountToBeWithdrawn <= 5000)
        {
            // 1000 rupee currency -> 1, if it's more than 1000
            if(amountToBeWithdrawn >= 1000 && atmMap.getOrDefault(1000, 0) >= 1)
            {
                int[] res = deduct(atmBalance, atmMap, amountToBeWithdrawn, 1000, 1);
                // updating values
                amountToBeWithdrawn = res[0];
                atmBalance = res[1];
            }

            // 100 rupee currency -> 10 denominations allowed
            if(amountToBeWithdrawn > 0 && atmMap.getOrDefault(100, 0) >= 1)
            {
                int[] res = deduct(atmBalance, atmMap, amountToBeWithdrawn, 100, 10);
                // updating values
                amountToBeWithdrawn = res[0];
                atmBalance = res[1];
            }

            // 500 rupee currency -> 6 denominations allowed
            if(amountToBeWithdrawn > 0 && atmMap.getOrDefault(500, 0) >= 1)
            {
                int[] res = deduct(atmBalance, atmMap, amountToBeWithdrawn, 500, 6);
                // updating values
                amountToBeWithdrawn = res[0];
                atmBalance = res[1];
            }

            if(amountToBeWithdrawn == 0) {
                Transaction transaction = new Transaction(customer.getTransactionList().size()+1, "Debited " + withdrawalAmt + " from ATM      ", "Debit", withdrawalAmt);
                customer.getTransactionList().add(transaction);
                System.out.println(withdrawalAmt + " withdrawn Successfully!!");
            }
            else{
                System.out.println("Cannot be Withdrawn at this moment :(");
            }
        }
        else if(amountToBeWithdrawn > 5000 && amountToBeWithdrawn <= 10000)
        {
            if(atmMap.getOrDefault(1000, 0) >= 1)
            {
                int[] res = deduct(atmBalance, atmMap, amountToBeWithdrawn, 1000, 3);
                // updating values
                amountToBeWithdrawn = res[0];
                atmBalance = res[1];
            }

            // 100 rupee currency -> 10 denominations allowed
            if(amountToBeWithdrawn > 0 && atmMap.getOrDefault(100, 0) >= 1)
            {
                int[] res = deduct(atmBalance, atmMap, amountToBeWithdrawn, 100, 10);
                // updating values
                amountToBeWithdrawn = res[0];
                atmBalance = res[1];
            }

            // 500 rupee currency -> 2 denominations allowed [increase for higher amount)
            if(amountToBeWithdrawn > 0 && atmMap.getOrDefault(500, 0) >= 2)
            {
                int notes = Math.min(amountToBeWithdrawn / 500, atmMap.get(500));

                amountToBeWithdrawn -= (500 * notes);
                atmBalance -= (500 * notes);

                // if available notes is same as minimal notes needed, then delete
                if(atmMap.get(500)== notes)
                    atmMap.remove(500);
                else
                    atmMap.put(500, atmMap.get(500) - notes);
            }

            if(amountToBeWithdrawn == 0) {
                Transaction transaction = new Transaction(customer.getTransactionList().size()+1, "Debited " + withdrawalAmt + " from ATM      ", "Debit", withdrawalAmt);
                customer.getTransactionList().add(transaction);
                System.out.println(withdrawalAmt + " withdrawn Successfully!!");
            }
            else{
                System.out.println("Cannot be Withdrawn at this moment :(");
            }
        }

//        System.out.println("After Withdrawn");
//        displayATMBalanceWithDenom(atmBalance, atmMap);

        return atmBalance;
    }

    // method to update atmMap and atmBalance while withdrawal
    private static int[] deduct(int atmBalance, HashMap<Integer, Integer> atmMap, int amountToBeWithdrawn,
                               int currency, int minimalNotesNeeded)
    {
        int minimalNotes = Math.min(atmMap.get(currency), minimalNotesNeeded);
        int notes = Math.min(amountToBeWithdrawn / currency, minimalNotes);

        amountToBeWithdrawn -= (currency * notes);
        atmBalance -= (currency * notes);

        // if available notes is same as minimal notes needed, then delete
        if(atmMap.get(currency)== minimalNotes)
            atmMap.remove(currency);
        else
            atmMap.put(currency, atmMap.get(currency) - notes);

        return new int[]{amountToBeWithdrawn, atmBalance};
    }
    private static void checkBalance(Customer customer) {
        System.out.println("Your Balance " + customer.getAccBalance());
    }

    private static void displayAllCustomers(HashMap<Integer, Customer> newCusMap, HashMap<Integer, Customer> cusMap) {
        cusMap.putAll(newCusMap);

        System.out.println("Customer Details");
        System.out.println("Acc No\t Acc Holder\t Pin \t Acc Balance");
        for(HashMap.Entry<Integer, Customer> entry : cusMap.entrySet())
        {
            int accNum = entry.getKey();
            String accHolder = entry.getValue().accHolder;
            int pin = entry.getValue().pin;
            int accBalance = entry.getValue().accBalance;

            System.out.println(accNum + "\t\t\t" + accHolder + "\t" + pin + "\t\t" + accBalance);
        }
    }

    private static int loadCashToATM(HashMap<Integer, Integer> denomMap, HashMap<Integer, Integer> atmMap) {

        int curBalance = 0;

        if(denomMap != null)
        {
            for(HashMap.Entry<Integer, Integer> entry : denomMap.entrySet())
            {
                int key = entry.getKey();
                int count = entry.getValue();

                // add cash to cur atm Map
                atmMap.put(key, atmMap.getOrDefault(key, 0) + count);
                curBalance += (key * count);
            }
            System.out.println(curBalance + " Cash loaded Successfully");
        }

        else{
            System.out.print("No cash to load");
        }

        return curBalance;
    }

    private static void displayATMBalanceWithDenom(int atmBalance, HashMap<Integer, Integer> atmMap)
    {
        System.out.println("Current ATM Balance :" + atmBalance);
        System.out.println("Currency\tDenomination");
        for(HashMap.Entry<Integer, Integer> entry : atmMap.entrySet())
        {
            System.out.println(entry.getKey() + " X " + entry.getValue() + " = " + entry.getKey() * entry.getValue());
        }
    }
}
