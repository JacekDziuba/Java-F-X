package ContactsProject;

import ContactsProject.DataModel.Contact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    // == fields ==

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private BorderPane mainBorderPane;

    // == methods ==

    public void initialize() {

        Contact contact = new Contact("Jim", "Beam", "100", "Note");
        Contact contact2 = new Contact("Jim", "Beam", "100", "Note");
        Contact contact3 = new Contact("Jim", "Beam", "100", "Note");
        Contact contact4 = new Contact("Jim", "Beam", "100", "Note");
        contactsTable.getItems().addAll(contact, contact2, contact3, contact4);

    }

    @FXML
    public void addNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Contact");
        dialog.setHeaderText("Use this dialog to add a new Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DialogController controller = fxmlLoader.getController();
                Contact newContact = controller.processResults();
                contactsTable.getItems().add(newContact);
            }

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    }

}