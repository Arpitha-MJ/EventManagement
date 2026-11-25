package org.example;

import java.util.Objects;

// Concrete implementation of IPerson.
// Equality is based on firstName + lastName (case-sensitive).
class Person implements IPerson {

    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    // Two Person objects with the same first and last name are treated as the same person.
    // Required so that Set<IPerson> membership checks work correctly.
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person) obj;

        return Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName);
    }

    // Must be consistent with equals: same names → same hash.
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
