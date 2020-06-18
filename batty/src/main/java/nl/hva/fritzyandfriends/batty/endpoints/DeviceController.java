package nl.hva.fritzyandfriends.batty.endpoints;

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
@RequestMapping("/device")
public class DeviceController {
    private final Environment environment;

    public DeviceController(Environment environment) {
        this.environment = environment;
    }


    @GetMapping("/register")
    Mono<Device> Register() {
        Device device = new Device("Batty", environment.getProperty("server.port"), DeviceType.STORAGE);
        return WebClient.create("http://localhost:8081")
                .post()
                .uri("/device/register")
                .body(BodyInserters.fromValue(device))
                .retrieve()
                .bodyToMono(Device.class);
    }
}
