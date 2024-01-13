package uz.pdp.chat.entity;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.UUID;

public class User implements Serializable {
    private final String name;
    private final String phoneNumber;
    private final String password;
    private final UUID id = UUID.randomUUID();
    private final ZoneId zoneId;

    public User(String name, String phoneNumber, String password, ZoneId zoneId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.zoneId = zoneId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }


    public ZoneId getZoneId() {
        return zoneId;
    }


    public UUID getId() {
        return id;
    }
}
