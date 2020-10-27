package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class FirstComeFirstServe extends Algorithm {

	public FirstComeFirstServe(ArrayList<Job> jobs) {
		super(jobs);
	}

	protected void iterate() {

		// Loop through each job to see which has the smallest arrival time
		Job currentJob = new Job(0, (int) Double.POSITIVE_INFINITY, 0);

		for (Job job : jobs) {
			if (job.getArrivalTime() < currentJob.getArrivalTime()) {
				currentJob = job;
			}
		}

		// If job has not arrived yet create a gap first
		if (currentJob.getArrivalTime() >= time) {
			ganttChart.addGap(currentJob.getArrivalTime() - time);
		}

		completeJob(currentJob);
	}

}
