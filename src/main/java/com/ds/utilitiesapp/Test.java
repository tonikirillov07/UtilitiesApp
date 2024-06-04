package com.ds.utilitiesapp;

import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.records.CondolesRecord;
import com.ds.utilitiesapp.records.RecordsWriter;
import com.ds.utilitiesapp.records.ServicesRecord;
import com.ds.utilitiesapp.utils.settings.SettingsManager;

public class Test {
    public static void main(String[] args) {
        RecordsWriter.addService(new ServicesRecord(Condoles.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), "???????? ????", "19/02/2024", 54), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
    }
}
