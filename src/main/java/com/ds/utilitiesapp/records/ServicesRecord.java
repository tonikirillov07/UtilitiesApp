package com.ds.utilitiesapp.records;

public class ServicesRecord extends Record{
    private String name, date;
    private int condoleNumber;

    public ServicesRecord(){
        super(null, null);
    }

    public ServicesRecord(String tableName, String databasePath, String name, String date, int condoleNumber) {
        super(tableName, databasePath);
        this.name = name;
        this.date = date;
        this.condoleNumber = condoleNumber;
    }

    public ServicesRecord(String tableName, String databasePath, long id, String name, String date, int condoleNumber) {
        super(tableName, databasePath, id);
        this.name = name;
        this.date = date;
        this.condoleNumber = condoleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCondoleNumber() {
        return condoleNumber;
    }

    public void setCondoleNumber(int condoleNumber) {
        this.condoleNumber = condoleNumber;
    }

    @Override
    public String toString() {
        return "ServicesRecord{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", condoleNumber=" + condoleNumber +
                '}';
    }
}
