package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.Confirmation;
import nl.hva.fritzyandfriends.common.Device;
import nl.hva.fritzyandfriends.common.DeviceType;
import nl.hva.fritzyandfriends.localnetty.database.DeviceRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/all")
    Mono<Iterable<Device>> getAllDevices() {
        return Mono.just(deviceRepository.findAll());
    }

    @PostMapping("/register")
    Mono<Device> registerDevice(@RequestBody Device device) {
        return Mono.just(deviceRepository.save(device));
    }

    @PostMapping("/cheatRegister")
    Mono<Boolean> registerDevices() {
        registerDevice(new Device("Fritzy", "8082", DeviceType.CONSUMER));
        registerDevice(new Device("Batty", "8083", DeviceType.STORAGE));
        registerDevice(new Device("Sunny", "8084", DeviceType.PRODUCER));

        return Mono.just(true);
    }

}