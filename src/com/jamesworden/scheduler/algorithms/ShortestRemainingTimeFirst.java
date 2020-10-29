package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class ShortestRemainingTimeFirst extends Algorithm {

	public ShortestRemainingTimeFirst(ArrayList<Job> jobs) {
		super(jobs);
		name = "SRTF";
	}

	protected void iterate() {

		// Categorize the remaining jobs in two groups:
		ArrayList<Job> arrivedJobs = getArrivedJobs(); // Jobs that have arrived
		ArrayList<Job> nextToArriveJobs = getNextToArriveJobs(); // Jobs that are closest to arriving

		// If no jobs have arrived, complete the quickest next-to-arrive job
		if (arrivedJobs.isEmpty()) {
			completeJob(getQuickestJob(nextToArriveJobs));
			return;
		}
		// If all jobs have arrived, complete the quickest arrived job
		Job quickestJob = getQuickestJob(arrivedJobs);

		if (nextToArriveJobs.isEmpty()){
			completeJob(quickestJob);
			return;
		}
		// Compare the second groups arrival time and the fastest job's burst time
		int secondGroupArrivalTime = nextToArriveJobs.get(0).getArrivalTime();
		int quickestJobBurstTime = quickestJob.getBurstTime();

		// Check if fastest job will not complete before the next arrives
		if (secondGroupArrivalTime < quickestJobBurstTime) {
			int partialCompletionTime = quickestJobBurstTime - secondGroupArrivalTime;
			completeJob(quickestJob, partialCompletionTime);
			return;
		}
		// Complete fastest job entirely as the next will not interrupt it
		completeJob(quickestJob);
	}

}
