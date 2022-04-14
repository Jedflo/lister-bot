package me.jedflores.listerbot;

import java.util.HashMap;

public class TaskList {
    private String task_list_name = "";
    private HashMap <String, Task> task_list;

    public String getTask_list_name(){
        return task_list_name;
    }

    public void setTask_list_name(String list_name){
        task_list_name=list_name;
    }



}
