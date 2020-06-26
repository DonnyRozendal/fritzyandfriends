package nl.hva.fritzyandfriends.localnetty.endpoints;

import contracts.generated.Fritzy;

import nl.hva.fritzyandfriends.common.Confirmation;
import nl.hva.fritzyandfriends.common.Transaction;
import nl.hva.fritzyandfriends.common.TransactionConfirmation;
import nl.hva.fritzyandfriends.localnetty.utils.FritzyGasProvider;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;
import reactor.core.publisher.Mono;

import static nl.hva.fritzyandfriends.common.TransactionType.BUY;
import static nl.hva.fritzyandfriends.common.TransactionType.SELL;

@RestController
public class BlockchainController {

    //Ethereum node address.
    private Web3j web3 = Web3j.build(new HttpService("https://kovan.infura.io/v3/db1c0cb6d5b245208edf0bbfc2a088ee"));

    //Private key of the wallet that is used for the transaction fees.
    private Credentials creds = Credentials.create("88D12DA1B252CC7B2AA2E4584504F0CEAC608373F0B00E00BF6A751446DA1ACE");

    //Address that the contract is deployed to.
    private String contractAddress = "0xcf0f7c8906b774c5775889273a4c543f374f8a53";

    //Smart contract java wrapper
    private Fritzy fritzyContract;

    //Contract gas provider that handles the max amount of gas payed for transaction.
    private ContractGasProvider contractGasProvider = new FritzyGasProvider();

    // Constructor, loadContract is called so you don't have to manually
    public BlockchainController() throws Exception {
//        deployContract();
        loadContract();
    }

    /**
     * Deploys a new contract and returns the address of the deployed contract.
     *
     * @return the Contractadress as a String
     * @throws Exception
     */
    @GetMapping("/deploy")
    Mono<String> deployContract() throws Exception {
        contractAddress = Fritzy.deploy(web3, creds, contractGasProvider).send().getContractAddress();
        return Mono.just(contractAddress);
    }

    /**
     * Loads the smart contract with the contractadress ,node, credentials and gasprovider. The contract can only be used
     * if the result of fritzyContract.isValid() = true. Otherwise the contract is not functioning.
     *
     * @return the Confirmation that the contract is loaded
     * @throws Exception
     */
    @GetMapping("/load")
    Mono<Confirmation> loadContract() throws Exception {
        fritzyContract = Fritzy.load(contractAddress, web3, creds, contractGasProvider);
//        System.out.println("isContractValid=" + fritzyContract.isValid());
        return Mono.just(new Confirmation("Contract loaded."));
    }

    /**
     * Set another wallet private key to be used as the credentials
     *
     * @return the Confirmation that the credentials have changed
     * @throws Exception
     */
    @PostMapping("/setCredentials")
    Mono<Confirmation> setCredentials(@RequestParam String walletPkey) throws Exception {
        creds = Credentials.create(walletPkey);
        return Mono.just(new Confirmation("Credentials changed."));

    }

    /**
     * Makes a transaction to write to the smart contract with the information passed on from LocalNetty to make a
     * buying/selling transaction with the desired power in kwh.
     *
     *
     * @return the TransactionConfirmation that the transaction is successfully made.
     * @throws Exception
     */
    @PostMapping("/makeTransaction")
    public Mono<TransactionConfirmation> makeTransaction(@RequestParam boolean isSelling, @RequestParam BigInteger power) throws Exception {
        TransactionReceipt transaction2 = fritzyContract.makeTransaction(isSelling, power).send();
        String trans2Hash = transaction2.getTransactionHash();
        System.out.println("Transaction made " + trans2Hash);

        if (isSelling) {
            return Mono.just(new TransactionConfirmation(SELL, power, "Transaction successfully made"));
        } else {
            return Mono.just(new TransactionConfirmation(BUY, power, "Transaction successfully made"));
        }
    }

    /**
     * Gets all transactions that are made by the message.sender. These are done by getting the two corresponding arrays
     * which contain the power and isSelling values for the transactions posted by the message.sender.
     *
     * The reason why two seperate arrays are being used for these values instead of a structure array is because
     * returning structure arrays in solidity is not supported yet and is only possible by using the experimental ABIEncoderV2
     * which for NOW makes it unable to be used in live contracts.
     *
     *
     * @return the ArrayList filled with Transactions made by the user.
     * @throws Exception
     */
    @GetMapping("/getAllTransactions")
    Mono<ArrayList> getAllTransactions() throws Exception {
        List resultPower = fritzyContract.getPowerArray().send();
        List resultSelling = fritzyContract.getIsSellingArray().send();

        ArrayList<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < resultPower.size(); i++) {
            if ((Boolean) resultSelling.get(i)) {
                transactions.add(new Transaction(SELL, (BigInteger) resultPower.get(i)));
            } else if (!(Boolean) resultSelling.get(i)) {
                transactions.add(new Transaction(BUY, (BigInteger) resultPower.get(i)));

            }
        }

        System.out.println(transactions);

        return Mono.just(transactions);

    }


    @GetMapping("/balance")
    Mono<String> balance() throws IOException {
        EthGetBalance balanceWei = web3.ethGetBalance(creds.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);
        return Mono.just("balance in ether: " + balanceInEther);
    }

    @GetMapping("/info")
    Mono<String> info() throws IOException {
        // web3_clientVersion returns the current client version.
        Web3ClientVersion clientVersion = web3.web3ClientVersion().send();
        // eth_blockNumber returns the number of most recent block.
        EthBlockNumber blockNumber = web3.ethBlockNumber().send();
        // eth_gasPrice, returns the current price per gas in wei.
        EthGasPrice gasPrice = web3.ethGasPrice().send();

        return Mono.just("Client version: " + clientVersion.getWeb3ClientVersion() + "\nBlock number" + blockNumber.getBlockNumber() + "\nGas price" + gasPrice.getGasPrice());
    }


}
