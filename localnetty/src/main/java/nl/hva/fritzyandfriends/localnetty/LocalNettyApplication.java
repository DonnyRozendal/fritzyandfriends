package nl.hva.fritzyandfriends.localnetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("nl.hva.fritzyandfriends.common")
@SpringBootApplication
public class LocalNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalNettyApplication.class, args);
    }

}