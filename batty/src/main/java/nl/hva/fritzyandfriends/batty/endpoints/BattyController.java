package nl.hva.fritzyandfriends.batty.endpoints;

import nl.hva.fritzyandfriends.common.InboxData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/batty")
public class BattyController {

    @PostMapping("/relayData")
    Mono<InboxData> storePower(@RequestParam Integer kwh) {
        InboxData inboxData = new InboxData("Batty", kwh);

        return WebClient
                .create("http://localhost:8081")
                .post()
                .uri("/power/store")
                .body(BodyInserters.fromValue(inboxData))
                .retrieve()
                .bodyToMono(InboxData.class);

    }
}
