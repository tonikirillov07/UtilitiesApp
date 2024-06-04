package com.ds.utilitiesapp.records;

public class CondolesRecord extends Record{
    private String ownerName;
    private int peopleNumber, roomsNumber, number;

    public CondolesRecord(String tableName, String databasePath, int number, String ownerName, int peopleNumber, int roomsNumber) {
        super(tableName, databasePath);
        this.number = number;
        this.ownerName = ownerName;
        this.peopleNumber = peopleNumber;
        this.roomsNumber = roomsNumber;
    }

    public CondolesRecord(String tableName, String databasePath, long id, String ownerName, int peopleNumber, int roomsNumber, int number) {
        super(tableName, databasePath, id);
        this.ownerName = ownerName;
        this.peopleNumber = peopleNumber;
        this.roomsNumber = roomsNumber;
        this.number = number;
    }

    public int getNumber() {
        return number;
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
