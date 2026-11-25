# Wayfair Machine Coding: Event Management System

## 1. Problem Statement

### What is the problem asking?

A company needs an application to manage events and generate yearly reports.

We need to design an Event Management System with three main components:

1. **Person** – represents a person who can register for and attend events.
2. **EventInfo** – represents an event and manages its registrations and attendees.
3. **EventManager** – manages multiple events and generates yearly reports.

The application should support:

- Creating people and events.
- Registering people for events.
- Marking registered people as attendees.
- Preventing duplicate registrations and attendance.
- Preventing registration when an event is cancelled or full.
- Generating yearly event, registration and attendee reports.

### What I would say to the interviewer

"I understand that we need to build an Event Management System where a company can create events, register people for those events, track attendance and generate reports grouped by year.

The system has three main components.

Person represents an individual participating in an event.

EventInfo maintains the event details, registered people and attendees. It also ensures that the registration and attendance rules are followed.

EventManager manages multiple events, handles registration and attendance requests, and generates yearly reports.

The reports should show the total number of events, registrations and attendees for each year, ordered by year."

---

## 2. Important Requirement Clarifications

Before starting, I would clarify these points with the interviewer.

| Question | Why it matters |
|---|---|
| How should we identify a person? Can two people have the same first and last name? | Clarifies person identity and duplicate handling. |
| Should cancelled events be included in yearly reports? | Clarifies which events contribute to the reports. |
| Are event names case-sensitive when checking duplicates? | Clarifies duplicate event-name rules. |
| Should events with zero registrations or attendees still appear in reports? | Clarifies report output. |

### Assumptions

Unless the interviewer specifies otherwise:

1. First name and last name together identify a person.
2. Event names are case-sensitive.
3. Cancelled events are excluded from yearly reports.
4. Events with zero registrations or attendees are included in the corresponding reports.
5. Invalid registration and attendance operations are rejected.

---

## 3. Functional Requirements

### 3.1 Person

Create a `Person` class implementing `IPerson`.

Properties:
- First name
- Last name

The constructor takes both names and initializes the properties.

### 3.2 EventInfo

Create an `EventInfo` class implementing `IEventInfo`.

| Property | Type | Description |
|---|---|---|
| eventName | String | Event name |
| eventDate | LocalDate | Event date |
| capacity | int | Maximum registration capacity |
| cancelled | boolean | Cancellation status |
| Registration | List of IPerson | Registered people |
| Attendees | List of IPerson | People who attended |

**Register(Person)**

A person can register only if:

1. The event is not cancelled.
2. The current registration count is less than capacity.
3. The person is not already registered.

**Attend(Person)**

A person can attend only if:

1. The event is not cancelled.
2. The person is already registered.
3. The person is not already marked as an attendee.

### 3.3 EventManager

Create an `EventManager` class implementing `IEventManager`.

It maintains a collection of events.

| Method | Responsibility |
|---|---|
| AddEvent | Add an event if its name does not already exist. |
| Register | Register a person for an event by its name. |
| Attend | Mark a person as an attendee for an event by its name. |
| GetEventCountByYears | Return event counts grouped by year. |
| GetEventRegistrationCountByYears | Return registration counts grouped by year. |
| GetEventAttendeesCountByYears | Return attendee counts grouped by year. |

All three reports must return a list of strings ordered by year.

---

## 4. High-Level Design

The system has three main responsibilities:

### Person
Represents an individual participating in an event.

### EventInfo
Represents an individual event and is responsible for:
- Maintaining event details.
- Maintaining registrations.
- Maintaining attendees.
- Enforcing registration rules.
- Enforcing attendance rules.

### EventManager
Responsible for:
- Managing multiple events.
- Adding events.
- Handling registration requests.
- Handling attendance requests.
- Generating yearly reports.

### Overall relationship

```text
             EventManager
                  |
           Manages Events
                  |
          -----------------
          |               |
       Event1           Event2
          |               |
      Registrations   Registrations
      Attendees       Attendees
          |               |
        People          People
```

---

## 5. Example and Dry Run

### Input data

People:
- Jane Smith
- John Doe
- Richard Roe

Events:

| Event | Year |
|---|---:|
| Event1 | 2021 |
| Event2 | 2020 |

### Actions performed

| Person | Event | Action |
|---|---|---|
| John Doe | Event1 | Register |
| John Doe | Event2 | Register |
| Richard Roe | Event1 | Register |
| Jane Smith | Event1 | Register |
| Jane Smith | Event2 | Register |
| John Doe | Event1 | Attend |
| Richard Roe | Event1 | Attend |

### Registrations

| Event | Registered people | Total |
|---|---|---:|
| Event1 (2021) | John, Richard, Jane | 3 |
| Event2 (2020) | John, Jane | 2 |

### Attendance

| Event | Attendees | Total |
|---|---|---:|
| Event1 (2021) | John, Richard | 2 |
| Event2 (2020) | None | 0 |

### Expected output

```text
Event Count:
2020 - 1
2021 - 1

Registrations:
2020 - 2
2021 - 3

Attendees:
2020 - 0
2021 - 2
```

### How I would explain the output

"There is one event in 2020 and one event in 2021.

In 2020, John and Jane registered for Event2, so the registration count is two. Nobody attended Event2, so the attendee count is zero.

In 2021, John, Jane and Richard registered for Event1, so the registration count is three. John and Richard attended, so the attendee count is two."

---

## 6. Edge Cases to Consider

| Scenario | Expected result |
|---|---|
| Register for a cancelled event | Registration rejected |
| Register when capacity is full | Registration rejected |
| Register the same person twice | Second registration rejected |
| Attend without registering | Attendance rejected |
| Attend the same event twice | Second attendance rejected |
| Add an event with a duplicate name | Event rejected |
| Register for a non-existent event | Operation rejected |
| Attend a non-existent event | Operation rejected |
| Event with zero capacity | Registration rejected |
| Event with registrations but no attendees | Attendee count is zero |

---

## 7. Possible Interviewer Follow-up Questions

### Q1. What happens if a person tries to attend without registering?

"The system should reject the attendance request because only registered people are allowed to attend."

### Q2. Can a person register for multiple events?

"Yes. There is no restriction in the given requirements preventing a person from registering for multiple events."

### Q3. Can a person attend an event without being registered?

"No. The requirements explicitly state that the person must already be registered."

### Q4. What happens when an event reaches its capacity?

"Further registration requests should be rejected. Existing registrations should remain unchanged."

### Q5. Can an event be registered for after cancellation?

"No. Registration and attendance are both rejected when the event is cancelled."

### Q6. What is the difference between registrations and attendees?

"Registration represents people who signed up for an event. Attendance represents people who actually attended. Therefore, the attendee count can be lower than the registration count."

### Q7. What should happen if an event has no attendees?

"The event should still appear in the attendee report with a count of zero, as shown in the example."

---

## 8. Final Interview Summary

If the interviewer asks me to summarize my understanding, I would say:

"The main objective is to manage events, registrations and attendance while enforcing the given business rules.

The system separates individual person details, event-specific responsibilities and overall event management.

It also generates three yearly reports: event count, registration count and attendee count.

I would ensure that registration and attendance validations are handled correctly and that the report output follows the required year-wise ordering."

