package me.jedflores.listerbot;
import okhttp3.internal.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilities {
    /***
     * method used to create a file
     * @param file_path path of the file to be created
     * @param file_contents contents of the file to be created
     */
    public static void createFile(String file_path, String file_contents){
        try {
            FileWriter writer = new FileWriter(file_path);
            writer.write(file_contents);
            writer.close();
            System.out.println(file_path+"created successfully");
        }
        catch (IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static String readFile(String file_path){
        String contents = "";
        try{
            File fileToRead = new File(file_path);
            Scanner reader = new Scanner(fileToRead);
            while(reader.hasNextLine()){
                contents += reader.nextLine()+"\n";
            }

        }
        catch(FileNotFoundException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return contents;
    }

    public static void deleteFile(String file_path){
        File myFile = new File(file_path);
        if(myFile.delete()){
            System.out.println(myFile.getName()+" Successfully deleted");
        }
        else {
            System.out.println(myFile.getName()+" cannot be deleted");
        }
    }

    public static void appendFile(String file_path, String file_append){
        try {
            File file = new File(file_path);
            FileWriter writer = new FileWriter(file, true);
            writer.write(file_append);
            writer.close();
            System.out.println("append successful");
        }
        catch (IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utilities util = new Utilities();
        //util.createFile("token.txt","Testing testing");
        //util.appendFile("token.txt","four\nfive\nsix\n");
        String contents = util.readFile("token.txt");
        System.out.println(contents);
    }


}
