package me.jedflores.listerbot.Tools;
import me.jedflores.listerbot.movie;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class Utilities implements Serializable {
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

    /***
     * reads specified file
     * @param file_path file path of the file to be read.
     * @return
     */
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

    /***
     * Deletes specified file
     * @param file_path file path of the file to be deleted
     */
    public static void deleteFile(String file_path){
        File myFile = new File(file_path);
        if(myFile.delete()){
            System.out.println(myFile.getName()+" Successfully deleted");
        }
        else {
            System.out.println(myFile.getName()+" cannot be deleted");
        }
    }

    /***
     * appends text to a specified file
     * @param file_path path of the file to be appended
     * @param file_append string that will be appended to specified file
     */
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

    /*
    ====================================Saving and Reading Objects==============================================
     */

    /***
     * saves an ArrayList<movie> into file
     * @param filename file path
     * @param movie_list movie array list
     */
    public static void saveToFile(String filename, ArrayList<movie> movie_list){
        try {
            FileOutputStream FOS = new FileOutputStream(filename);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(movie_list);
            OOS.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * reads a file that was saved using the saveToFile method.
     * @param filename path of the file to be read
     * @return Arraylist in file.
     */
    public static ArrayList loadFile(String filename){
        ArrayList<movie> movie_list = null;
        try {
            FileInputStream FIS = new FileInputStream(filename);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            movie_list = (ArrayList<movie>)OIS.readObject();
            OIS.close();

        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return movie_list;
    }

    public static void writeObject(String filename,Object object){
        File output_file = new File(filename); //close
        FileOutputStream fos; //close
        ObjectOutputStream oos; //close
        try{
            if(!output_file.exists()){
                output_file.createNewFile();
            }
            fos = new FileOutputStream(output_file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static ObjectInputStream readObject(String filename){
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
        }catch(Exception e){
            System.out.println(e);
        }
        return ois;

    }

    public static void main(String[] args) {
        Utilities util = new Utilities();
        //util.createFile("token.txt","Testing testing");
        //util.appendFile("token.txt","four\nfive\nsix\n");
        //String contents = util.readFile("token.txt");
        //System.out.println(contents);
        Stack<String> stack = new Stack<>();
        stack.push("one");
        stack.push("two");
        stack.push("three");
        util.writeObject("Category Trackers\\testing.bin",stack);
        ObjectInputStream ois = util.readObject("Category Trackers\\testing.bin");

        try {
            Stack<String> stak = (Stack<String>) ois.readObject();
            System.out.println(stak);
            stak.pop();
            System.out.println(stak);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




    }


}
