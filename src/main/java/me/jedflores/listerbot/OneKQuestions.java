package me.jedflores.listerbot;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneKQuestions {
    //private static List<String> list;
    private static String CATEGORY_PROGRESS_TRACKING = "used-indexes.bin";
    private static String QUESTION_CATEGORY = "Questions.txt";
    private static List<Integer> spent_index = new ArrayList<>();


    /**
     * method used to save a list of integers into a file
     * @param filename file path
     * @param numbers List
     */
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

    /**
     * Loads a specified file that contains a list
     * @param filename file path
     * @return List, contents of file
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
            spent_index = new ArrayList<>();
            numbers = spent_index;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return numbers;
    }

    /**
     * method that returns a list of questions
     * @return list of questions
     */
    public static List<String> loadQuestions(){
        List<String> questions_list = null;
        try {
            questions_list = Files.readAllLines(new File(QUESTION_CATEGORY).toPath(), Charset.defaultCharset() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions_list;
    }

    /**
     * used to set the question file
     * @param q_file filename
     */
    public static void setQuestionCategory(String q_file){
        QUESTION_CATEGORY = q_file;
    }

    public static String getQuestionCategory(){
        return QUESTION_CATEGORY;
    }

    public static void setCategoryProgressTracking(String savefile){
        CATEGORY_PROGRESS_TRACKING = savefile;
    }

    public static String getCategoryProgressTracking(){
        return CATEGORY_PROGRESS_TRACKING;
    }

    public static String getQuestion(){
        String question = "";
        // load questions from file
        List<String> list= loadQuestions();
        // initialize random number generator
        Random rand = new Random();
        // load global index tracker
        spent_index = loadFile(CATEGORY_PROGRESS_TRACKING);
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
        saveToFile(CATEGORY_PROGRESS_TRACKING,spent_index);
        return question;
    }


    public static void printAllQuestions(){
        List<String> list= loadQuestions();
        for (String questions: list) {
            System.out.println(questions);
        }
    }

    public static List<Integer> getUsedIndexes(){
        List<Integer> used_index = loadFile(CATEGORY_PROGRESS_TRACKING);
        return used_index;
    }

    public static void deleteIndex(int indexToDelete){
        List<Integer> used_indexes = loadFile(CATEGORY_PROGRESS_TRACKING);
        int i = used_indexes.indexOf(indexToDelete);
        if(i==-1){
            return;
        }
        used_indexes.remove(i);
        saveToFile(CATEGORY_PROGRESS_TRACKING,used_indexes);

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
