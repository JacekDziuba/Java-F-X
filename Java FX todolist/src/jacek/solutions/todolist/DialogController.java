package jacek.solutions.todolist;

import jacek.solutions.todolist.datamodel.ToDoData;
import jacek.solutions.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    // == fields ==

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    // == method ==

    public ToDoItem processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        ToDoItem newItem = new ToDoItem(shortDescription, details, deadlineValue);
        ToDoData.getInstance().addToDoItem(newItem);
        return newItem;
    }

}
