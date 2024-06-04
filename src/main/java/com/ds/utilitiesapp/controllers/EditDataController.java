package com.ds.utilitiesapp.controllers;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.database.DatabaseService;
import com.ds.utilitiesapp.database.tablesConstants.Agents;
import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.database.tablesConstants.Services;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.extendsNodes.ExtendedTextField;
import com.ds.utilitiesapp.records.AgentRecord;
import com.ds.utilitiesapp.records.CondolesRecord;
import com.ds.utilitiesapp.records.ServicesRecord;
import com.ds.utilitiesapp.utils.InputTypes;
import com.ds.utilitiesapp.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class EditDataController {
    @FXML
    private Button buttonNext;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button buttonDelete;

    @FXML
    void initialize() {
        buttonNext.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));

        buttonDelete.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));
        buttonDelete.setStyle("-fx-background-color: rgb(187, 71, 71);");
    }

    private void closeStage(){
        ((Stage) buttonNext.getScene().getWindow()).close();
    }

    private void applyDeleteButton(long id, String tableName, String databasePath){
        buttonDelete.setOnAction(actionEvent -> {
            DatabaseService.deleteRecord(id, tableName, databasePath);
            closeStage();
        });
    }

    private boolean hasEmptyFields(ExtendedTextField[] extendedTextFields){
        List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(extendedTextFields);
        emptyFields.forEach(ExtendedTextField::setError);

        return !emptyFields.isEmpty();
    }

    public void loadAgent(AgentRecord agentRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя агента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldSurname = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Фамилия агента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldPersonalCode = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Персональный код", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldAddress = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Адрес проживания", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldTelephone = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Телефон", Utils.getImage("images/telephone.png"));

            extendedTextFieldPersonalCode.setInputType(InputTypes.NUMERIC);

            extendedTextFieldName.setText(agentRecord.getName());
            extendedTextFieldSurname.setText(agentRecord.getSurname());
            extendedTextFieldPersonalCode.setText(String.valueOf(agentRecord.getPersonalCode()));
            extendedTextFieldAddress.setText(agentRecord.getAddress());
            extendedTextFieldTelephone.setText(agentRecord.getTelephone());

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone);
            buttonNext.setOnAction(actionEvent -> {
                if(hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldSurname, extendedTextFieldPersonalCode, extendedTextFieldAddress, extendedTextFieldTelephone}))
                    return;

                DatabaseService.changeValue(Agents.NAME_ROW, extendedTextFieldName.getText(), agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());
                DatabaseService.changeValue(Agents.SURNAME_ROW, extendedTextFieldSurname.getText(), agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());
                DatabaseService.changeValue(Agents.PERSONAL_CODE_ROW, extendedTextFieldPersonalCode.getText(), agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());
                DatabaseService.changeValue(Agents.ADDRESS_ROW, extendedTextFieldAddress.getText(), agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());
                DatabaseService.changeValue(Agents.TELEPHONE_ROW, extendedTextFieldTelephone.getText(), agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());

                closeStage();
            });
            applyDeleteButton(agentRecord.getId(), Agents.TABLE_NAME, agentRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadCondole(CondolesRecord condolesRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер квартиры", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldOwnerName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя владельца", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldRoomsNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество комнат", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldPeopleNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Количество жильцов", Utils.getImage("images/digits.png"));

            extendedTextFieldNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldRoomsNumber.setInputType(InputTypes.NUMERIC);
            extendedTextFieldPeopleNumber.setInputType(InputTypes.NUMERIC);

            extendedTextFieldNumber.setText(String.valueOf(condolesRecord.getNumber()));
            extendedTextFieldOwnerName.setText(condolesRecord.getOwnerName());
            extendedTextFieldRoomsNumber.setText(String.valueOf(condolesRecord.getRoomsNumber()));
            extendedTextFieldPeopleNumber.setText(String.valueOf(condolesRecord.getPeopleNumber()));

            contentVbox.getChildren().addAll(extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber);
            buttonNext.setOnAction(actionEvent -> {
                if(hasEmptyFields(new ExtendedTextField[]{extendedTextFieldNumber, extendedTextFieldOwnerName, extendedTextFieldRoomsNumber, extendedTextFieldPeopleNumber}))
                    return;

                DatabaseService.changeValue(Condoles.NUMBER_ROW, extendedTextFieldNumber.getText(), condolesRecord.getId(), Condoles.TABLE_NAME, condolesRecord.getDatabasePath());
                DatabaseService.changeValue(Condoles.OWNER_NAME_ROW, extendedTextFieldOwnerName.getText(), condolesRecord.getId(), Condoles.TABLE_NAME, condolesRecord.getDatabasePath());
                DatabaseService.changeValue(Condoles.ROOMS_NUMBER_ROW, extendedTextFieldRoomsNumber.getText(), condolesRecord.getId(), Condoles.TABLE_NAME, condolesRecord.getDatabasePath());
                DatabaseService.changeValue(Condoles.PEOPLE_NUMBER_ROW, extendedTextFieldPeopleNumber.getText(), condolesRecord.getId(), Condoles.TABLE_NAME, condolesRecord.getDatabasePath());

                closeStage();
            });
            applyDeleteButton(condolesRecord.getId(), Condoles.TABLE_NAME, condolesRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadService(ServicesRecord servicesRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя услуги", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldCondoleNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер квартиры", Utils.getImage("images/digits.png"));
            ExtendedTextField extendedTextFieldDate = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Дата услуги", Utils.getImage("images/all_symbols.png"));

            extendedTextFieldCondoleNumber.setInputType(InputTypes.NUMERIC);

            extendedTextFieldName.setText(servicesRecord.getName());
            extendedTextFieldCondoleNumber.setText(String.valueOf(servicesRecord.getCondoleNumber()));
            extendedTextFieldDate.setText(servicesRecord.getDate());

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldCondoleNumber, extendedTextFieldDate);
            buttonNext.setOnAction(actionEvent -> {
                if(hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldCondoleNumber, extendedTextFieldDate}))
                    return;

                DatabaseService.changeValue(Services.NAME_ROW, extendedTextFieldName.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
                DatabaseService.changeValue(Services.CONDOLE_NUMBER_ROW, extendedTextFieldCondoleNumber.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
                DatabaseService.changeValue(Services.DATE_ROW, extendedTextFieldDate.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());

                closeStage();
            });
            applyDeleteButton(servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void clearContentVBox(){
        contentVbox.getChildren().clear();
    }

}
