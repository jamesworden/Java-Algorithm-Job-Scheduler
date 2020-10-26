package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.GanttChart;
import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public abstract class Algorithm {

	ArrayList<Job> jobs;
	final ArrayList<Job> finishedJobs;
	final GanttChart ganttChart;
	int time;

	public Algorithm(ArrayList<Job> jobs) {

		// Initialize
		this.jobs = jobs;
		this.finishedJobs = new ArrayList<>();
		this.ganttChart = new GanttChart();
		this.time = 0;

		// Run algorithm until initial jobs arraylist is empty
		while (!jobs.isEmpty()) {
			iterate();
		}
	}

	protected abstract void iterate();

	public void printStatistics() {
		printGanttChart();
		printTurnaroundTime();
		printWaitingTime();
	}

	public void printGanttChart() {
		System.out.println(" - Gantt Chart: ");
		System.out.println("     + " + ganttChart.getChart());
		System.out.println("     + " + ganttChart.getTimeline());
	}

	public void printTurnaroundTime() {
		System.out.println(" - Turnaround times: ");
		for (Job job : finishedJobs) {
			System.out.println("     + Job " + job.getId() + ": " + job.getTurnaroundTime());
		}
	}

	public void printWaitingTime() {
		System.out.println(" - Waiting times: ");
		for (Job job : finishedJobs) {
			System.out.println("     + Job " + job.getId() + ": " + job.getWaitingTime());
		}
	}

}
