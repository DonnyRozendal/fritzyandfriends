package nl.hva.fritzyandfriends.localnetty.database;

import nl.hva.fritzyandfriends.common.InboxData;
import org.springframework.data.repository.CrudRepository;

public interface InboxRepository extends CrudRepository<InboxData, Integer> {
}
