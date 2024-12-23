package com.example.demo_yana;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class ActionsWindowController {


    @FXML
    private void getOrganizations() {
        String series = getUserInput("Введите серию");
        if (series != null) {
            try {
                List<Map<String, String>> organizations = getOrganizationsBySeries(series);
                TableDisplay.showTable(organizations);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }


    private String getUserInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ввод данных");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private List<Map<String, String>> getOrganizationsBySeries(String series) throws SQLException {
        List<Map<String, String>> organizations = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetOrganizationsBySeries(?)}")) {
            stmt.setString(1, series);
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
                    organizations.add(row);
                }
            }
        }
        return organizations;
    }

    private String formatOrganization(Map<String, String> organization) {
        return organization.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
    }




    private List<Map<String, String>> getOwnerInfoByLicensePlate(String licensePlate) throws SQLException {
        List<Map<String, String>> ownerInfo = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetOwnerInfoByLicensePlate(?)}")) {
            stmt.setString(1, licensePlate);
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
                    ownerInfo.add(row);
                }
            }
        }
        return ownerInfo;
    }




   /* @FXML
    private void getCarDossier() {
        String licensePlate = getUserInput("Введите государственный номер");
        if (licensePlate != null) {
            try {
                List<Map<String, String>> vehicleDossier = getVehicleDossierByLicensePlate(licensePlate);
                TableDisplay.showTable(vehicleDossier);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }*/

    private List<Map<String, String>> getVehicleDossierByLicensePlate(String licensePlate) throws SQLException {
        List<Map<String, String>> vehicleDossier = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetVehicleDossier(?)}")) {
            stmt.setString(1, licensePlate);
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
                    vehicleDossier.add(row);
                }
            }
        }
        return vehicleDossier;
    }

    @FXML
    private void getOwnersWithoutInspection() {
        try {
            List<Map<String, String>> ownersWithoutInspection = getOwnersWithoutInspectionFromDB();
            TableDisplay.showTable(ownersWithoutInspection);
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<Map<String, String>> getOwnersWithoutInspectionFromDB() throws SQLException {
        List<Map<String, String>> ownersWithoutInspection = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetOwnersWithoutInspection()}")) {
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
                    ownersWithoutInspection.add(row);
                }
            }
        }
        return ownersWithoutInspection;
    }


 /*   @FXML
    private void getAccidentStatistics() {
        String startDate = getUserInput("Введите начальную дату (формат YYYY-MM-DD)");
        String endDate = getUserInput("Введите конечную дату (формат YYYY-MM-DD)");
        String accidentType = getUserInput("Введите тип ДТП");
        if (startDate != null && endDate != null && accidentType != null) {
            try {
                List<Map<String, String>> accidentStatistics = getAccidentStatisticsFromDB(startDate, endDate, accidentType);
                TableDisplay.showTable(accidentStatistics);
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    } */

    private List<Map<String, String>> getAccidentStatisticsFromDB(String startDate, String endDate, String accidentType) throws SQLException {
        List<Map<String, String>> accidentStatistics = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetAccidentStatistics(?, ?, ?)}")) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            stmt.setString(3, accidentType);
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
                    accidentStatistics.add(row);
                }
            }
        }
        return accidentStatistics;
    }


    @FXML
    private void getAccidentAnalysis() {
        try {
            List<List<Map<String, String>>> accidentAnalysisResults = getAccidentAnalysisResultsFromDB();
            for (List<Map<String, String>> table : accidentAnalysisResults) {
                TableDisplay.showTable(table);
            }
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<List<Map<String, String>>> getAccidentAnalysisResultsFromDB() throws SQLException {
        List<List<Map<String, String>>> accidentAnalysisResults = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetAccidentAnalysisResults()}")) {
            boolean hasResults = stmt.execute();
            while (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<Map<String, String>> table = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String value = rs.getString(i);
                            row.put(columnName, value);
                        }
                        table.add(row);
                    }
                    accidentAnalysisResults.add(table);
                }
                hasResults = stmt.getMoreResults();
            }
        }
        return accidentAnalysisResults;
    }


    @FXML
    private void getAccidentsWithDrunkDrivers() {
        try {
            List<List<Map<String, String>>> drunkDrivingStatistics = getDrunkDrivingStatisticsFromDB();
            for (List<Map<String, String>> table : drunkDrivingStatistics) {
                TableDisplay.showTable(table);
            }
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<List<Map<String, String>>> getDrunkDrivingStatisticsFromDB() throws SQLException {
        List<List<Map<String, String>>> drunkDrivingStatistics = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetDrunkDrivingStatistics()}")) {
            boolean hasResults = stmt.execute();
            while (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<Map<String, String>> table = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String value = rs.getString(i);
                            row.put(columnName, value);
                        }
                        table.add(row);
                    }
                    drunkDrivingStatistics.add(table);
                }
                hasResults = stmt.getMoreResults();
            }
        }
        return drunkDrivingStatistics;
    }

    @FXML
    private void getMissingCars() {
        try {
            List<Map<String, String>> wantedVehiclesWithOwners = getWantedVehiclesWithOwnersFromDB();
            TableDisplay.showTable(wantedVehiclesWithOwners);
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<Map<String, String>> getWantedVehiclesWithOwnersFromDB() throws SQLException {
        List<Map<String, String>> wantedVehiclesWithOwners = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetWantedVehiclesWithOwners()}")) {
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
                    wantedVehiclesWithOwners.add(row);
                }
            }
        }
        return wantedVehiclesWithOwners;
    }

    @FXML
    private void getSearchEfficiency() {
        try {
            List<Map<String, String>> searchEfficiency = getSearchEfficiencyFromDB();
            TableDisplay.showTable(searchEfficiency);
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    private List<Map<String, String>> getSearchEfficiencyFromDB() throws SQLException {
        List<Map<String, String>> searchEfficiency = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetSearchEfficiency()}")) {
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
                    searchEfficiency.add(row);
                }
            }
        }
        return searchEfficiency;
    }

    @FXML
    private void getCarThefts() {
        List<String> inputs = getUserInputs("Введите начальную дату (формат YYYY-MM-DD)", "Введите конечную дату (формат YYYY-MM-DD)");
        if (inputs != null && inputs.size() == 2) {
            String startDate = inputs.get(0);
            String endDate = inputs.get(1);
            try {
                List<List<Map<String, String>>> vehicleTheftStatistics = getVehicleTheftStatisticsFromDB(startDate, endDate);
                for (List<Map<String, String>> table : vehicleTheftStatistics) {
                    TableDisplay.showTable(table);
                }
            } catch (SQLException e) {
                showAlert("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }
    }


    private List<String> getUserInputs(String... prompts) {
        List<String> inputs = new ArrayList<>();
        for (String prompt : prompts) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ввод данных");
            dialog.setHeaderText(null);
            dialog.setContentText(prompt);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                inputs.add(result.get());
            } else {
                return null; // пользователь нажал "Отмена"
            }
        }
        return inputs;
    }
    private List<List<Map<String, String>>> getVehicleTheftStatisticsFromDB(String startDate, String endDate) throws SQLException {
        List<List<Map<String, String>>> vehicleTheftStatistics = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetVehicleTheftStatistics(?, ?)}")) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            boolean hasResults = stmt.execute();
            while (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<Map<String, String>> table = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String value = rs.getString(i);
                            row.put(columnName, value);
                        }
                        table.add(row);
                    }
                    vehicleTheftStatistics.add(table);
                }
                hasResults = stmt.getMoreResults();
            }
        }
        return vehicleTheftStatistics;
    }


   /* @FXML
    private void getTheftStatistics() {
        try {
            List<List<Map<String, String>>> vehicleTheftStatistics = getVehicleTheftStatisticsFromDB();
            for (List<Map<String, String>> table : vehicleTheftStatistics) {
                TableDisplay.showTable(table);
            }
        } catch (SQLException e) {
            showAlert("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }*/


    private List<List<Map<String, String>>> getVehicleTheftStatisticsFromDB() throws SQLException {
        List<List<Map<String, String>>> vehicleTheftStatistics = new ArrayList<>();
        try (Connection conn = MySQLConnector.connect();
             CallableStatement stmt = conn.prepareCall("{call GetVehicleTheftStatistics()}")) {
            boolean hasResults = stmt.execute();
            while (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<Map<String, String>> table = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String value = rs.getString(i);
                            row.put(columnName, value);
                        }
                        table.add(row);
                    }
                    vehicleTheftStatistics.add(table);
                }
                hasResults = stmt.getMoreResults();
            }
        }
        return vehicleTheftStatistics;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
