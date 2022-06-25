package me.jedflores.listerbot;

import me.jedflores.listerbot.Tools.Utilities;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * returns list of questions from questions database
     * @param asked 0 for unasked questions and 1 for asked questions
     * @return List of questions
     */
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
                //System.out.println(rs.getString(1));
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

    public static String getTrackerFilePath(){return TRACKER_FILE_PATH;}

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

    public static String getAnyQuestion(){
        String output = "";

        try {
            //get row of any question from database
            Connection con = getConnection();
            PreparedStatement random_question = con.prepareStatement("SELECT * FROM questions WHERE Asked=? ORDER BY RAND() LIMIT 1");
            random_question.setInt(1,0);
            ResultSet rs = random_question.executeQuery();

            while(rs.next()){
                //get category from query to set the category in lister. this is so we can easily access the tracker file
                String random_question_category = rs.getString("Category");
                System.out.println("Question Category: " +random_question_category);
                output += "This question is from the category **"+random_question_category+"**:\n\n";
                setQuestionCategory(random_question_category);

                //mark the selected random question as used by pushing it in the track file of its category and updating the Asked field in the database.
                //database update
                int random_question_ID = rs.getInt("Question_ID");
                PreparedStatement mark_rand_question_used = con.prepareStatement("UPDATE questions SET Asked = 1 WHERE Question_ID=?");
                mark_rand_question_used.setInt(1,random_question_ID);
                mark_rand_question_used.executeUpdate();
                mark_rand_question_used.close();

                //tracker file update
                File tracker_file = new File(TRACKER_FILE_PATH);
                Stack<Integer> used_questions= new Stack<>();
                if(!tracker_file.exists()){
                    used_questions.push(random_question_ID);
                }
                else {
                    ObjectInputStream ois = Utilities.readObject(TRACKER_FILE_PATH);
                    used_questions = (Stack<Integer>) ois.readObject();
                    used_questions.push(random_question_ID);
                    ois.close();
                }
                Utilities.writeObject(TRACKER_FILE_PATH, used_questions);


                output += rs.getString("Question");
            }
            random_question.close();
            rs.close();
            con.close();
            //update fetched question as asked and push it to tracker file
            //return selected random question from database

        } catch (Exception e) {
            e.printStackTrace();
        }


        return output;
    }

    /**
     * returns the selected question category
     *
     * @return currently selected question category
     */
    public static List<String> getCategories(){
        List<String> output = new ArrayList<>();
        try {
            Connection con = getConnection();
            String statement = "SELECT DISTINCT Category FROM questions";
            PreparedStatement query = con.prepareStatement(statement);
            ResultSet rs = query.executeQuery();
            while (rs.next()){
                output.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * marks the latest asked question as unasked.
     * @return the question marked as unasked.
     */
    public static String undoLatestQuestion(){
        String undo_out = "";
        try {
            //access category stack
            ObjectInputStream ois = Utilities.readObject(getTrackerFilePath());
            Stack<Integer> tracker_stack = (Stack<Integer>) ois.readObject();
            // if category stack does not exist, return "no more questions to undo"
            if(tracker_stack.isEmpty()){
                undo_out = "no more questions to undo.";
            }
            else{
                // else pop from stack which will return question id,
                int undo_qid = tracker_stack.pop();
                // save stack to file
                Utilities.writeObject(getTrackerFilePath(),tracker_stack);
                // search for question using ID in database. for user information purposes
                Connection con = getConnection();
                PreparedStatement query_find_question = con.prepareStatement("SELECT Question FROM questions WHERE Question_ID = ?");
                query_find_question.setInt(1, undo_qid);
                ResultSet rs = query_find_question.executeQuery();
                rs.next();
                // store question to be restored. for print later
                String restored_question = rs.getString(1);
                rs.close();
                query_find_question.close();
                // update record of question to be restored: set Asked to 0 using Question ID to find the record.
                PreparedStatement query_undo = con.prepareStatement("UPDATE questions SET Asked = 0 where Question_ID = ?");
                query_undo.setInt(1, undo_qid);
                query_undo.executeUpdate();
                query_undo.close();
                con.close();
                // return question "question" restored to pool of questions
                undo_out = "question: *\""+restored_question+"\"* restored to pool of questions";
            }
            ois.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return undo_out;
    }


    public static String listCategories(){
        String category_list = "**Categories**\n";
        int asked;
        int unasked;
        int total_questions;
        //get categories
        List<String> categories = getCategories();
        //for each category get asked and unasked questions
        for (String category:categories) {
            System.out.println(category);
            setQuestionCategory(category);
            asked = getQuestions(1).size();
            System.out.println("no. of asked questions: "+asked);
            unasked = getQuestions(0).size();
            total_questions = asked+unasked;
            System.out.println("total no. of questions: "+total_questions+"\n");
            //if category asked == asked+unasked then cross out category then += it to category_list. format of string to += is "[category] [asked]/[asked+unasked]"
            if(asked == total_questions){
                category_list += "~~"+getQuestionCategory()+" "+asked+"/"+total_questions+"~~\n";
            }
            //else += "[category] [asked]/[asked+unasked]" to category_list
            else {
                category_list+= getQuestionCategory()+" "+asked+"/"+total_questions+"\n";
            }

        }
        return category_list;
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
//        System.out.println(getQuestion());
//        ObjectInputStream oos = Utilities.readObject(TRACKER_FILE_PATH);
//        try {
//            Stack<Integer> used_questions = (Stack<Integer>) oos.readObject();
//            System.out.println(used_questions);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println(getCategories());
    }
}
