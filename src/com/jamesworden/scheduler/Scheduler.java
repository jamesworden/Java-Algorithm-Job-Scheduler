package com.jamesworden.scheduler;

import com.jamesworden.scheduler.algorithms.FirstComeFirstServe;
import com.jamesworden.scheduler.algorithms.ShortestJobFirst;
import com.jamesworden.scheduler.algorithms.ShortestRemainingTimeFirst;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Scheduler for Lab5
 * @author James Worden
 * @version 1.0.0
 */
public class Scheduler {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to James's Scheduler!");

		// Get number of jobs
		System.out.println("Please enter the number of jobs you would like to schedule.");
		int numJobs = getPositiveInt();

		// Get burst and arrival time for each job
		ArrayList<Job> jobs = new ArrayList<>();
		for (int i = 1; i < numJobs + 1; i++) { // Starting with i = 1 to make the ID's more intuitive

			// Arrival time
			System.out.println("Please enter the arrival time for Job " + i);
			int arrivalTime = getPositiveInt();

			// Burst time
			System.out.println("Please enter the burst time for Job " + i);
			int burstTime = getPositiveInt();

			jobs.add(new Job(i, arrivalTime, burstTime));
		}

		// Print Jobs
		System.out.println("---------- Jobs ----------");
		for (Job job : jobs) {
			System.out.println("Job " + job.getId() + " - "+ job.toString());
		}

		// Get a start time to calculate time elapsed after calculations
		Long timeStarted = new Date().getTime();

		// Run and print algorithm statistics
		new FirstComeFirstServe(new ArrayList<>(jobs)).getProcessedAlgorithm().printStatistics();
		new ShortestJobFirst(new ArrayList<>(jobs)).getProcessedAlgorithm().printStatistics();
		new ShortestRemainingTimeFirst(new ArrayList<>(jobs)).getProcessedAlgorithm().printStatistics();

		// Calculate and print time elapsed
		Long timeFinished = new Date().getTime();
		long timeElapsed = timeFinished - timeStarted;

		System.out.println("Finished in: " + timeElapsed + " milliseconds!");
		System.out.println("Thank you for using James's Scheduler!");

	}

	/**
	 * Continues to request valid user input
	 * @return positive integer from scanner
	 */
	private static int getPositiveInt() {
		while (!scanner.hasNextInt()) {
			System.out.println("Please enter a positive integer.");
			scanner.next();
		}
		return Math.abs(scanner.nextInt()); // Convert negative input to positive
	}

}
