package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Concrete implementation of IEventManager.
// Events list is searched linearly; event names are unique.
class EventManager implements IEventManager {

    // All events added to this manager.
    private final List<IEventInfo> events;

    public EventManager() {
        this.events = new ArrayList<>();
    }

    // Add an event only if its name is not already in the list.
    @Override
    public boolean addEvent(IEventInfo event) {

        if (event == null) {
            return false;
        }

        // Reject duplicate event names (case-sensitive).
        for (IEventInfo existingEvent : events) {
            if (existingEvent.getEventName().equals(event.getEventName())) {
                return false;
            }
        }

        events.add(event);
        return true;
    }

    // Linear search by event name; returns null if not found.
    private IEventInfo findEvent(String eventName) {

        for (IEventInfo event : events) {
            if (event.getEventName().equals(eventName)) {
                return event;
            }
        }

        return null;
    }

    // Register a person for the named event.
    // Returns false if the event does not exist or registration rules fail.
    @Override
    public boolean register(String eventName, IPerson person) {

        IEventInfo event = findEvent(eventName);

        if (event == null) {
            return false;
        }

        return event.register(person);
    }

    // Mark a person as an attendee for the named event.
    // Returns false if the event does not exist or attend rules fail.
    @Override
    public boolean attend(String eventName, IPerson person) {

        IEventInfo event = findEvent(eventName);

        if (event == null) {
            return false;
        }

        return event.attend(person);
    }

    // -------------------------------------------------------
    // YEARLY REPORT 1: EVENT COUNT
    // Returns ["year - eventCount", ...] sorted ascending by year.
    // Cancelled events are excluded.
    // -------------------------------------------------------
    @Override
    public List<String> getEventCountByYears() {

        // TreeMap keeps years sorted in ascending order automatically.
        Map<Integer, Integer> yearlyCount = new TreeMap<>();

        for (IEventInfo event : events) {

            // Cancelled events did not actually occur; skip them.
            if (event.isCancelled()) {
                continue;
            }

            int year = event.getEventDate().getYear();
            yearlyCount.put(year, yearlyCount.getOrDefault(year, 0) + 1);
        }

        return convertToReport(yearlyCount);
    }

    // -------------------------------------------------------
    // YEARLY REPORT 2: REGISTRATION COUNT
    // Returns ["year - totalRegistrations", ...] sorted by year.
    // Cancelled events are excluded.
    // -------------------------------------------------------
    @Override
    public List<String> getEventRegistrationCountByYears() {

        Map<Integer, Integer> yearlyCount = new TreeMap<>();

        for (IEventInfo event : events) {

            if (event.isCancelled()) {
                continue;
            }

            int year = event.getEventDate().getYear();
            int registrationCount = event.getRegistration().size();

            yearlyCount.put(year, yearlyCount.getOrDefault(year, 0) + registrationCount);
        }

        return convertToReport(yearlyCount);
    }

    // -------------------------------------------------------
    // YEARLY REPORT 3: ATTENDEE COUNT
    // Returns ["year - totalAttendees", ...] sorted by year.
    // Cancelled events are excluded.
    // -------------------------------------------------------
    @Override
    public List<String> getEventAttendeesCountByYears() {

        Map<Integer, Integer> yearlyCount = new TreeMap<>();

        for (IEventInfo event : events) {

            if (event.isCancelled()) {
                continue;
            }

            int year = event.getEventDate().getYear();
            int attendeeCount = event.getAttendees().size();

            yearlyCount.put(year, yearlyCount.getOrDefault(year, 0) + attendeeCount);
        }

        return convertToReport(yearlyCount);
    }

    // Convert a sorted year→count map into the required "year - count" string format.
    // e.g. {2020=1, 2021=3} → ["2020 - 1", "2021 - 3"]
    private List<String> convertToReport(Map<Integer, Integer> yearlyCount) {

        List<String> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : yearlyCount.entrySet()) {
            result.add(entry.getKey() + " - " + entry.getValue());
        }

        return result;
    }
}
