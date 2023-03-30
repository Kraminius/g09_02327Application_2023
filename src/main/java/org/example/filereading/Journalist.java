package org.example.filereading;

public class Journalist {

    private final String employeeNumber;
    private final String cpr;
    private final String firstName;
    private final String lastName;
    private final String streetName;
    private final Integer civicNumber;
    private final Integer zipCode;
    private final String country;
    private final String city;

    public Journalist(String employeeNumber, String cpr, String firstName, String lastName, String streetName, Integer civicNumber, String city, Integer zipCode, String country) {
        this.employeeNumber = employeeNumber;
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.civicNumber = civicNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getCPR() { return cpr; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public Integer getCivicNumber() {
        return civicNumber;
    }

    public Integer getZIPCode() { return zipCode; }

    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }


    @Override
    public String toString() {
        final String D = ";";

        return getCPR() + D + getFirstName() + D + getLastName() + D + getStreetName() + D + getCivicNumber() + D + getZIPCode() + D + getCountry();
    }

}

