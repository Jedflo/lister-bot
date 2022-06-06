package me.jedflores.listerbot.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static me.jedflores.listerbot.OneKQuestions.*;
import static me.jedflores.listerbot.Tools.DatabaseTools.getConnection;
import static me.jedflores.listerbot.Tools.DatabaseTools.insertInto;

public class Text2Database {
    public static void main(String[] args) {
        String category = "vacation";
        setQuestionCategory("Question Categories\\"+category+".txt");
        List<String> questions = loadQuestions();
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
    }
}
