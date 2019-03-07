package jacek.solutions.todolist;

import jacek.solutions.todolist.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private ListView<ToDoItem> toDoItemsView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    private List<ToDoItem> toDoItems;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Call Tim", "Call Tim at 123456", LocalDate.of(2019, Month.MARCH, 20));
        ToDoItem item2 = new ToDoItem("Call Joe", "Call Joe at 654321", LocalDate.of(2019, Month.MARCH, 21));
        ToDoItem item3 = new ToDoItem("Visit Bill", "Bill comes to Poland", LocalDate.of(2019, Month.MARCH, 22));
        ToDoItem item4 = new ToDoItem("Buy for Suzan", "It is her birthday", LocalDate.of(2019, Month.MARCH, 23));
        ToDoItem item5 = new ToDoItem("Have a drink", "Relax after busy week", LocalDate.of(2019, Month.MARCH, 24));

        toDoItems = new ArrayList<ToDoItem>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        toDoItemsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if (newValue != null) {
                    ToDoItem item = toDoItemsView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        toDoItemsView.getItems().setAll(toDoItems);
        toDoItemsView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoItemsView.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = toDoItemsView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
    }

}
