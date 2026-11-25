package org.example;

import java.util.List;

// Interface for managing a collection of events.
public interface IEventManager {

    // Returns true if the event was added; false if name already exists.
    boolean addEvent(IEventInfo event);

    // Finds the event by name and registers the person.
    boolean register(String eventName, IPerson person);

    // Finds the event by name and marks the person as an attendee.
    boolean attend(String eventName, IPerson person);

    // Returns ["year - count", ...] for non-cancelled events, ordered by year.
    List<String> getEventCountByYears();

    // Returns ["year - totalRegistrations", ...] ordered by year.
    List<String> getEventRegistrationCountByYears();

    // Returns ["year - totalAttendees", ...] ordered by year.
    List<String> getEventAttendeesCountByYears();
}
