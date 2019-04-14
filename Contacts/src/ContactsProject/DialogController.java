package ContactsProject;

import ContactsProject.DataModel.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {

    // == fields ==

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea notesArea;

    // == methods ==

    public Contact processResults() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String notes = notesArea.getText().trim();

        Contact contact = new Contact(firstName, lastName, phoneNumber, notes);
        return contact;
    }

}
