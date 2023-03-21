package org.example.hjælpefiler;

import jdk.javadoc.doclet.Reporter;

import java.util.Date;

public class FootageAndReporter {
	private final Journalist journalist;
	private final Footage footage;

	public FootageAndReporter(String title, Date date, Integer duration, Integer cpr, String firstName, String lastName, String streetName, Integer civicNumber, Integer zipCode, String country) {
		journalist = new Journalist(cpr, firstName, lastName, streetName, civicNumber, zipCode, country);
		footage = new Footage(title, date, duration);
	}

	public Journalist getJournalist() {
		return journalist;
	}

	public Footage getFootage() {
		return footage;
	}
}
