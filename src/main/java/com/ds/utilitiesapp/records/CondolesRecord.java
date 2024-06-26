package com.ds.utilitiesapp.records;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.database.DatabaseService;
import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.utilitiesapp.database.DatabaseConstants.ID_ROW;
import static com.ds.utilitiesapp.utils.Utils.convertMdlToDollars;

public class CondolesRecord extends Record{
    private String ownerName;
    private int peopleNumber, roomsNumber, number;
    private double maintenanceAmount, square;

    public CondolesRecord(){
        super(null, null);

    }

    public CondolesRecord(String tableName, String databasePath, int number, String ownerName, int peopleNumber, int roomsNumber, double maintenanceAmount, double square) {
        super(tableName, databasePath);
        this.number = number;
        this.ownerName = ownerName;
        this.peopleNumber = peopleNumber;
        this.roomsNumber = roomsNumber;
        this.maintenanceAmount = maintenanceAmount;
        this.square = square;
    }

    public CondolesRecord(String tableName, String databasePath, long id, String ownerName, int peopleNumber, int roomsNumber, int number, double maintenanceAmount, double square) {
        super(tableName, databasePath, id);
        this.ownerName = ownerName;
        this.peopleNumber = peopleNumber;
        this.roomsNumber = roomsNumber;
        this.number = number;
        this.maintenanceAmount = maintenanceAmount;
        this.square = square;
    }

    public int getNumber() {
        return number;
    }

    public static boolean findCondoleWithNumber(int number){
        try {
            String select = "SELECT * FROM " + Condoles.TABLE_NAME + " WHERE " + Condoles.NUMBER_ROW + "=" + number;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isThisCondoleExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return isThisCondoleExists;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return false;
    }

    public static @Nullable CondolesRecord getCondoleWithNumber(int number){
        try {
            String select = "SELECT * FROM " + Condoles.TABLE_NAME + " WHERE " + Condoles.NUMBER_ROW + "=" + number;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            CondolesRecord condolesRecord = new CondolesRecord(Condoles.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW),
                    resultSet.getString(Condoles.OWNER_NAME_ROW), resultSet.getInt(Condoles.PEOPLE_NUMBER_ROW), resultSet.getInt(Condoles.ROOMS_NUMBER_ROW), resultSet.getInt(Condoles.NUMBER_ROW),
                    resultSet.getDouble(Condoles.MAINTENANCE_AMOUNT_ROW), resultSet.getDouble(Condoles.SQUARE_ROW));

            resultSet.close();
            preparedStatement.close();

            return condolesRecord;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public double getMaintenanceAmountInDollars() {
        return convertMdlToDollars(getMaintenanceAmount());
    }

    public void setMaintenanceAmount(double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    @Override
    public String toString() {
        return "CondolesRecord{" +
                "ownerName='" + ownerName + '\'' +
                ", peopleNumber=" + peopleNumber +
                ", roomsNumber=" + roomsNumber +
                ", number=" + number +
                '}';
    }
}
