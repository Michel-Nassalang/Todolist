package fr.univrouen.task;

import java.time.LocalDate;

public class SimpleTask implements TaskComponent {
	private String desc;
	private LocalDate dueDate;
	private Priority priority;
	private Integer estimdate;
	private Boolean progress;

	public SimpleTask() {}
	public SimpleTask(String desc, LocalDate dueDate, Priority priority, Integer estimdate, Boolean progress){
		this.desc = desc;
		this.dueDate = dueDate;
		this.priority = priority;
		this.estimdate = estimdate;
		this.progress = progress;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}

	@Override
	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate duaDate) {
		this.dueDate = duaDate;
	}

	@Override
	public Priority getPriority(){
		return priority;
	}

	public void setPriority(Priority priority){
		this.priority = priority;
	}

	@Override
	public Integer getEstimatedDate() {
		return estimdate;
	}

	public void setEstimatedDate(Integer estimdate) {
		this.estimdate = estimdate;
	}

	@Override
	public float getProgress(){
		return (progress == true)? 1 : 0;
	}

	public void setProgress(float progress) {
		this.progress = (progress==0 ? false : true );
	}
	
}
