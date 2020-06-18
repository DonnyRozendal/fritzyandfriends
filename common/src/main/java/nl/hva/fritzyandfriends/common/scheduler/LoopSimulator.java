package nl.hva.fritzyandfriends.common.scheduler;

import nl.hva.fritzyandfriends.common.InboxData;
import org.springframework.web.reactive.function.client.WebClient;

public class LoopSimulator {

    public static void main(String[] args) {
        loopFritzy();
    }

    private static void loopFritzy() {
        Scheduler.loop(() -> WebClient.create("http://localhost:8082")
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/fritzy/relayData")
                        .queryParam("kwh", 500)
                        .build())
                .retrieve()
                .bodyToMono(InboxData.class));
    }

}