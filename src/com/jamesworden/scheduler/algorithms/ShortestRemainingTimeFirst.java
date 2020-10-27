package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class ShortestRemainingTimeFirst extends Algorithm {

	public ShortestRemainingTimeFirst(ArrayList<Job> jobs) {
		super(jobs);
	}

	protected void iterate() {

		// If there is one job left, complete it
		if (jobs.size() == 1) {
			completeJob(jobs.get(0));
			return;
		}

		// Categorize the remaining jobs in terms of the first smallest and second smallest arrival time
		ArrayList<Job> firstJobs = new ArrayList<Job>();
		ArrayList<Job> secondJobs = new ArrayList<Job>();

		for (Job job : jobs) {
			int arrivalTime = job.getArrivalTime();

			if (firstJobs.isEmpty()) { // Check if first group is empty
				firstJobs.add(job);

			} else if (arrivalTime < firstJobs.get(0).getArrivalTime()) { // Check if job is faster than first group
				secondJobs = firstJobs;
				firstJobs = new ArrayList<>();
				firstJobs.add(job);

			} else if (arrivalTime == firstJobs.get(0).getArrivalTime()) { // Check if job is as fast as first group
				firstJobs.add(job);

			} else if (secondJobs.isEmpty()) { // Job is slower than first group; check if second is empty
				secondJobs.add(job);

			} else if (arrivalTime < secondJobs.get(0).getArrivalTime()) { // Check if job is faster than second group
				secondJobs = new ArrayList<>();
				secondJobs.add(job);

			} else if (arrivalTime == secondJobs.get(0).getArrivalTime()) { // Check if job is as fast as second group
				secondJobs.add(job);
			}

			// Job is slower than first and second groups; it will be ignored
		}

		// Find the job with the fastest burst time of the first group
		Job fastestJob = new Job(0,0,(int) Double.POSITIVE_INFINITY);

		for (Job job: firstJobs) {
			if (job.getBurstTime() < fastestJob.getBurstTime()) {
				fastestJob = job;
			}
		}

		// If the second group is empty, execute the fastest job from above
		if (secondJobs.isEmpty()) {
			completeJob(fastestJob);
			return;
		}

		// Compare the second groups arrival time and the fastest job's burst time
		int secondGroupArrivalTime = secondJobs.get(0).getArrivalTime();
		int fastestJobBurstTime = fastestJob.getBurstTime();

		if (secondGroupArrivalTime < fastestJobBurstTime) { // The fastest job will not complete before the next arrives
			int difference = fastestJobBurstTime - secondGroupArrivalTime;
			fastestJob.setBurstTime(difference); // Partial completion
			time += difference;

		} else { // Complete fastest job
			completeJob(fastestJob);
		}

	}

}
