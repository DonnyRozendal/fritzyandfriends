package nl.hva.fritzyandfriends.sunny.endpoints;

import nl.hva.fritzyandfriends.common.InboxData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sunny")
public class SunnyController {

    @PostMapping("/relayData")
    Mono<InboxData> producePower(@RequestParam Integer kwh) {
        InboxData inboxData = new InboxData("Sunny", kwh);

        return WebClient.create("http://localhost:8081")
                .post()
                .uri("/power/produce")
                .body(BodyInserters.fromValue(inboxData))
                .retrieve()
                .bodyToMono(InboxData.class);
    }


}
