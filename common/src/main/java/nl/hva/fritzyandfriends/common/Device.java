package nl.hva.fritzyandfriends.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Device {

    @Id
    private String name;
    private String address;
    private String type;
    private final Timestamp timestamp;

    public Device() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Device(String name, String address, DeviceType deviceType) {
        this.name = name;
        this.address = address;
        this.type = deviceType.toString();
        this.timestamp = new Timestamp(System.currentTimeMillis());
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
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}