package org.example;

import java.time.LocalDate;
import java.util.List;

// Interface for a single event.
// Register adds a person to the waitlist; attend marks them as physically present.
public interface IEventInfo {
    String getEventName();

    LocalDate getEventDate();

    int getCapacity();

    boolean isCancelled();

    // Returns the list of registered people (not modifiable).
    List<IPerson> getRegistration();

    // Returns the list of people who actually attended (not modifiable).
    List<IPerson> getAttendees();

    // Returns true if registration succeeded; false otherwise.
    boolean register(IPerson person);

    // Returns true if the attendee was recorded; false otherwise.
    boolean attend(IPerson person);
}
