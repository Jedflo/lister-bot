package me.jedflores.listerbot;


import java.util.Date;

/*
Discord Bot task list feature requirements:
-bot must be able to create two types of list:
    -personal list, where only the user that created the list will be able to see and edit his/her list
    -universal list, where everyone in the server can edit the list. there can be multiple universal lists

-If a deadline with time is set, the bot must be able to notify the user 5 to 30 mins before deadline time

-If a deadline without time is set, the bot must be able to notify the user a day before deadline.

-countdown feature, e.g., "*Task name* X days before deadline", "*Task name* X days overdue"

-SCRUM board feature

 */

public class Task {
    private String taskName = "";
    private String description = "";
    private Date deadline = null;
    DateHandling t = new DateHandling();


    public void setTaskName(String taskName){
        this.taskName=taskName;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDeadline(String deadline){
        this.deadline=t.stringToDate(deadline);
    }

    public String getDeadline(){
        return t.dateToString(deadline);
    }






}
