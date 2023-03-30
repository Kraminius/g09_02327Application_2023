package org.example.hjælpefiler;

import org.example.filereading.FootageAndReporter;

import java.io.IOException;
import java.util.List;

public class LoaderExample {

	public static void main(String[] args) {
		FootagesAndReportersLoader loader = new FootagesAndReportersLoader();
		try {
			System.out.println("loading");
			List<FootageAndReporter> footagesAndReporters = loader.loadFootagesAndReporters("src/main/Ressources/30032023updatetv3.csv");
			for(FootageAndReporter footageAndReporter : footagesAndReporters) {
				System.out.print("\tFootage: " + footageAndReporter.getFootage());
				System.out.println("\tReporter: " + footageAndReporter.getJournalist());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


