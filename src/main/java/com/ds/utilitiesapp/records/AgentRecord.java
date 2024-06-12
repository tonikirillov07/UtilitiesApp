package com.ds.utilitiesapp.records;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.database.DatabaseService;
import com.ds.utilitiesapp.database.tablesConstants.Agents;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.utils.settings.SettingsManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.utilitiesapp.utils.Utils.convertMdlToDollars;

public class AgentRecord extends Record{
    private String name, surname, address, telephone;
    private int personalCode;
    private double payments;

    public AgentRecord(){
        super(null, null);

    }

    public AgentRecord(String tableName, String databasePath, String name, String surname, String address, String telephone, int personalCode, double payments) {
        super(tableName, databasePath);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.telephone = telephone;
        this.personalCode = personalCode;
        this.payments = payments;
    }

    public AgentRecord(String tableName, String databasePath, long id, String name, String surname, String address, String telephone, int personalCode, double payments) {
        super(tableName, databasePath, id);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.telephone = telephone;
        this.personalCode = personalCode;
        this.payments = payments;
    }

    public static boolean findAgentWithPersonalCode(int personalCode){
        try {
            String select = "SELECT * FROM " + Agents.TABLE_NAME + " WHERE " + Agents.PERSONAL_CODE_ROW + "=" + personalCode;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isThisAgentExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return isThisAgentExists;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return false;
    }

    public double getPaymentInDollars(){
        return convertMdlToDollars(getPayments());
    }

    public void setPayments(double payments) {
        this.payments = payments;
    }

    public double getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(int personalCode) {
        this.personalCode = personalCode;
    }

    @Override
    public String toString() {
        return "AgentRecord{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", personalCode=" + personalCode +
                '}';
    }
}
