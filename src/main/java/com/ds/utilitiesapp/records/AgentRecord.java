package com.ds.utilitiesapp.records;

public class AgentRecord extends Record{
    private String name, surname, address, telephone;
    private int personalCode;

    public AgentRecord(String tableName, String databasePath, String name, String surname, String address, String telephone, int personalCode) {
        super(tableName, databasePath);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.telephone = telephone;
        this.personalCode = personalCode;
    }

    public AgentRecord(String tableName, String databasePath, long id, String name, String surname, String address, String telephone, int personalCode) {
        super(tableName, databasePath, id);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.telephone = telephone;
        this.personalCode = personalCode;
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
