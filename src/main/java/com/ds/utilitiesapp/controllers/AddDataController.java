package com.ds.utilitiesapp.controllers;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.database.tablesConstants.Agents;
import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.database.tablesConstants.Services;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.extendsNodes.ExtendedTextField;
import com.ds.utilitiesapp.records.*;
import com.ds.utilitiesapp.utils.InputTypes;
import com.ds.utilitiesapp.utils.Utils;
import com.ds.utilitiesapp.utils.actionListeners.IOnAction;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ds.utilitiesapp.Constants.*;
import static com.ds.utilitiesapp.utils.Utils.checkPhoneNumber;
import static com.ds.utilitiesapp.utils.Utils.defaultCategoryMenuItemsAction;

public class AddDataController {
    @FXML
    private MenuButton categoryMenuButton;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button nextButton;
    private IOnAction onClose;

    @FXML
    void initialize() {
        initCategoryMenuButton();

        nextButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));
    }

    public void setOnClose(IOnAction onClose) {
        this.onClose = onClose;
    }

    private void initCategoryMenuButton() {
        try {
            MenuItem menuItemAgents = new MenuItem("Агенты");
            MenuItem menuItemCondoles = new MenuItem("Квартиры");
            MenuItem menuItemServices = new MenuItem("Услуги");

            menuItemAgents.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemAgents, categoryMenuButton);
                loadAgentComponents();
            });
            menuItemCondoles.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemCondoles, categoryMenuButton);
                loadCondolesComponents();
            });
            menuItemServices.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemServices, categoryMenuButton);
                loadServicesComponents();
            });

            categoryMenuButton.setText("Выбор");
            categoryMenuButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));
            categoryMenuButton.getItems().addAll(menuItemAgents, menuItemCondoles, menuItemServices);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadAgentComponents() {
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя агента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldSurname = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Фамилия агента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldPersonalCode = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Персональный код", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldAddress = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Адрес проживания", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldTelephone = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Телефон", Utils.getImage("images/telephone.png"));
            ExtendedTextField extendedTextFieldPayments = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Ежемесячные выплаты (руб.)", Utils.getImage("images/payments.png"));

            extendedTextFieldPersonalCode.setInputType(InputTypes.NUMERIC);

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldPayments);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldPayments});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if (AgentRecord.findAgentWithPersonalCode(Integer.parseInt(extendedTextFieldPersonalCode.getText()))) {
                        ErrorDialog.show(new IllegalArgumentException("Агент с таким кодом уже существует"));
                        return;
                    }

                    if(!checkPhoneNumber(extendedTextFieldTelephone.getText()))
                        return;

                    RecordsWriter.addAgent(new AgentRecord(Agents.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldSurname.getText(),
                            extendedTextFieldAddress.getText(), extendedTextFieldTelephone.getText(), Integer.parseInt(extendedTextFieldPersonalCode.getText()), Double.parseDouble(extendedTextFieldPayments.getText())), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static double calculateMaintenanceAmount(double square, int peopleNumber, int roomsNumber){
        return (square * ONE_QUAD_METER_COST) + (roomsNumber * ONE_ROOM_COST) + (peopleNumber * ONE_PEOPLE_COST);
    }

    public static void setMaintenanceAmountTextInTextField(@NotNull ExtendedTextField extendedTextFieldRoomsNumber, @NotNull ExtendedTextField extendedTextFieldPeopleNumber, @NotNull ExtendedTextField extendedTextFieldSquare, ExtendedTextField extendedTextFieldMaintenanceAmount){
        if(!extendedTextFieldSquare.isEmpty() & !extendedTextFieldPeopleNumber.isEmpty() & !extendedTextFieldRoomsNumber.isEmpty())
            extendedTextFieldMaintenanceAmount.setText(String.valueOf(calculateMaintenanceAmount(Double.parseDouble(extendedTextFieldSquare.getText()),
                    Integer.parseInt(extendedTextFieldPeopleNumber.getText()), Integer.parseInt(extendedTextFieldRoomsNumber.getText()))));
    }

    private void loadCondolesComponents(){
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер квартиры", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldOwnerName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя владельца", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldRoomsNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество комнат", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldPeopleNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество жильцов", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldSquare = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Площадь (м^2)", Utils.getImage("images/square.png"));
            ExtendedTextField extendedTextFieldMaintenanceAmount = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Сумма за содержание (руб.)", Utils.getImage("images/amount.png"));

            extendedTextFieldNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldRoomsNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldPeopleNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldMaintenanceAmount.getTextField().setEditable(false);

            extendedTextFieldRoomsNumber.setOnTextTyping(text -> setMaintenanceAmountTextInTextField(extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber, extendedTextFieldSquare, extendedTextFieldMaintenanceAmount));
            extendedTextFieldSquare.setOnTextTyping(text -> setMaintenanceAmountTextInTextField(extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber, extendedTextFieldSquare, extendedTextFieldMaintenanceAmount));
            extendedTextFieldPeopleNumber.setOnTextTyping(text -> setMaintenanceAmountTextInTextField(extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber, extendedTextFieldSquare, extendedTextFieldMaintenanceAmount));

            contentVbox.getChildren().addAll(extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber, extendedTextFieldSquare, extendedTextFieldMaintenanceAmount);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber, extendedTextFieldMaintenanceAmount, extendedTextFieldSquare});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if (CondolesRecord.findCondoleWithNumber(Integer.parseInt(extendedTextFieldNumber.getText()))) {
                        ErrorDialog.show(new IllegalArgumentException("Квартира с таким номером уже существует"));
                        return;
                    }

                    RecordsWriter.addCondole(new CondolesRecord(Condoles.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), Integer.parseInt(extendedTextFieldNumber.getText()),
                            extendedTextFieldOwnerName.getText(), Integer.parseInt(extendedTextFieldPeopleNumber.getText()), Integer.parseInt(extendedTextFieldRoomsNumber.getText()), Double.parseDouble(extendedTextFieldMaintenanceAmount.getText()), Double.parseDouble(extendedTextFieldSquare.getText())), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadServicesComponents(){
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя услуги", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldCondoleNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер квартиры", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldDate = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Дата услуги", Utils.getImage("images/all_symbols.png"));

            extendedTextFieldCondoleNumber.setInputType(InputTypes.NUMERIC);

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldCondoleNumber, extendedTextFieldDate);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldCondoleNumber, extendedTextFieldDate});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    RecordsWriter.addService(new ServicesRecord(Services.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldDate.getText(),
                            Integer.parseInt(extendedTextFieldCondoleNumber.getText())), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void clearContentVBox(){
        try {
            contentVbox.getChildren().clear();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

    }

    private void closeStage(){
        if(onClose != null)
            onClose.onAction();
        ((Stage) nextButton.getScene().getWindow()).close();
    }

}
