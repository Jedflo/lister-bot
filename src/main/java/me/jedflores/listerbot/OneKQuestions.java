package me.jedflores.listerbot;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneKQuestions {
    //private static List<String> list;
    private static String INDEX_TRACK_FILE = "used-indexes.bin";
    private static String QUESTION_FILE = "Questions.txt";
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

    public static List loadFile(String filename){
        List<Integer> numbers = null;
        try {
            FileInputStream FIS = new FileInputStream(filename);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            numbers = (List<Integer>)OIS.readObject();
            OIS.close();

        }catch (IOException e){
            e.printStackTrace();
            spent_index = new ArrayList<>();
            numbers = spent_index;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return numbers;
    }

    public static List<String> loadQuestions(){
        List<String> questions_list = null;
        try {
            questions_list = Files.readAllLines(new File(QUESTION_FILE).toPath(), Charset.defaultCharset() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions_list;
    }

    public static void setQuestionFile(String q_file){
        QUESTION_FILE = q_file;
    }

    public static String getQuestion(){
        String question = "";
        // load questions from file
        List<String> list= loadQuestions();
        // initialize random number generator
        Random rand = new Random();
        // load global index tracker
        spent_index = loadFile(INDEX_TRACK_FILE);
        //check if all questions have been asked
        if(spent_index.size() == list.size()){
            return "All questions have been asked :)";
        }
        // generate random index
        int number = rand.nextInt(list.size());
        //check if index has been used. repeat number generation until an unused number is generated
        while(spent_index.contains(number)) {
            number = rand.nextInt(list.size());
        }
        //get a random question from question list
        question = list.get(number);
        //add used index to used index tracker
        spent_index.add(number);
        //save used index to file
        saveToFile(INDEX_TRACK_FILE,spent_index);
        return question;
    }


    public static void printAllQuestions(){
        List<String> list= loadQuestions();
        for (String questions: list) {
            System.out.println(questions);
        }
    }

    public static List<Integer> getUsedIndexes(){
        List<Integer> used_index = loadFile(INDEX_TRACK_FILE);
        return used_index;
    }

    public static void deleteIndex(int indexToDelete){
        List<Integer> used_indexes = loadFile(INDEX_TRACK_FILE);
        int i = used_indexes.indexOf(indexToDelete);
        if(i==-1){
            return;
        }
        used_indexes.remove(i);
        saveToFile(INDEX_TRACK_FILE,used_indexes);

    }


    public static void main(String[] args) {

        //System.out.println(list.size());
        //getQuestion();
        //getAllQuestions();


/*        List<Integer> used_indexes = loadFile("used-indexes.bin");

        for (int x:used_indexes) {
            System.out.println(x);
        }*/

/*        List<Integer> used_index = getUsedIndexes();
        for (int x:used_index) {
            System.out.print(x+",");
        }

        System.out.println();
        deleteIndex(6);

        used_index = getUsedIndexes();
        for (int x:used_index) {
            System.out.print(x+",");
        }*/


/*        String question = getQuestion();
        System.out.println(question);*/



    }
}
