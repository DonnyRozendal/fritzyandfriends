package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.Device;
import nl.hva.fritzyandfriends.localnetty.database.DeviceRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/device")
public class DeviceResource {

    private final DeviceRepository deviceRepository;

    public DeviceResource(DeviceRepository deviceRepository) {
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

}