package me.jedflores.listerbot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartupChecks {

    public static void OneKQuestions(){
        String tracker_directory_path = "Category Trackers";
        File tracker_directory = new File(tracker_directory_path);
        if(!Files.exists(Paths.get(tracker_directory_path))){
            tracker_directory.mkdir();
            System.out.println("directory "+tracker_directory_path+" created");
        }
        else {
            System.out.println(tracker_directory_path+" already exists");
        }

    }

    public static void main(String[] args) {
        StartupChecks.OneKQuestions();
    }

}
