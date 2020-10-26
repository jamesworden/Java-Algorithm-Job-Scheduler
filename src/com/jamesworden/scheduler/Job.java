package com.jamesworden.scheduler;

public class Job {

	private final int id;
	private final int arrivalTime;
	private final int burstTime;
	private int turnaroundTime;
	private int waitingTime;

	public Job(int id, int arrivalTime, int burstTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
	}

	public int getId() { return id; }
	public int getArrivalTime() { return arrivalTime; }
	public int getBurstTime() { return burstTime; }

	public int getTurnaroundTime() { return turnaroundTime; }
	public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }

	public int getWaitingTime() { return waitingTime; }
	public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

	public String toString() {
		return "Arrival time: " + arrivalTime + "  |  Burst time: " + burstTime;
	}
}
