package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.GanttChart;
import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public abstract class Algorithm {

	static ArrayList<Job> jobs;
	static ArrayList<Job> finishedJobs;
	static GanttChart ganttChart;
	static int time;

	public Algorithm(ArrayList<Job> inputJobs) {
		// Initialize
		jobs = inputJobs;
		finishedJobs = new ArrayList<>();
		ganttChart = new GanttChart();
		time = 0;

		// Run algorithm until initial jobs arraylist is empty
		while (!jobs.isEmpty()) {
			iterate();
		}
	}

	/**
	 * Function that completes one iteration of the algorithm at hand until all jobs have been processed.
	 * Will be repeatedly called in the constructor.
	 */
	protected abstract void iterate();

	public void printStatistics() {
		printGanttChart();
		printTurnaroundTime();
		printWaitingTime();
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

	protected static void completeJob(Job job) {

		// Get job data
		int id = job.getId();
		int burstTime = job.getBurstTime();
		int waitingTime = time - job.getArrivalTime();
		int turnaroundTime = waitingTime + burstTime;

		// Update statistics fromd data
		ganttChart.addJob(id, burstTime);
		job.setWaitingTime(waitingTime);
		job.setTurnaroundTime(turnaroundTime);

		// Update job status of algorithm
		finishedJobs.add(job);
		jobs.remove(job);

		// Update time
		time += burstTime;

	}

}
