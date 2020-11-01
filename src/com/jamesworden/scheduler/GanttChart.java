package com.jamesworden.scheduler;

/**
 * Each algorithm class should contain its own Gantt Chart
 * Comprised of current time, a chart portion for job data, and a corresponding timeline portion
 */
public class GanttChart {

	private int time; // Total and latest time
	private String chart; // Chart portion
	private String timeline; // Timeline portion

	public GanttChart() {
		this.time = 0;
		this.chart = "Jobs:   |";
		this.timeline = "Time:   0";
	}

	/**
	 * Display a job in the gantt chart
	 * @param id Id of the job to be displayed; leave as 0 if the job is a gap
	 * @param time Duration of the job
	 */
	public void addJob(int id, int time) {
		// Ensure the job has a valid time and id
		if (time <= 0 || id < 0) {
			return;
		}
		// If job is not a gap (gaps have an id of 0), set the id to the job number
		String chartId = " ";
		if (id > 0) {
			chartId = String.valueOf(id);
		}
		// Update time
		this.time += time;

		// Create and concatenate chart and timeline strings
		this.chart += " " + chartId +" |"; // ' id |'
		this.timeline += " ";

		// Add extra spaces to the timeline portion to compensate for each extra id digits
		for (int i = 0; i < chartId.length(); i++) {
			this.timeline += " ";
		}
		this.timeline += " " + this.time;

		// Add extra spaces to the chart portion to compensate for extra timeline digits
		for (int i = 0; i < String.valueOf(this.time).length() - 1; i++) {
			this.chart += " ";
		}
	}

	public String getChart() { return chart; }
	public String getTimeline() { return timeline; }

}
