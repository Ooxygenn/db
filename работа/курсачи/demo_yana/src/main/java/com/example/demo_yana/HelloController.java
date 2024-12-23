package com.example.demo_yana;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class HelloController {
    @FXML
    private ComboBox<String> tableChoice;

    @FXML
    private TableView<Map<String, Object>> tableView;


    private boolean isUpdating;
    private Map<String, Object> selectedRecord;
    private String idColumnName;

    @FXML
    private Button actionsButton;

    private String getUserInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ввод данных");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        dialog.setGraphic(null);
        dialog.getDialogPane().setGraphic(null);

        // Результат будет представлять собой объект Optional<String>
        return dialog.showAndWait().orElse(null);
    }
    @FXML
    private void getTyristByCategory() {
        String category = getUserInput("Введите категорию туристов:");
        if (category != null) {
            try {
                List<Map<String, String>> tyristList = getTyristByCategory(category);
                TableDisplay.showTable(tyristList);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }

    private List<Map<String, String>> getTyristByCategory(String category) throws SQLException {
        List<Map<String, String>> tyristList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTyristByCategory(?)}")) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    tyristList.add(row);
                }
            }
        }
        return tyristList;
    }

    @FXML
    private void getTyristInfoByCategory() {
        String category = getUserInput("Введите категорию туристов:");
        if (category != null) {
            try {
                List<Map<String, String>> tyristInfoList = getTyristInfoByCategory(category);
                TableDisplay.showTable(tyristInfoList);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }

    private List<Map<String, String>> getTyristInfoByCategory(String category) throws SQLException {
        List<Map<String, String>> tyristInfoList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTyristInfoByCategory(?)}")) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    tyristInfoList.add(row);
                }
            }
        }
        return tyristInfoList;
    }


    @FXML
    private void getLoadDataByDate() {
        String date = getUserInput("Введите дату в формате (гггг-мм-дд):");
        if (date != null) {
            try {
                List<Map<String, String>> loadDataList = getLoadDataByDate(date);
                TableDisplay.showTable(loadDataList);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }

    private List<Map<String, String>> getLoadDataByDate(String date) throws SQLException {
        List<Map<String, String>> loadDataList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetLoadDataByDate(?)}")) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    loadDataList.add(row);
                }
            }
        }
        return loadDataList;
    }

    @FXML
    private void getTotalTouristsWithExcursions() {
        String startDate = getUserInput("Введите начальную дату (гггг-мм-дд):");
        if (startDate != null) {
            String endDate = getUserInput("Введите конечную дату (гггг-мм-дд):");
            if (endDate != null) {
                try {
                    List<Map<String, String>> touristsWithExcursionsList = getTotalTouristsWithExcursions(startDate, endDate);
                    TableDisplay.showTable(touristsWithExcursionsList);
                } catch (SQLException e) {
                    showAlert("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }
        }
    }

    private List<Map<String, String>> getTotalTouristsWithExcursions(String startDate, String endDate) throws SQLException {
        List<Map<String, String>> touristsWithExcursionsList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTotalTouristsWithExcursions(?, ?)}")) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    touristsWithExcursionsList.add(row);
                }
            }
        }
        return touristsWithExcursionsList;
    }


    @FXML
    private void getTotalTouristsByDateRange() {
        String startDate = getUserInput("Введите начальную дату (гггг-мм-дд):");
        if (startDate != null) {
            String endDate = getUserInput("Введите конечную дату (гггг-мм-дд):");
            if (endDate != null) {
                try {
                    List<Map<String, String>> totalTouristsList = getTotalTouristsByDateRange(startDate, endDate);
                    TableDisplay.showTable(totalTouristsList);
                } catch (SQLException e) {
                    showAlert("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }
        }
    }

    private List<Map<String, String>> getTotalTouristsByDateRange(String startDate, String endDate) throws SQLException {
        List<Map<String, String>> totalTouristsList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTotalTouristsByDateRange(?, ?)}")) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    totalTouristsList.add(row);
                }
            }
        }
        return totalTouristsList;
    }

    @FXML
    private void getTouristInformation() {
        String touristId = getUserInput("Введите идентификатор туриста:");
        if (touristId != null) {
            try {
                List<Map<String, String>> touristInfoList = getTouristInformation(Integer.parseInt(touristId));
                TableDisplay.showTable(touristInfoList);
            } catch (NumberFormatException e) {
                showAlert("Некорректный идентификатор туриста.");
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }

    private List<Map<String, String>> getTouristInformation(int touristId) throws SQLException {
        List<Map<String, String>> touristInfoList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTouristInformation(?)}")) {
            stmt.setInt(1, touristId);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    touristInfoList.add(row);
                }
            }
        }
        return touristInfoList;
    }


    @FXML
    private void showExcursionAndAgencyStats() {
        try {
            List<Map<String, String>> statsList = getExcursionAndAgencyStatsFromDatabase();
            TableDisplay.showTable(statsList);
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<Map<String, String>> getExcursionAndAgencyStatsFromDatabase() throws SQLException {
        List<Map<String, String>> statsList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetExcursionAndAgencyStats()}")) {
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    statsList.add(row);
                }
            }
        }
        return statsList;
    }

    @FXML
    private void getTouristInfoByFlightId() {
        String flightId = getUserInput("Введите идентификатор рейса:");
        if (flightId != null) {
            try {
                List<Map<String, String>> touristInfoList = getTouristInfoByFlightId(Integer.parseInt(flightId));
                TableDisplay.showTable(touristInfoList);
            } catch (NumberFormatException e) {
                showAlert("Некорректный идентификатор рейса.");
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }

    private List<Map<String, String>> getTouristInfoByFlightId(int flightId) throws SQLException {
        List<Map<String, String>> touristInfoList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetTouristInfoByFlightId(?)}")) {
            stmt.setInt(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    touristInfoList.add(row);
                }
            }
        }
        return touristInfoList;
    }

    @FXML
    private void displayShopTouristPercentage() {
        try {
            List<Map<String, String>> percentageList = calculateShopTouristPercentageFromDatabase();
            TableDisplay.showTable(percentageList);
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<Map<String, String>> calculateShopTouristPercentageFromDatabase() throws SQLException {
        List<Map<String, String>> percentageList = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call CalculateShopTouristPercentage()}")) {
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(columnName, value);
                    }
                    percentageList.add(row);
                }
            }
        }
        return percentageList;
    }










    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Заполните выпадающий список названиями таблиц
        ObservableList<String> options = FXCollections.observableArrayList(
                "Tyrist",
                "Cklad",
                "ekskyrsii",
                "Zakaz_ekskyrsii",
                "Agent",
                "Pytevka",
                "Reis_samoleta",
                "Tyrist_gryppa",
                "Priem",
                "Deti",
                "Svedeniya",
                "Gostiniza",
                "Tyristy_Gostiniza",
                "Rasselenie_Gostiniza",
                "Tyristy_Rasseleniee",
                "Otdel_emigrazii",
                "Rezhenie_tamozhni"
        );




        tableChoice.setItems(options);
        actionsButton.setOnAction(event -> showActionsWindow());
    }

    private void showActionsWindow() {
        try {
            // Загрузите FXML для нового окна
            FXMLLoader loader = new FXMLLoader(getClass().getResource("actionsWindow.fxml"));
            Parent root = loader.load();

            // Создайте новое модальное окно (Stage)
            Stage actionsStage = new Stage();
            actionsStage.setTitle("Действия");
            actionsStage.setScene(new Scene(root));

            // Установите модальность окна
            actionsStage.initModality(Modality.APPLICATION_MODAL);

            // Покажите новое окно
            actionsStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showTable() {
        // Очистите TableView
        tableView.getItems().clear();
        tableView.getColumns().clear();

        // Получите выбранное название таблицы
        String tableName = tableChoice.getValue();

        // Выполните SQL-запрос для получения данных из таблицы
        String query = "SELECT * FROM " + tableName;

        try (Connection conn = MySQLConnector.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)) {

            // Получите метаданные ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Получите имя столбца ID
            idColumnName = metaData.getColumnName(1); // Добавьте это

            // Создайте столбцы для TableView
            for (int i = 2; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(columnName)));
                tableView.getColumns().add(column);
            }

            // Обновите tableView данными
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    row.put(columnName, columnValue);
                }

                tableView.getItems().add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addRecord() {
        isUpdating = false;
        selectedRecord = null;
        addOrUpdateRecord();
    }

    @FXML
    public void updateRecord() {
        isUpdating = true;
        selectedRecord = tableView.getSelectionModel().getSelectedItem();
        addOrUpdateRecord();
    }

    @FXML
    public void addOrUpdateRecord() {
        // Создайте диалоговое окно
        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle(isUpdating ? "Обновить запись" : "Добавить запись");

        // Создайте панель с полями ввода для каждого столбца
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Map<String, TextField> fields = new HashMap<>();

        for (TableColumn<Map<String, Object>, ?> column : tableView.getColumns()) {
            TextField textField = new TextField();
            if (isUpdating && selectedRecord != null) {
                textField.setText(selectedRecord.get(column.getText()).toString());
            }
            fields.put(column.getText(), textField);
            grid.add(new Label(column.getText()), 0, grid.getRowCount());
            grid.add(textField, 1, grid.getRowCount() - 1);
        }

        dialog.getDialogPane().setContent(grid);

        // Добавьте кнопки "ОК" и "Отмена"
        ButtonType okButtonType = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Конвертируйте результат в Map
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Map<String, Object> result = new HashMap<>();
                for (Map.Entry<String, TextField> field : fields.entrySet()) {
                    result.put(field.getKey(), field.getValue().getText());
                }
                return result;
            }
            return null;
        });

        // Покажите диалоговое окно и получите результат
        Optional<Map<String, Object>> result = dialog.showAndWait();

        result.ifPresent(record -> {
            // Запрос SQL для добавления или обновления записи
            String query;
            if (isUpdating) {
                // Здесь вы можете добавить код для обновления записи в базе данных
                StringBuilder sb = new StringBuilder("UPDATE " + tableChoice.getValue() + " SET ");
                for (String column : record.keySet()) {
                    if (!column.equals(idColumnName)) { // Исключите ID из обновления
                        sb.append(column + " = '" + record.get(column) + "', ");
                    }
                }
                sb.delete(sb.length() - 2, sb.length()); // Удалите последнюю запятую
                sb.append(" WHERE " + idColumnName + " = " + selectedRecord.get(idColumnName));
                query = sb.toString();
            } else {
                // Здесь вы можете добавить код для добавления записи в базе данных
                StringBuilder sb1 = new StringBuilder("INSERT INTO " + tableChoice.getValue() + " (");
                StringBuilder sb2 = new StringBuilder(") VALUES (");
                for (String column : record.keySet()) {
                    if (!column.equals(idColumnName)) { // Исключите ID из добавления
                        sb1.append(column + ", ");
                        sb2.append("'" + record.get(column) + "', ");
                    }
                }
                sb1.delete(sb1.length() - 2, sb1.length()); // Удалите последнюю запятую
                sb2.delete(sb2.length() - 2, sb2.length()); // Удалите последнюю запятую
                sb2.append(")");
                query = sb1.toString() + sb2.toString();
            }

            try (Connection conn = MySQLConnector.connect();
                 Statement stmt  = conn.createStatement()) {

                // Выполните запрос SQL
                stmt.executeUpdate(query);

                // Обновите TableView
                showTable();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });


    }

    @FXML
    public void deleteRecord() {
        // Получите выбранную запись
        Map<String, Object> selectedRecord = tableView.getSelectionModel().getSelectedItem();

        // Запрос SQL для удаления записи
        String query = "DELETE FROM " + tableChoice.getValue() + " WHERE " + idColumnName + " = " + selectedRecord.get(idColumnName);

        try (Connection conn = MySQLConnector.connect();
             Statement stmt  = conn.createStatement()) {

            // Выполните запрос SQL
            stmt.executeUpdate(query);

            // Обновите TableView
            showTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


