package com.ds.utilitiesapp.records;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.database.DatabaseService;
import com.ds.utilitiesapp.database.tablesConstants.Agents;
import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.database.tablesConstants.Services;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ds.utilitiesapp.database.DatabaseConstants.ID_ROW;

public class RecordsGetter {
    @Contract(pure = true)
    private static @NotNull String getSelectRequest(String tableName){
        return "SELECT * FROM " + tableName + " ORDER BY " + ID_ROW + " ASC";
    }

    public static @Nullable List<AgentRecord> getAllAgentRecords(){
        try {
            String selectAll = getSelectRequest(Agents.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AgentRecord> agentRecords = new ArrayList<>();
            while (resultSet.next()){
                agentRecords.add(new AgentRecord(Agents.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW),
                        resultSet.getString(Agents.NAME_ROW), resultSet.getString(Agents.SURNAME_ROW), resultSet.getString(Agents.ADDRESS_ROW), resultSet.getString(Agents.TELEPHONE_ROW),
                        resultSet.getInt(Agents.PERSONAL_CODE_ROW), resultSet.getDouble(Agents.PAYMENTS_ROW)));
            }

            return agentRecords;

        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<CondolesRecord> getAllCondolesRecords(){
        try {
            String selectAll = getSelectRequest(Condoles.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CondolesRecord> condolesRecords = new ArrayList<>();
            while (resultSet.next()){
                condolesRecords.add(new CondolesRecord(Condoles.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW),
                        resultSet.getString(Condoles.OWNER_NAME_ROW), resultSet.getInt(Condoles.PEOPLE_NUMBER_ROW), resultSet.getInt(Condoles.ROOMS_NUMBER_ROW),
                        resultSet.getInt(Condoles.NUMBER_ROW), resultSet.getDouble(Condoles.MAINTENANCE_AMOUNT_ROW), resultSet.getDouble(Condoles.SQUARE_ROW)));
            }

            return condolesRecords;

        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<ServicesRecord> getAllServicesRecords(){
        try {
            String selectAll = getSelectRequest(Services.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ServicesRecord> servicesRecords = new ArrayList<>();
            while (resultSet.next()){
                servicesRecords.add(new ServicesRecord(Services.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW),
                        resultSet.getString(Services.NAME_ROW), resultSet.getString(Services.DATE_ROW), resultSet.getInt(Services.CONDOLE_NUMBER_ROW)));
            }

            return servicesRecords;

        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
