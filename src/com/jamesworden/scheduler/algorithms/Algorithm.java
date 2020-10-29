package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.GanttChart;
import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

/**
 * Superclass for all algorithms
 */
public abstract class Algorithm {

	static ArrayList<Job> jobs;
	static ArrayList<Job> finishedJobs;
	static GanttChart ganttChart; // Note that the Gantt Chart has its own time variable
	static String name;
	static int time;

	public Algorithm(ArrayList<Job> inputJobs) {
		jobs = inputJobs;
		finishedJobs = new ArrayList<>();
		ganttChart = new GanttChart();
		time = 0;
	}

	/**
	 * Calls the iterate method until there are no more processed jobs
	 * @return Processed algorithm
	 */
	public Algorithm getProcessedAlgorithm () {

		// Reset job data in case data leaked from previous operations
		for (Job job : jobs) {
			job.setWaitingTime(0);
			job.setTurnaroundTime(0);
		}

		// Run algorithm until initial jobs arraylist is empty
		while (!jobs.isEmpty()) {
			// If there is one job left, complete it
			if (jobs.size() == 1) {
				completeJob(jobs.get(0));
				break;
			}
			iterate();
		}
		return this;
	}

	/**
	 * Function that completes one iteration of the algorithm at hand until all jobs have been processed.
	 * Will be repeatedly called in the constructor.
	 */
	protected abstract void iterate();

	public void printStatistics() {
		System.out.println("---------- " + name + " ----------");
		printGanttChart();
		printTurnaroundTime();
		printWaitingTime();
		System.out.println();
	}

	public static void printGanttChart() {
		System.out.println(" - Gantt Chart: ");
		System.out.println("     + " + ganttChart.getChart());
		System.out.println("     + " + ganttChart.getTimeline());
	}

	public static void printTurnaroundTime() {
		System.out.println(" - Turnaround times: ");
		for (Job job : finishedJobs) {
			System.out.println("     + Job " + job.getId() + ": " + job.getTurnaroundTime());
		}
	}

	public static void printWaitingTime() {
		System.out.println(" - Waiting times: ");
		for (Job job : finishedJobs) {
			System.out.println("     + Job " + job.getId() + ": " + job.getWaitingTime());
		}
	}

	/**
	 * Handles updating the jobs and finished jobs arraylists, current time, gantt charts, and job statistics.
	 * @param job Job to be completed
	 */
	protected static void completeJob(Job job) {

		int arrivalTime = job.getArrivalTime();

		// If job has not arrived yet create a gap first
		if (arrivalTime > time) {
			int gap = arrivalTime - time;
			ganttChart.addJob(0, gap); // A job with id 0 is interpreted as a gap
			time += gap;
		}

		// Get job data
		int id = job.getId();
		int burstTime = job.getBurstTime();
		int turnaroundTime = time - arrivalTime + burstTime;

		// Update statistics from data
		ganttChart.addJob(id, burstTime);
		job.setTurnaroundTime(turnaroundTime);

		// Update job status of algorithm
		finishedJobs.add(job);
		jobs.remove(job);

		// Update time
		time += burstTime;

		// Check if the job has been partially completed by seeing if its waiting time has been set
		int waitingTime = job.getWaitingTime();

		if (waitingTime > 0) {
			job.setWaitingTime(time - waitingTime);
			return;
		}

		// Job has not been touched before
		job.setWaitingTime(time - burstTime - job.getArrivalTime());

	}

	/**
	 * Partially completing a job with a time parameter
	 * @param job Job to be completed
	 * @param completionTime Partial time for the job to run for
	 */
	protected static void completeJob(Job job, int completionTime) {

		// Ensure this is a partial completion, not a whole one
		if (completionTime >= job.getBurstTime()) {
			completeJob(job);
			return;
		}

		// Store the original burst time in waiting time until processed at the very final job completion
		if (job.getWaitingTime() <= 0) { // If job does not have a waiting time set
			job.setWaitingTime(job.getBurstTime());
		}

		// Set partial burst and remaining time
		int partialBurstTime = job.getBurstTime() - completionTime;
		int remainingBurstTime = job.getBurstTime() - partialBurstTime;
		job.setBurstTime(remainingBurstTime);

		// Update gantt chart
		int id = job.getId();
		ganttChart.addJob(id, partialBurstTime);

		// Update time
		time += partialBurstTime;

	}

	/**
	 * @param jobList ArrayList of jobs where the one with the smallest burst time will be returned
	 * @return Job with smallest burst time from a given ArrayList
	 */
	public Job getQuickestJob(ArrayList<Job> jobList) {
		// Check if input is empty
		if (jobList.isEmpty()) {
			return null;
		}
		// Loop through given jobs and return one with quickest burst time
		Job quickestJob = new Job(0,0,(int) Double.POSITIVE_INFINITY);
		for (Job job : jobList) {
			if (job.getBurstTime() < quickestJob.getBurstTime()) {
				quickestJob = job;
			}
		}
		return quickestJob;
	}

	/**
	 * @return ArrayList of all jobs that have arrived but have not yet been completed
	 */
	public ArrayList<Job> getArrivedJobs() {
		ArrayList<Job> arrivedJobs = new ArrayList<>();
		for (Job job : jobs) {
			if (job.getArrivalTime() <= time) { // Check if job just arrived or was waiting
				arrivedJobs.add(job);
			}
		}
		return arrivedJobs;
	}

	/**
	 * @return ArrayList of all of the jobs equally close to arriving next
	 */
	public ArrayList<Job> getNextToArriveJobs() {
		ArrayList<Job> nextToArriveJobs = new ArrayList<>();

		for (Job job : jobs) {
			int arrivalTime = job.getArrivalTime();

			if (arrivalTime <= time) { // Disregard jobs that have arrived
				continue;
			}
			if (nextToArriveJobs.isEmpty()) { // Check if next to arrive jobs has any in it
				nextToArriveJobs.add(job);

			} else if (arrivalTime < nextToArriveJobs.get(0).getArrivalTime()) { // Check if job will arrive sooner
				nextToArriveJobs.clear();
				nextToArriveJobs.add(job);

			} else if (arrivalTime == nextToArriveJobs.get(0).getArrivalTime()) { // Check if job will arrive simultaneously
				nextToArriveJobs.add(job);
			}
		}
		return nextToArriveJobs;
	}

}
