package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class ShortestJobFirst extends Algorithm {

	public ShortestJobFirst(ArrayList<Job> jobs) {
		super(jobs);
		name = "SJF";
	}

	protected void iterate() {
		// Categorize the remaining jobs in two groups:
		ArrayList<Job> arrivedJobs = getArrivedJobs(); // Jobs that have arrived
		ArrayList<Job> nextToArriveJobs = getNextToArriveJobs(); // Jobs that are closest to arriving

		if (arrivedJobs.isEmpty()) {
			completeJob(getQuickestJob(nextToArriveJobs));
		} else {
			completeJob(getQuickestJob(arrivedJobs));
		}
	}

}
