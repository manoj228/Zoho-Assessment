public class Transaction {
    int id;
    String transactionRemarks;
    String transactionType;
    int transactionAmount;

    public Transaction()
    {

    }

    public Transaction(int id, String transactionRemarks, String transactionType, int transactionAmount) {
        this.id = id;
        this.transactionRemarks = transactionRemarks;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionRemarks() {
        return transactionRemarks;
    }

    public void setTransactionRemarks(String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
