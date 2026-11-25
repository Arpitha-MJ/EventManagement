package org.example;

import java.time.LocalDate;
import java.util.List;

// Demonstrates the full example from the problem statement.
// Expected output:
//   Event Count:
//   2020 - 1
//   2021 - 1
//   Registrations:
//   2020 - 2
//   2021 - 3
//   Attendees:
//   2020 - 0
//   2021 - 2
public class Main {

    public static void main(String[] args) {

        // --- People ---
        IPerson janeDoe   = new Person("Jane", "Smith");
        IPerson johnDoe   = new Person("John", "Doe");
        IPerson richardRoe = new Person("Richard", "Roe");

        // --- Events ---
        // Event1 is in 2021, Event2 is in 2020.
        IEventInfo event1 = new EventInfo("Event1", LocalDate.of(2021, 6, 1), 10, false);
        IEventInfo event2 = new EventInfo("Event2", LocalDate.of(2020, 6, 1), 10, false);

        // --- Manager ---
        IEventManager manager = new EventManager();
        manager.addEvent(event1);
        manager.addEvent(event2);

        // --- Registrations ---
        manager.register("Event1", johnDoe);    // John → Event1 (2021)
        manager.register("Event2", johnDoe);    // John → Event2 (2020)
        manager.register("Event1", richardRoe); // Richard → Event1 (2021)
        manager.register("Event1", janeDoe);    // Jane → Event1 (2021)
        manager.register("Event2", janeDoe);    // Jane → Event2 (2020)

        // --- Attendances ---
        manager.attend("Event1", johnDoe);    // John attended Event1
        manager.attend("Event1", richardRoe); // Richard attended Event1

        // --- Print Reports ---
        printReport("Event Count",   manager.getEventCountByYears());
        printReport("Registrations", manager.getEventRegistrationCountByYears());
        printReport("Attendees",     manager.getEventAttendeesCountByYears());
    }

    // Helper to print a labeled report section.
    private static void printReport(String label, List<String> lines) {
        System.out.println(label + ":");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
