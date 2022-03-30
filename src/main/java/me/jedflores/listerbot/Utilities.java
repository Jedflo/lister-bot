package me.jedflores.listerbot;
import okhttp3.internal.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilities {

    public void createFile(String file_name, String file_contents){
        try {
            FileWriter writer = new FileWriter(file_name);
            writer.write(file_contents);
            writer.close();
            System.out.println(file_name+"created successfully");
        }
        catch (IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public String readFile(String file_path){
        String contents = "";
        try{
            File fileToRead = new File(file_path);
            Scanner reader = new Scanner(fileToRead);
            while(reader.hasNextLine()){
                contents = reader.nextLine();
            }

        }
        catch(FileNotFoundException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return contents;
    }

    public void deleteFile(String file_path){
        File myFile = new File(file_path);
        if(myFile.delete()){
            System.out.println(myFile.getName()+" Successfully deleted");
        }
        else {
            System.out.println(myFile.getName()+" cannot be deleted");
        }
    }

    public static void main(String[] args) {
        Utilities util = new Utilities();
        //util.createFile("testing.txt","Testing testing");
        String contents = util.readFile("testing.txt");
        System.out.println(contents);
    }

}
