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
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

import static com.ds.utilitiesapp.utils.Utils.defaultCategoryMenuItemsAction;

public class AddDataController {
    @FXML
    private MenuButton categoryMenuButton;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button nextButton;

    @FXML
    void initialize() {
        initCategoryMenuButton();

        nextButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));
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

            extendedTextFieldPersonalCode.setInputType(InputTypes.NUMERIC);

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone);
            nextButton.setOnAction(actionEvent -> {
                List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone});
                emptyFields.forEach(ExtendedTextField::setError);

                if(!emptyFields.isEmpty())
                    return;

                RecordsWriter.addAgent(new AgentRecord(Agents.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldSurname.getText(),
                        extendedTextFieldAddress.getText(), extendedTextFieldTelephone.getText(), Integer.parseInt(extendedTextFieldPersonalCode.getText())),  SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                closeStage();
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadCondolesComponents(){
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер квартиры", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldOwnerName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя владельца", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldRoomsNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество комнат", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldPeopleNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество жильцов", Utils.getImage("images/digits.png"));

            extendedTextFieldNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldRoomsNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldPeopleNumber.setInputType(InputTypes.NUMERIC);

            contentVbox.getChildren().addAll(extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber);
            nextButton.setOnAction(actionEvent -> {
                List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber});
                emptyFields.forEach(ExtendedTextField::setError);

                if(!emptyFields.isEmpty())
                    return;

                RecordsWriter.addCondole(new CondolesRecord(Condoles.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), Integer.parseInt(extendedTextFieldNumber.getText()),
                        extendedTextFieldOwnerName.getText(), Integer.parseInt(extendedTextFieldPeopleNumber.getText()), Integer.parseInt(extendedTextFieldRoomsNumber.getText())), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                closeStage();
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
                List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldCondoleNumber, extendedTextFieldDate});
                emptyFields.forEach(ExtendedTextField::setError);

                if(!emptyFields.isEmpty())
                    return;

                RecordsWriter.addService(new ServicesRecord(Services.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldDate.getText(),
                        Integer.parseInt(extendedTextFieldCondoleNumber.getText())), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                closeStage();
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
        ((Stage) nextButton.getScene().getWindow()).close();
    }

}
