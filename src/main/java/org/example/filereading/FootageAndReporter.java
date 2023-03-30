package org.example.filereading;

import java.util.Date;

public class FootageAndReporter {
	private Journalist journalist;
	private Footage footage;
	//FootageID;Title;PublishingDate;EmployeeNumber;Lenght;CPR;First_Name;Last_Name;Street_Name;Civic_Number;City;ZIP;Country

	public FootageAndReporter(Integer footageID, String title, Date date, String employeeNumber , Integer duration, String cpr, String firstName, String lastName, String streetName, Integer civicNumber, String city ,Integer zipCode, String country) {
		journalist = new Journalist(employeeNumber, cpr, firstName, lastName, streetName, civicNumber, city , zipCode, country);
		footage = new Footage(footageID, title, date, duration);
	}
	public FootageAndReporter(Integer footageID, String title, Date date, Integer duration) {
		footage = new Footage(footageID, title, date, duration);
		journalist = null;
	}


	public Journalist getJournalist() {
		return journalist;
	}

	public Footage getFootage() {
		return footage;
	}
}
