package org.example;

import java.time.LocalDate;
import java.util.*;

// Concrete implementation of IEventInfo.
// Maintains both a List (for ordered access) and a Set (for O(1) duplicate checks).
class EventInfo implements IEventInfo {

    private final String eventName;
    private final LocalDate eventDate;
    private final int capacity;

    private boolean cancelled;

    // Lists preserve insertion order and are exposed via getRegistration() / getAttendees().
    private final List<IPerson> registration;
    private final List<IPerson> attendees;

    // Sets provide O(1) duplicate and membership checks (requires equals+hashCode on IPerson).
    private final Set<IPerson> registeredPeople;
    private final Set<IPerson> attendeePeople;

    public EventInfo(
            String eventName,
            LocalDate eventDate,
            int capacity,
            boolean cancelled) {

        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name is required");
        }

        if (eventDate == null) {
            throw new IllegalArgumentException("Event date is required");
        }

        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.capacity = capacity;
        this.cancelled = cancelled;

        this.registration = new ArrayList<>();
        this.attendees = new ArrayList<>();

        this.registeredPeople = new HashSet<>();
        this.attendeePeople = new HashSet<>();
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    // Return unmodifiable view so callers cannot mutate the internal list.
    @Override
    public List<IPerson> getRegistration() {
        return Collections.unmodifiableList(registration);
    }

    // Return unmodifiable view so callers cannot mutate the internal list.
    @Override
    public List<IPerson> getAttendees() {
        return Collections.unmodifiableList(attendees);
    }

    // Register a person only when all three business rules pass.
    @Override
    public boolean register(IPerson person) {

        if (person == null) {
            return false;
        }

        // Rule 1: Event must not be cancelled.
        if (cancelled) {
            return false;
        }

        // Rule 2: Registered count must be below capacity.
        if (registration.size() >= capacity) {
            return false;
        }

        // Rule 3: Person must not already be registered.
        if (registeredPeople.contains(person)) {
            return false;
        }

        // Both data structures must be updated together.
        registration.add(person);
        registeredPeople.add(person);

        return true;
    }

    // Mark a registered person as an attendee only when all three rules pass.
    @Override
    public boolean attend(IPerson person) {

        if (person == null) {
            return false;
        }

        // Rule 1: Event must not be cancelled.
        if (cancelled) {
            return false;
        }

        // Rule 2: Person must already be registered.
        if (!registeredPeople.contains(person)) {
            return false;
        }

        // Rule 3: Person must not already be recorded as an attendee.
        if (attendeePeople.contains(person)) {
            return false;
        }

        // Both data structures must be updated together.
        attendees.add(person);
        attendeePeople.add(person);

        return true;
    }
}
