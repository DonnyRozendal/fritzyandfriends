package nl.hva.fritzyandfriends.localnetty.endpoints;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/power")
public class PowerResource {

    @PostMapping("/consume")
    Mono<String> consumePower() {
        return Mono.just("TODO");
    }

    @PostMapping("/produce")
    Mono<String> producePower() {
        return Mono.just("TODO");
    }

    @PostMapping("/store")
    Mono<String> storePower() {
        return Mono.just("TODO");
    }

}