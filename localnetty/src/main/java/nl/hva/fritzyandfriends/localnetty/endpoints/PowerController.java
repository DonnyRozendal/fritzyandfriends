package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.Confirmation;
import nl.hva.fritzyandfriends.common.InboxData;
import nl.hva.fritzyandfriends.common.Transaction;
import nl.hva.fritzyandfriends.common.TransactionConfirmation;
import nl.hva.fritzyandfriends.localnetty.database.InboxRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/power")
public class PowerController {

    private final InboxRepository inboxRepository;

    public PowerController(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    @PostMapping("/consume")
    Mono<InboxData> consumePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @PostMapping("/produce")
    Mono<InboxData> producePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @PostMapping("/store")
    Mono<InboxData> storePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @GetMapping("/getAllInboxData")
    Mono<Iterable<InboxData>> getAllInboxData() {
        return Mono.just(inboxRepository.findAll());
    }


    /**
     * This method calculates the transaction that has to be made based on the latest InboxData sent from the three actors.
     * <p>
     * The latest InboxData from each actor is put together in an array which is cycled through and based on if the
     * InboxData is from a consuming/producing or storage device their communicated kwh value is added to their respective ...Power Integer.
     * <p>
     * The netpower is then calculated and the corresponding transaction is then made(BUY/SELL or Nothing if netPower =0)
     *
     * @return the TransactionConfirmation if the transaction passed or failed.
     */
    @GetMapping("/calculateTransaction")
    Mono<TransactionConfirmation> calculateTransactions() {
        Optional<InboxData> fritzy = inboxRepository.findTopBySenderOrderByTimestampDesc("Fritzy");
        Optional<InboxData> batty = inboxRepository.findTopBySenderOrderByTimestampDesc("Batty");
        Optional<InboxData> sunny = inboxRepository.findTopBySenderOrderByTimestampDesc("Sunny");

        ArrayList<Optional<InboxData>> latestInboxData = new ArrayList<>();

        latestInboxData.add(fritzy);
        latestInboxData.add(batty);
        latestInboxData.add(sunny);

        int consumedPower = 0;
        int storedPower = 0;
        int producedPower = 0;

        for (Optional<InboxData> i : latestInboxData) {
            if (i.get().getSender().equals("Fritzy")) {
                consumedPower += i.get().getKwh();
            }
            if (i.get().getSender().equals("Batty")) {
                storedPower += i.get().getKwh();
            }
            if (i.get().getSender().equals("Sunny")) {
                producedPower += i.get().getKwh();
            }

        }

        int netPower = (producedPower + storedPower) - consumedPower;
        System.out.println("Netpower = Sunny:" + producedPower + " + Batty:" + storedPower + " - Fritzy:" + consumedPower);
        boolean isSelling;

        if (netPower > 0) {
            isSelling = true;
            System.out.println("Netpower > 0 so SELL Transaction with kwh value of " + netPower);
        } else if (netPower < 0) {
            isSelling = false;
            netPower = Math.abs(netPower);
            System.out.println("Netpower < 0 so BUY Transaction with kwh value of " + netPower);
        } else {
            System.out.println("Netpower = 0 so no transaction needed");
            return Mono.just(new TransactionConfirmation("Transaction not needed because netPower equals 0"));
        }

        try {
            BlockchainController blockchainController = new BlockchainController();
            return blockchainController.makeTransaction(isSelling, BigInteger.valueOf(netPower));
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.just(new TransactionConfirmation("Making transaction failed because of exception : " + e.getMessage()));
        }

    }

}