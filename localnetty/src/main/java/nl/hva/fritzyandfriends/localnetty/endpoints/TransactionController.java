package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.Confirmation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @PostMapping("/")
    Mono<Confirmation> postTransaction() {
        return Mono.just(new Confirmation("Transaction received."));
    }

}