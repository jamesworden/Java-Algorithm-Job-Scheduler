package com.jamesworden.scheduler;

public class GanttChart {

	private int time;
	private String chart;
	private String timeline;

	public GanttChart() {
		this.time = 0;
		this.chart = "|";
		this.timeline = "0";
	}

	public void addGap(int time) {
		addJob(0, time);
	}

	public void addJob(int id, int time) {

		// Ensure the job has time to it
		if (time <= 0) { return; }

		// Update time
		this.time += time;

		// Updates chart and adds a space at the end to compensate for extra timeline digits
		this.chart += " " + id +" |";
		for (int i = 0; i < String.valueOf(time).length(); i++) {
			this.chart += " ";
		}

		// Updates the timeline and adds spaces at the end to compensate for id digits
		this.timeline += " ";
		for (int i = 0; i < String.valueOf(id).length(); i++) {
			this.timeline += " ";
		}
		this.timeline += " " + this.time;
	}

	public String getChart() { return chart; }
	public String getTimeline() { return timeline; }

}
