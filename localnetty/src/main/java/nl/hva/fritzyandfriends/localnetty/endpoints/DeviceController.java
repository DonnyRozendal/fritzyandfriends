package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.Device;
import nl.hva.fritzyandfriends.common.DeviceType;
import nl.hva.fritzyandfriends.localnetty.database.DeviceRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    boolean registerDevices() {
        registerDevice(new Device(136, "fritzy", "8082", DeviceType.CONSUMER));
        registerDevice(new Device(137, "sunny", "8080", DeviceType.PRODUCER));
        registerDevice(new Device(138, "batty", "8083", DeviceType.STORAGE));

        return true;
    }

}