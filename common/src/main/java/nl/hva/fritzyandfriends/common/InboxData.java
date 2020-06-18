package nl.hva.fritzyandfriends.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class InboxData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private Integer kwh;
    private final Timestamp timestamp;

    public InboxData() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public InboxData(String sender, Integer kwh) {
        this.sender = sender;
        this.kwh = kwh;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public Integer getKwh() {
        return kwh;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
