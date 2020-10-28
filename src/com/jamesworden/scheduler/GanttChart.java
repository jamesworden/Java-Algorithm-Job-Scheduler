package com.jamesworden.scheduler;

public class GanttChart {

	private int time;
	private String chart;
	private String timeline;

	public GanttChart() {
		this.time = 0;
		this.chart = "Jobs:   |";
		this.timeline = "Time:   0";
	}

	public void addGap(int time) {
		addJob(0, time);
	}

	public void addJob(int id, int time) {
		System.out.println("JOB WITH ID: " + id + " ADDED FOR " + time + " UNITS OF TIME.");

		// Ensure the job has a valid time and id
		if (time <= 0 || id < 0) {
			return;
		}

		// If job is not a gap, set the id to the job number
		String chartId = " ";
		if (id > 0) {
			chartId = String.valueOf(id);
		}

		// Update time
		this.time += time;

		// Updates chart and adds a space at the end to compensate for extra timeline digits
		this.chart += " " + chartId +" ";
		for (int i = 0; i < String.valueOf(time).length() - 1; i++) {
			this.chart += " ";
		}
		this.chart += "|";

		// Updates the timeline and adds spaces at the end to compensate for id digits
		this.timeline += " ";
		for (int i = 0; i < chartId.length(); i++) {
			this.timeline += " ";
		}
		this.timeline += " " + this.time;
	}

	public String getChart() { return chart; }
	public String getTimeline() { return timeline; }

}
