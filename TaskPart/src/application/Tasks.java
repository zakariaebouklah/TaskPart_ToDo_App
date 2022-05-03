package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tasks {

	private SimpleStringProperty taskName;
	private SimpleStringProperty taskDescription;
	private SimpleStringProperty taskDetails;
	private SimpleStringProperty taskDate;
	private SimpleStringProperty taskCategory;

	public Tasks(){

	}

	public Tasks(String taskName, String taskDate, String taskDescription, String taskDetails, String taskCategory) {
		this.taskName = new SimpleStringProperty(taskName);
		this.taskDate = new SimpleStringProperty(taskDate);
		this.taskDescription = new SimpleStringProperty(taskDescription);
		this.taskDetails = new SimpleStringProperty(taskDetails);
		this.taskCategory = new SimpleStringProperty(taskCategory);
	}


	public String getTaskName() {
		return taskName.get();
	}
	public void setTaskName(String taskName) {
		this.taskName.set(taskName);
	}
	public StringProperty taskNameProperty(){
		return taskName;
	}
	//---------------------------------------------------------------------

	public String getTaskDate() {
		return taskDate.get();
	}
	public void setTaskDate(String taskDate) {
		this.taskDate.set(taskDate);
	}
	public StringProperty taskDateProperty(){
		return taskDate;
	}

	//---------------------------------------------------------------------

	public String getTaskDescription() {
		return taskDescription.get();
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription.set(taskDescription);
	}
	public StringProperty taskDescriptionProperty(){
		return taskDescription;
	}

	//---------------------------------------------------------------------

	public String getTaskDetails() {
		return taskDetails.get();
	}
	public void setTaskDetails(String taskDetails) {
		this.taskDetails.set(taskDetails);
	}
	public StringProperty taskDetailsProperty(){
		return taskDetails;
	}

	//---------------------------------------------------------------------

	public String getTaskCategory() {
		return taskCategory.get();
	}
	public void setTaskCategory(String taskCategory) {
		this.taskCategory.set(taskCategory);
	}
	public StringProperty taskCategoryProperty(){
		return taskCategory;
	}
	//---------------------------------------------------------------------

	@Override
    public String toString(){
    	return this.taskName + " | " + this.taskDescription + " | " + this.taskDetails + " | " + this.taskDate + " | " + this.taskCategory;
    }


}
