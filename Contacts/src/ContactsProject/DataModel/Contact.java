package ContactsProject.DataModel;

import javafx.beans.property.SimpleStringProperty;

public class Contact {

    // == fields ==

    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private final SimpleStringProperty notes = new SimpleStringProperty();

    // == constructors ==

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setNotes(notes);
    }

    // == getters and setters

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
