package com.jamesworden.scheduler.algorithms;

import com.jamesworden.scheduler.Job;

import java.util.ArrayList;

public class FirstComeFirstServe extends Algorithm {

	public FirstComeFirstServe(ArrayList<Job> jobs) {
		super(jobs);
	}

	@Override
	protected void iterate() {

		// Loop through each job to see which has the smallest arrival time
		Job currentJob = new Job(0, (int) Double.POSITIVE_INFINITY, 0);

		for (Job job : jobs) {
			if (job.getArrivalTime() < currentJob.getArrivalTime()) {
				currentJob = job;
			}
		}

		// Check if job can start yet
		int burstTime = currentJob.getBurstTime();
		int arrivalTime = currentJob.getArrivalTime();

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
