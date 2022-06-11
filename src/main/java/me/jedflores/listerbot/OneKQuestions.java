package me.jedflores.listerbot;

import me.jedflores.listerbot.Tools.Utilities;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static me.jedflores.listerbot.Tools.DatabaseTools.getConnection;

public class OneKQuestions {
    //private static List<String> list;
    private static String CATEGORY_PROGRESS_TRACKING = "Category Trackers\\personality and emotions Tracker.bin"; //default
    private static String QUESTION_CATEGORY = "test categ"; //default category
    private static List<Integer> spent_index = new ArrayList<>();
    private static String TRACKER_FILE_PATH = "Category Trackers\\"+QUESTION_CATEGORY+" Tracker.data";


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
     * method that returns the list of questions
     * @return list of questions
     */
    public static List<String> loadQuestions(){
        List<String> questions_list = new ArrayList<>();
        try {
            Connection con = getConnection();
            String statement = "SELECT Question FROM questions WHERE Category=? AND Asked = ?" ;
            PreparedStatement query = con.prepareStatement(statement);
            query.setString(1,QUESTION_CATEGORY);
            query.setInt(2,0);
            ResultSet rs = query.executeQuery();
            while(rs.next()){
                questions_list.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions_list;
    }

    public static List<String> getQuestions(int asked){
        List<String> questions_list = new ArrayList<>();
        try {
            Connection con = getConnection();
            String statement = "SELECT Question FROM questions WHERE Category=? AND Asked = ?" ;
            PreparedStatement query = con.prepareStatement(statement);
            query.setString(1,QUESTION_CATEGORY);
            query.setInt(2,asked);
            ResultSet rs = query.executeQuery();
            while(rs.next()){
                questions_list.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            rs.close();
            con.close();
            query.close();

        } catch (Exception e) {
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

    /**
     * returns question category
     * @return question category
     */
    public static String getQuestionCategory(){
        return QUESTION_CATEGORY;
    }

    /**
     * used to specify where the category save file is
     * @param savefile
     */
    public static void setCategoryProgressTracking(String savefile){
        CATEGORY_PROGRESS_TRACKING = savefile;
    }

    /**
     * returns the set path of category progress tracking file
     * @return path of category progress file.
     */
    public static String getCategoryProgressTracking(){
        return CATEGORY_PROGRESS_TRACKING;
    }

    /**
     * returns a single question from the category of questions specified
     * @return question
     */
    public static String getQuestion(){
        String output= "All questions in "+QUESTION_CATEGORY+" have been asked! :)";
        try {
            Connection con = getConnection();
            String statement = "SELECT Question_ID, Question FROM questions WHERE Category=? AND Asked=? ORDER BY RAND() LIMIT 1" ;
            PreparedStatement query = con.prepareStatement(statement);
            query.setString(1,QUESTION_CATEGORY);
            query.setInt(2,0);
            ResultSet rs = query.executeQuery();

            if(rs.next()){
                try{
                    int q_id = rs.getInt(1);
                    output = rs.getString(2);
                    PreparedStatement mark_used = con.prepareStatement("UPDATE questions SET asked = 1 where Question_ID = ?");
                    mark_used.setInt(1, q_id);
                    mark_used.executeUpdate();
                    mark_used.close();
                    rs.close();
                    //access stack or create one if it does not exist
                    File tracker_file = new File(TRACKER_FILE_PATH);
                    Stack<Integer> used_questions= new Stack<>();
                    if(!tracker_file.exists()){
                        used_questions.push(q_id);
                    }
                    else {
                        ObjectInputStream ois = Utilities.readObject(TRACKER_FILE_PATH);
                        used_questions = (Stack<Integer>) ois.readObject();
                        used_questions.push(q_id);
                        ois.close();
                    }
                    Utilities.writeObject(TRACKER_FILE_PATH, used_questions);

                    //push q_id in stack
                    // for undoing of questions, we need to create a stack of question IDs
                }catch (Exception e){
                    System.out.println(e);

                }finally {
                    query.close();
                    con.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * prints out all the questions within a specified question pool
     */
    public static void printAllQuestions(){
        List<String> list= loadQuestions();
        for (String questions: list) {
            System.out.println(questions);
        }
    }

    /**
     * returns all used indexes
     * @return List of used indexes
     */
    public static List<Integer> getUsedIndexes(){
        List<Integer> used_index = loadFile(CATEGORY_PROGRESS_TRACKING);
        return used_index;
    }

    /**
     * deletes a specified used index
     * @param indexToDelete index of used index
     */
    public static void deleteIndex(int indexToDelete){
        List<Integer> used_indexes = loadFile(CATEGORY_PROGRESS_TRACKING);
        int i = used_indexes.indexOf(indexToDelete);
        if(i==-1){
            return;
        }
        used_indexes.remove(i);
        saveToFile(CATEGORY_PROGRESS_TRACKING,used_indexes);

    }

    /**
     * returns the selected question category
     *
     * @return currently selected question category
     */
    public static String getCategory(){
        String output = QUESTION_CATEGORY.replace("Question Categories\\","");
        output = output.replace(".txt","");
        return output;
    }


    public static void main(String[] args) {

        //System.out.println(list.size());
        //getQuestion();
        //getAllQuestions();


/*        List<Integer> used_indexes = loadFile("personality and emotions Tracker.bin");

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
        //loadQuestions();
        //getQuestions(0);
        System.out.println(getQuestion());
        ObjectInputStream oos = Utilities.readObject(TRACKER_FILE_PATH);
        try {
            Stack<Integer> used_questions = (Stack<Integer>) oos.readObject();
            System.out.println(used_questions);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
