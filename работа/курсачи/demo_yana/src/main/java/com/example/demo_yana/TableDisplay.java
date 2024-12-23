package com.example.demo_yana;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class TableDisplay {
    public static void showTable(List<Map<String, String>> data) {
        Stage stage = new Stage();
        TableView<Map<String, String>> table = new TableView<>();
        if (!data.isEmpty()) {
            Map<String, String> firstRow = data.get(0);
            for (String column : firstRow.keySet()) {
                TableColumn<Map<String, String>, String> tableColumn = new TableColumn<>(column);
                tableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(column)));
                table.getColumns().add(tableColumn);
            }
        }
        ObservableList<Map<String, String>> items = FXCollections.observableArrayList(data);
        table.setItems(items);
        stage.setScene(new Scene(table));
        stage.show();
    }
}
