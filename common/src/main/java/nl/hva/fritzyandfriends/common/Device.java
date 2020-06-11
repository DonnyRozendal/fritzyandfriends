package nl.hva.fritzyandfriends.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Device {

    @Id
    private Integer id;
    private String name;
    private String address;
    private String type;
    private final Timestamp timestamp;

    public Device() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Device(Integer id, String name, String address, DeviceType deviceType) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = deviceType.toString();
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}