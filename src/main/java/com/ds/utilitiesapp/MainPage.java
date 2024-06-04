package com.ds.utilitiesapp;

import com.ds.utilitiesapp.controllers.EditDataController;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.records.*;
import com.ds.utilitiesapp.records.Record;
import com.ds.utilitiesapp.utils.AnotherScenes;
import com.ds.utilitiesapp.utils.EditTypes;
import com.ds.utilitiesapp.utils.Utils;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.ds.utilitiesapp.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.utilitiesapp.Constants.TEMPORARY_DATABASE;
import static com.ds.utilitiesapp.utils.Utils.*;

public class MainPage {
    private final Label categoryLabel;
    private final MenuBar menuBar;
    private final MenuButton categoryMenuButton;
    private final VBox mainVbox;

    public MainPage(Label categoryLabel, MenuBar menuBar, MenuButton categoryMenuButton, VBox mainVbox) {
        this.categoryLabel = categoryLabel;
        this.menuBar = menuBar;
        this.categoryMenuButton = categoryMenuButton;
        this.mainVbox = mainVbox;
    }

    public void init(){
        checkDatabaseProperty();
        initCategoryLabel();
        initMenuBar();
        initCategoryMenuButton();
    }

    private void checkDatabaseProperty() {
        if(!new File(Objects.requireNonNull(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).exists()){
            SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, TEMPORARY_DATABASE);
        }
    }

    private void initCategoryMenuButton() {
        try {
            MenuItem menuItemCondos = new MenuItem("Квартиры");
            MenuItem menuItemServices = new MenuItem("Услуги");
            MenuItem menuItemAgents = new MenuItem("Агенты");

            menuItemCondos.setOnAction(actionEvent -> {
                displayCondoles();
                defaultCategoryMenuItemsAction(menuItemCondos, categoryMenuButton);
            });
            menuItemAgents.setOnAction(actionEvent -> {
                displayAgents();
                defaultCategoryMenuItemsAction(menuItemAgents, categoryMenuButton);
            });
            menuItemServices.setOnAction(actionEvent -> {
                displayServices();
                defaultCategoryMenuItemsAction(menuItemServices, categoryMenuButton);
            });

            categoryMenuButton.setText("Выбор");
            categoryMenuButton.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));
            categoryMenuButton.getItems().clear();
            categoryMenuButton.getItems().addAll(menuItemCondos, menuItemServices, menuItemAgents);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void applySettingForTableView(@NotNull TableView tableView){
        mainVbox.getChildren().removeIf(predicate -> predicate instanceof TableView);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        VBox.setMargin(tableView, new Insets(15));

        mainVbox.getChildren().add(tableView);
    }

    private void displayServices(){
        try{
            TableView<ServicesRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<ServicesRecord, Long> servicesRecordIdTableColumn = new TableColumn<>("ID");
            servicesRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<ServicesRecord, String> servicesRecordNameTableColumn = new TableColumn<>("Название услуги");
            servicesRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<ServicesRecord, Integer> servicesRecordCondoleNumberTableColumn = new TableColumn<>("Номер квартиры");
            servicesRecordCondoleNumberTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCondoleNumber()));

            TableColumn<ServicesRecord, String> servicesRecordDateTableColumn = new TableColumn<>("Дата услуги");
            servicesRecordDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

            tableView.getColumns().addAll(servicesRecordIdTableColumn, servicesRecordNameTableColumn, servicesRecordCondoleNumberTableColumn, servicesRecordDateTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                ServicesRecord servicesRecord = tableView.getItems().get(getSelectedRowIndexFromTableView(tableView));
                openEditingScene(servicesRecord, EditTypes.SERVICE);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllServicesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayCondoles(){
        try{
            TableView<CondolesRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<CondolesRecord, Long> condolesRecordIdTableColumn = new TableColumn<>("ID");
            condolesRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<CondolesRecord, Integer> condolesRecordNumberTableColumn = new TableColumn<>("Номер квартиры");
            condolesRecordNumberTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumber()));

            TableColumn<CondolesRecord, String> condolesRecordOwnerNameTableColumn = new TableColumn<>("Имя владельца");
            condolesRecordOwnerNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));

            TableColumn<CondolesRecord, Integer> condolesRecordRoomsNumberTableColumn = new TableColumn<>("Количество комнат");
            condolesRecordRoomsNumberTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoomsNumber()));

            TableColumn<CondolesRecord, Integer> condolesRecordPeopleNumberTableColumn = new TableColumn<>("Количество жильцов");
            condolesRecordPeopleNumberTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPeopleNumber()));

            tableView.getColumns().addAll(condolesRecordIdTableColumn, condolesRecordNumberTableColumn, condolesRecordOwnerNameTableColumn, condolesRecordRoomsNumberTableColumn,
                    condolesRecordPeopleNumberTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                CondolesRecord data = tableView.getItems().get(getSelectedRowIndexFromTableView(tableView));
                openEditingScene(data, EditTypes.CONDOLE);
            });

            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllCondolesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private int getSelectedRowIndexFromTableView(@NotNull TableView tableView){
        TablePosition tablePosition = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
        return tablePosition.getRow();
    }

    private void displayAgents(){
        try {
            TableView<AgentRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<AgentRecord, Long> agentRecordIdTableColumn = new TableColumn<>("ID");
            agentRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<AgentRecord, String> agentRecordNameTableColumn = new TableColumn<>("Имя");
            agentRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<AgentRecord, String> agentRecordSurnameTableColumn = new TableColumn<>("Фамилия");
            agentRecordSurnameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

            TableColumn<AgentRecord, Integer> agentRecordPersonalCodeTableColumn = new TableColumn<>("Персональный код");
            agentRecordPersonalCodeTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPersonalCode()).asObject());

            TableColumn<AgentRecord, String> agentRecordAddressTableColumn = new TableColumn<>("Адрес");
            agentRecordAddressTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

            TableColumn<AgentRecord, String> agentRecordTelephoneTableColumn = new TableColumn<>("Телефон");
            agentRecordTelephoneTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

            tableView.getColumns().addAll(agentRecordIdTableColumn, agentRecordNameTableColumn, agentRecordSurnameTableColumn, agentRecordPersonalCodeTableColumn, agentRecordAddressTableColumn,
                    agentRecordTelephoneTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                AgentRecord condolesRecord = tableView.getItems().get(getSelectedRowIndexFromTableView(tableView));
                openEditingScene(condolesRecord, EditTypes.AGENT);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllAgentRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuBar() {
        try {
            Menu menuFile = new Menu("Файл");
            Menu menuData = new Menu("Данные");

            menuBar.getMenus().clear();
            menuBar.getMenus().addAll(menuFile, menuData);

            initMenuFile(menuFile);
            initMenuData(menuData);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuData(@NotNull Menu menuData){
        try {
            MenuItem menuItemAdd = new MenuItem("Добавить");
            menuItemAdd.setOnAction(actionEvent -> AnotherScenes.goToAnotherScene("add-data-view.fxml", "Добавить запись"));

            menuData.getItems().addAll(menuItemAdd);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuFile(@NotNull Menu menuFile){
        try {
            MenuItem menuItemSave = new MenuItem("Сохранить");
            MenuItem menuItemLoad = new MenuItem("Загрузить");

            menuItemLoad.setOnAction(actionEvent -> loadOutsideFile());
            menuItemSave.setOnAction(actionEvent -> saveCurrentFile());

            menuFile.getItems().addAll(menuItemSave, menuItemLoad);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadOutsideFile(){
        File selectedFile = openFileDialog("Выберет файл", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, selectedFile.getAbsolutePath());
        }
    }

    private void saveCurrentFile(){
        File selectedFile = openSaveDialog("Выберет файл для сохранения", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            Utils.saveCurrentFile(selectedFile.getAbsolutePath());
        }
    }

    private void openEditingScene(Record record, @NotNull EditTypes editTypes){
        EditDataController editDataController = (EditDataController) AnotherScenes.goToAnotherScene("edit-data-view.fxml", "Редактирование данных");

        assert editDataController != null;
        switch (editTypes){
            case AGENT -> editDataController.loadAgent((AgentRecord) record);
            case CONDOLE -> editDataController.loadCondole((CondolesRecord) record);
            case SERVICE -> editDataController.loadService((ServicesRecord) record);
        }
    }

    private Stage getStage(){
        return (Stage) mainVbox.getScene().getWindow();
    }

    private void initCategoryLabel() {
        try {
            categoryLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));
            categoryLabel.setTextFill(Color.WHITE);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
