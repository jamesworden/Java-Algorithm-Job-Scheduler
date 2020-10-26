package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class ShortestJobFirst extends Algorithm {

	public ShortestJobFirst(ArrayList<Job> jobs) {
		super(jobs);
	}

	@Override
	protected void iterate() {

		// Loop through each job to see which job(s) has the smallest arrival time(s)
		ArrayList<Integer> arrivedJobIndexes = new ArrayList<>();
		int arrivalTime = (int) Double.POSITIVE_INFINITY;

		for (Job job : jobs) {

			if (job.getArrivalTime() < arrivalTime) { // Set the job with the new smallest arrival time
				arrivedJobIndexes.clear();
				arrivalTime = job.getArrivalTime();
			}

			if (job.getArrivalTime() <= arrivalTime) { // Add to arrived job indexes
				arrivedJobIndexes.add(jobs.indexOf(job));
			}
		}

		// Pick a job from the job indexes array list based on the shortest burst time
		Job currentJob = new Job(0, 0, (int) Double.POSITIVE_INFINITY);

		for (int index : arrivedJobIndexes) {
			if (jobs.get(index).getBurstTime() < currentJob.getBurstTime()) {
				currentJob = jobs.get(index);
			}
		}

		// Check if job can start yet
		int burstTime = currentJob.getBurstTime();
		arrivalTime = currentJob.getArrivalTime();

		// Remove job from queue
		jobs.remove(currentJob); // Since the job will be changed, it must be removed now.

		if (arrivalTime >= time) { // Next job has not arrived or has just arrived

			ganttChart.addGap(arrivalTime - time);
			time = arrivalTime;

			currentJob.setWaitingTime(0);
			currentJob.setTurnaroundTime(burstTime);

		} else { // Next job has been waiting

			int waitingTime = time - arrivalTime;
			currentJob.setWaitingTime(waitingTime);
			currentJob.setTurnaroundTime(waitingTime + burstTime);
		}

		int id = currentJob.getId();

		// Update gantt chart
		ganttChart.addJob(id, burstTime);
		time += burstTime;

		// Add job to finished jobs arraylist
		finishedJobs.add(currentJob);
	}

}
