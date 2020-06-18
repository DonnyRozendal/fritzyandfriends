package nl.hva.fritzyandfriends.common;

import java.math.BigInteger;

public class TransactionConfirmation {

    private TransactionType transactionType;
    private BigInteger kwh;
    private String description;

    public TransactionConfirmation() {
    }

    public TransactionConfirmation(TransactionType transactionType, BigInteger kwh, String description) {
        this.transactionType = transactionType;
        this.kwh = kwh;
        this.description = description;
    }

    public TransactionConfirmation(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigInteger getKwh() {
        return kwh;
    }

    public String getDescription() {
        return description;
    }
}
