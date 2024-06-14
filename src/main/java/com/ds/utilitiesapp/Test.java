package com.ds.utilitiesapp;

import com.ds.utilitiesapp.database.tablesConstants.Condoles;
import com.ds.utilitiesapp.records.CondolesRecord;
import com.ds.utilitiesapp.records.RecordsWriter;
import com.ds.utilitiesapp.records.ServicesRecord;
import com.ds.utilitiesapp.utils.settings.SettingsManager;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDate.now().getDayOfMonth());
    }
}
