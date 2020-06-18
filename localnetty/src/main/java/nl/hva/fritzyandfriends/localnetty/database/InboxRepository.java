package nl.hva.fritzyandfriends.localnetty.database;

import nl.hva.fritzyandfriends.common.InboxData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InboxRepository extends CrudRepository<InboxData, Integer> {


    Optional<InboxData> findTopBySenderOrderByTimestampDesc(String sender);
}
