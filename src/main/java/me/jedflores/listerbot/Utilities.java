package me.jedflores.listerbot;
import okhttp3.internal.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Utilities {

//    public void createFile(String file_name){
//        try{
//            File myFile = new File(file_name);
//            if(myFile.createNewFile()){
//                System.out.println(file_name+" created successfully");
//            }
//            else{
//                System.out.println(file_name+" already exist!");
//            }
//
//        }
//        catch(IOException e){
//            System.out.println("An error occurred!");
//            e.printStackTrace();
//        }
//    }
//
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




    public static void main(String[] args) {
        Utilities util = new Utilities();
        util.createFile("testing.txt","Testing testing");
    }

}
