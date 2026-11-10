package pl.edu.pjatk.mpr.aggregates;

import java.util.Objects;

final class AgendaEntry {
    private final String eventId, roomId;
    private final TimeSlot slot;

    AgendaEntry(String eventId, String roomId, TimeSlot slot) {
        this.eventId = text(eventId);
        this.roomId = text(roomId);
        this.slot = Objects.requireNonNull(slot);
    }

    String eventId() {
        return eventId;
    }

    String roomId() {
        return roomId;
    }

    TimeSlot slot() {
        return slot;
    }

    private static String text(String v) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Identifier cannot be blank");
        return v.trim();
    }
}
