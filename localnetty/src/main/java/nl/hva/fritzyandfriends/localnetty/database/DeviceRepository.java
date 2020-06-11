package nl.hva.fritzyandfriends.localnetty.database;

import nl.hva.fritzyandfriends.common.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}