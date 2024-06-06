package com.ds.utilitiesapp;

import com.ds.utilitiesapp.controllers.AddDataController;
import com.ds.utilitiesapp.controllers.EditDataController;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.records.*;
import com.ds.utilitiesapp.records.Record;
import com.ds.utilitiesapp.utils.AnotherScenes;
import com.ds.utilitiesapp.utils.RecordsTypes;
import com.ds.utilitiesapp.utils.Utils;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
    private RecordsTypes currentRecordType;

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
            currentRecordType = RecordsTypes.SERVICE;

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
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                ServicesRecord servicesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(servicesRecord, RecordsTypes.SERVICE);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllServicesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayCondoles(){
        try{
            currentRecordType = RecordsTypes.CONDOLE;

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

            TableColumn<CondolesRecord, Double> condolesRecordSquareTableColumn = new TableColumn<>("Площадь");
            condolesRecordSquareTableColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSquare()).asObject());

            TableColumn<CondolesRecord, Double> condolesRecordMaintenanceAmountTableColumn = new TableColumn<>("Сумма за содержание");
            condolesRecordMaintenanceAmountTableColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMaintenanceAmount()).asObject());

            tableView.getColumns().addAll(condolesRecordIdTableColumn, condolesRecordNumberTableColumn, condolesRecordOwnerNameTableColumn, condolesRecordRoomsNumberTableColumn,
                    condolesRecordPeopleNumberTableColumn, condolesRecordSquareTableColumn, condolesRecordMaintenanceAmountTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                CondolesRecord data = tableView.getItems().get(cellIndex);
                openEditingScene(data, RecordsTypes.CONDOLE);
            });

            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllCondolesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private int getSelectedRowIndexFromTableView(@NotNull TableView tableView){
        try {
            ObservableList tablePositionObservableList = tableView.getSelectionModel().getSelectedCells();
            if (tablePositionObservableList.isEmpty())
                return -1;

            TablePosition tablePosition = (TablePosition) tablePositionObservableList.get(0);
            return tablePosition.getRow();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return -1;
    }

    private void displayAgents(){
        try {
            currentRecordType = RecordsTypes.AGENT;

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

            TableColumn<AgentRecord, Double> agentRecordPaymentsTableColumn = new TableColumn<>("Выплаты");
            agentRecordPaymentsTableColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPayments()).asObject());

            tableView.getColumns().addAll(agentRecordIdTableColumn, agentRecordNameTableColumn, agentRecordSurnameTableColumn, agentRecordPersonalCodeTableColumn, agentRecordAddressTableColumn,
                    agentRecordTelephoneTableColumn, agentRecordPaymentsTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                AgentRecord condolesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(condolesRecord, RecordsTypes.AGENT);
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
            Menu menuHelp = new Menu("Помощь");

            menuBar.getMenus().clear();
            menuBar.getMenus().addAll(menuFile, menuData, menuHelp);

            initMenuFile(menuFile);
            initMenuData(menuData);
            initMenuHelp(menuHelp);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuHelp(Menu menuHelp) {
        try{
            MenuItem menuItemContacts = new MenuItem("Контакты");
            menuItemContacts.setOnAction(actionEvent -> AnotherScenes.goToAnotherScene("help-view.fxml", "Контакты"));

            menuHelp.getItems().add(menuItemContacts);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuData(@NotNull Menu menuData){
        try {
            MenuItem menuItemAdd = new MenuItem("Добавить");
            menuItemAdd.setOnAction(actionEvent -> {
                AddDataController addDataController = (AddDataController) AnotherScenes.goToAnotherScene("add-data-view.fxml", "Добавить запись");
                addDataController.setOnClose(this::update);
            });

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

    private void update(){
        if(currentRecordType == null)
            return;

        switch (currentRecordType){
            case CONDOLE -> displayCondoles();
            case AGENT -> displayAgents();
            case SERVICE -> displayServices();
        }
    }

    private void saveCurrentFile(){
        File selectedFile = openSaveDialog("Выберет файл для сохранения", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            Utils.saveCurrentFile(selectedFile.getAbsolutePath());
        }
    }

    private void openEditingScene(Record record, @NotNull RecordsTypes recordsTypes){
        EditDataController editDataController = (EditDataController) AnotherScenes.goToAnotherScene("edit-data-view.fxml", "Редактирование данных");

        assert editDataController != null;
        editDataController.setOnClose(this::update);

        switch (recordsTypes){
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
