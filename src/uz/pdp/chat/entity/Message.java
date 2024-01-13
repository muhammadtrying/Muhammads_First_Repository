package uz.pdp.chat.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Message implements Serializable {
    private final UUID from;
    private final UUID to;
    private final String text;
    private final ZonedDateTime zonedDateTime;

    public Message(UUID from, UUID to, String text, ZonedDateTime zonedDateTime) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.zonedDateTime = zonedDateTime;
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
