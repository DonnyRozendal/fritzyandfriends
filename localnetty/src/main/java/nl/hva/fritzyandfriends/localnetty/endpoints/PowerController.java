package nl.hva.fritzyandfriends.localnetty.endpoints;

import nl.hva.fritzyandfriends.common.InboxData;
import nl.hva.fritzyandfriends.localnetty.database.InboxRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/power")
public class PowerController {

    private final InboxRepository inboxRepository;

    public PowerController(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    @PostMapping("/consume")
    Mono<InboxData> consumePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @PostMapping("/produce")
    Mono<InboxData> producePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @PostMapping("/store")
    Mono<InboxData> storePower(@RequestBody InboxData inboxData) {
        return Mono.just(inboxRepository.save(inboxData));
    }

    @GetMapping("/getAllInboxData")
    Mono<Iterable<InboxData>> getAllInboxData() {
        return Mono.just(inboxRepository.findAll());
    }

}