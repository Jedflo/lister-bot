package me.jedflores.listerbot;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneKQuestions {
    //private static List<String> list;
    private static String INDEX_TRACK_FILE = "used-indexes.bin";
    private static List<Integer> spent_index = new ArrayList<>();

    public static void saveToFile(String filename, List<Integer> numbers){
        try {
            FileOutputStream FOS = new FileOutputStream(filename);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(numbers);
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
    public static List loadFile(String filename){
        List<Integer> numbers = null;
        try {
            FileInputStream FIS = new FileInputStream(filename);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            numbers = (List<Integer>)OIS.readObject();
            OIS.close();

        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public static List<String> loadQuestions(){
        List<String> questions_list = null;
        try {
            questions_list = Files.readAllLines(new File("Questions.txt").toPath(), Charset.defaultCharset() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions_list;
    }

    public static String getQuestion(){
        String question = "";
        // load questions from file
        List<String> list= loadQuestions();
        // initialize random number generator
        Random rand = new Random();

        spent_index = loadFile(INDEX_TRACK_FILE);


        int number = rand.nextInt(list.size());
        spent_index.add(number);
        //System.out.println("list size:" + (list.size()));
        //System.out.println("rand number chosen:" + number);
        question = list.get(number);

        saveToFile(INDEX_TRACK_FILE,spent_index);
        //System.out.println("str len:" + question.length());
        return question;
    }

    //prob with index 264, 35, 67, 240, 222, 147, 270

    public static void getAllQuestions(){
        List<String> list= loadQuestions();
        for (String questions: list) {
            System.out.println(questions);
        }
    }


    public static void main(String[] args) {

        //System.out.println(list.size());
        //getQuestion();
        //getAllQuestions();
        //String question = getQuestion();

/*        List<Integer> used_indexes = loadFile("used-indexes.bin");

        for (int x:used_indexes) {
            System.out.println(x);
        }*/




        //System.out.println(question);


    }
}
