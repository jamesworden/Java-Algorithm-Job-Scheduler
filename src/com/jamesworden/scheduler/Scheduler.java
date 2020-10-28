package com.jamesworden.scheduler;

import com.jamesworden.scheduler.algorithms.Algorithm;
import com.jamesworden.scheduler.algorithms.FirstComeFirstServe;
import com.jamesworden.scheduler.algorithms.ShortestJobFirst;
import com.jamesworden.scheduler.algorithms.ShortestRemainingTimeFirst;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Scheduler for Lab5
 */
public class Scheduler {

	private static final Scanner scanner = new Scanner(System.in);
	private static ArrayList<Job> jobs;

	public static void main(String[] args) {

		System.out.println("Welcome to James's Scheduler!");

		// Get number of jobs
		System.out.println("Please enter the number of jobs you would like to schedule.");
		int numJobs = getPositiveInt();

		// Get burst and arrival time for each job
		jobs = new ArrayList<>();
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
		System.out.println();

		// Run and print algorithm statistics
		new FirstComeFirstServe(new ArrayList<>(jobs)).printStatistics();
		new ShortestJobFirst(new ArrayList<>(jobs)).printStatistics();
		new ShortestRemainingTimeFirst(new ArrayList<>(jobs)).printStatistics();

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
