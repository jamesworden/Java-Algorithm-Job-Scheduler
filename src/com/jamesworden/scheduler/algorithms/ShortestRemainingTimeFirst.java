package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class ShortestRemainingTimeFirst extends Algorithm {

	public ShortestRemainingTimeFirst(ArrayList<Job> jobs) {
		super(jobs);
	}

	@Override
	protected void iterate() {

		// Create a sorted array by arrival time

		// Get difference between next arrival time and current time

		// If difference is less than the burst time of the current one,
		// 		Set the current time equal to the next arrival time
		//		Set the original job burst time to -= that difference
		// Else complete the task and remove the job

	}

}
