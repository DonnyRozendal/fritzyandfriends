package nl.hva.fritzyandfriends.common;

import java.math.BigInteger;

public class Transaction {

    private TransactionType transactionType;
    private BigInteger kwh;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, BigInteger kwh) {
        this.transactionType = transactionType;
        this.kwh = kwh;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigInteger getKwh() {
        return kwh;
    }
}