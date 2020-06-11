package nl.hva.fritzyandfriends.common;

public class Transaction {

    private TransactionType transactionType;
    private int kwh;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, int kwh) {
        this.transactionType = transactionType;
        this.kwh = kwh;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getKwh() {
        return kwh;
    }
}