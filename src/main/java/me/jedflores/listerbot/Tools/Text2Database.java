package me.jedflores.listerbot.Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.jedflores.listerbot.OneKQuestions.*;
import static me.jedflores.listerbot.Tools.DatabaseTools.getConnection;
import static me.jedflores.listerbot.Tools.DatabaseTools.insertInto;

public class Text2Database {
    public static void main(String[] args) {
        textToDatabase("Question Categories");
    }

    public static boolean textToDatabase(String folder_path){
        boolean out = true;
        File folder = new File(folder_path);
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles) {
            if(file.isFile()){
                String category = file.getName().replaceFirst("[.][^.]+$", "");
                System.out.println(category+" file path : "+file.getPath());
                List<String> questions = Utilities.fileToList(file.getPath());
                System.out.println(questions.size()+" lines read from "+category+" file");
                for (String q:questions) {
                    System.out.println(q);
                    try {
                        Connection con = getConnection();
                        String statement = "INSERT INTO questions(Question, Category) VALUES (?,?)";
                        PreparedStatement query = con.prepareStatement(statement);
                        query.setString(1,q);
                        query.setString(2,category);
                        query.executeUpdate();
                        query.close();
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(category+" DONE!");
            } else if (file.isDirectory()) {
                System.out.println(file.getName());
            }
        }


//        String category = "vacation";
//        setQuestionCategory("Question Categories\\"+category+".txt");
//        List<String> questions = loadQuestions();
//        for (String q:questions) {
//            System.out.println(q);
//            try {
//                Connection con = getConnection();
//                String statement = "INSERT INTO questions(Question, Category) VALUES (?,?)";
//                PreparedStatement query = con.prepareStatement(statement);
//                query.setString(1,q);
//                query.setString(2,category);
//                query.executeUpdate();
//                query.close();
//                con.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(category+" DONE!");
        return out;
    }


}
