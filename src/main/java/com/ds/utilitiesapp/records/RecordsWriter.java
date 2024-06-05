package com.ds.utilitiesapp.records;

import com.ds.utilitiesapp.database.DatabaseService;
import com.ds.utilitiesapp.database.tablesConstants.Agents;
import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.database.tablesConstants.Services;
import com.ds.utilitiesapp.dialogs.ErrorDialog;

import java.sql.PreparedStatement;
import java.util.Objects;

public class RecordsWriter {
    public static void addAgent(AgentRecord agentRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Agents.TABLE_NAME + "(" + Agents.NAME_ROW + "," + Agents.SURNAME_ROW + "," + Agents.PERSONAL_CODE_ROW + "," + Agents.ADDRESS_ROW + "," + Agents.TELEPHONE_ROW + "," + Agents.PAYMENTS_ROW + ") VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, agentRecord.getName());
            preparedStatement.setString(2, agentRecord.getSurname());
            preparedStatement.setInt(3, agentRecord.getPersonalCode());
            preparedStatement.setString(4, agentRecord.getAddress());
            preparedStatement.setString(5, agentRecord.getTelephone());
            preparedStatement.setDouble(6, agentRecord.getPayments());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addCondole(CondolesRecord condolesRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Condoles.TABLE_NAME + "(" + Condoles.NUMBER_ROW + "," + Condoles.OWNER_NAME_ROW + "," + Condoles.ROOMS_NUMBER_ROW + "," + Condoles.PEOPLE_NUMBER_ROW + "," + Condoles.MAINTENANCE_AMOUNT_ROW + "," + Condoles.SQUARE_ROW + ") VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setInt(1, condolesRecord.getNumber());
            preparedStatement.setString(2, condolesRecord.getOwnerName());
            preparedStatement.setInt(3, condolesRecord.getRoomsNumber());
            preparedStatement.setInt(4, condolesRecord.getPeopleNumber());
            preparedStatement.setDouble(5, condolesRecord.getMaintenanceAmount());
            preparedStatement.setDouble(6, condolesRecord.getSquare());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addService(ServicesRecord servicesRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Services.TABLE_NAME + "(" + Services.NAME_ROW + "," + Services.CONDOLE_NUMBER_ROW + "," + Services.DATE_ROW + ") VALUES(?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, servicesRecord.getName());
            preparedStatement.setInt(2, servicesRecord.getCondoleNumber());
            preparedStatement.setString(3, servicesRecord.getDate());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
