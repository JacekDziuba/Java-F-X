package jacek.solutions.todolist.datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class ToDoData {

    // == fields ==

    private static ToDoData instance = new ToDoData();
    private static String filename = "ToDoListItems.txt";

    private List<ToDoItem> toDoItems;
    private DateTimeFormatter formatter;

    // == private constructor ==

    private ToDoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    // == getters and setters

    public static ToDoData getInstance() {
        return instance;
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    // == methods ==

    public void loadToDoItems() throws IOException {

        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = reader.readLine()) != null) {
                String[] strings = input.split("\t");

                String shortDescription = strings[0];
                String details = strings[1];
                String dateString = strings[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(toDoItem);
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void storeToDoItems() throws IOException{

        Path path = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(path);

        try {
            Iterator<ToDoItem> iterator = toDoItems.iterator();
            while (iterator.hasNext()) {
                ToDoItem item = iterator.next();
                writer.write(String.format("%s\t%s\t%s",
                                            item.getShortDescription(),
                                            item.getDetails(),
                                            item.getDeadline().format(formatter)));
                writer.newLine();
            }

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void addToDoItem(ToDoItem item) {
        toDoItems.add(item);
    }
}
