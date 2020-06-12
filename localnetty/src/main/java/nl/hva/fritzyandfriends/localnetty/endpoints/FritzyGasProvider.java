package nl.hva.fritzyandfriends.localnetty.endpoints;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class FritzyGasProvider implements ContractGasProvider {
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return BigInteger.valueOf(20000000000L);
    }

    @Override
    public BigInteger getGasPrice() {
        return BigInteger.valueOf(20000000000L);
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return BigInteger.valueOf(6721975L);
    }

    @Override
    public BigInteger getGasLimit() {
        return BigInteger.valueOf(6721975L);
    }
}
