package nl.hva.fritzyandfriends.fritzy.endpoints;

import nl.hva.fritzyandfriends.common.Device;
import nl.hva.fritzyandfriends.common.DeviceType;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/register")
public class RegisterResource {

    private final Environment environment;

    public RegisterResource(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    Mono<Device> register() {
        Device device = new Device(136, "fritzy", environment.getProperty("server.port"), DeviceType.CONSUMER);

        return WebClient.create("http://localhost:8081")
                .post()
                .uri("/device/register")
                .body(BodyInserters.fromValue(device))
                .retrieve()
                .bodyToMono(Device.class);
    }

}