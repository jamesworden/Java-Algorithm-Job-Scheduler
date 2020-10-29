package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class FirstComeFirstServe extends Algorithm {

	public FirstComeFirstServe(ArrayList<Job> jobs) {
		super(jobs);
		name = "FCFS";
	}

	protected void iterate() {

		// Loop through jobs and completes the one with the quickest burst time
		Job firstJob = new Job(0,(int) Double.POSITIVE_INFINITY, 0);

		for (Job job : jobs) {
			if (job.getArrivalTime() < firstJob.getArrivalTime()) {
				firstJob = job;
			}
		}
		completeJob(firstJob);
	}

}
