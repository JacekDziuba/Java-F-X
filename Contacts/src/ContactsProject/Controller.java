package ContactsProject;

import ContactsProject.DataModel.Contact;
import ContactsProject.DataModel.ContactData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
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

    private ContactData data;

    // == methods ==

    public void initialize() {

        data = new ContactData();
        data.loadContacts();
        contactsTable.setItems(data.getContacts());

        contactsTable.getSelectionModel().selectFirst();
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
                data.addContact(newContact);
                data.saveContacts();
                contactsTable.getSelectionModel().select(newContact);
            }

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public void editContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Use this dialog to edit Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            DialogController editController = fxmlLoader.getController();
            Contact editedContact = contactsTable.getSelectionModel().getSelectedItem();
            editController.setContact(editedContact);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                editController.editResults(editedContact);
                data.saveContacts();
                contactsTable.getSelectionModel().select(editedContact);
            }

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public void handleDeleteContact() {
        Contact contact = contactsTable.getSelectionModel().getSelectedItem();
        data.deleteContact(contact);
        data.saveContacts();
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }
}