package me.jedflores.listerbot.Tools;

import me.jedflores.listerbot.Passwords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseTools {

    public static Connection getConnection() throws Exception{
        try{
//            String driver = "com.mysql.jdbc.Driver";
            String url = Passwords.getSqlUrl().replace("\n", "").replace("\r", "");
            String username = Passwords.getSqlUsername().replace("\n", "").replace("\r", "");
            String password = Passwords.getSqlPass().replace("\n", "").replace("\r", "");
//            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        }catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    public static void CreateTable(String table)throws Exception{
        String end_process_message = "table successfully created!";
        try{
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTs "+table);
            create.executeUpdate();
            create.close();
            conn.close();
        }catch(Exception e){
            end_process_message = e.getMessage();
        }
        System.out.println(end_process_message);
    }

    public static void PreparedStatement(String statement)throws Exception{
        String end_process_message = "Query OK";
        try{
            Connection conn = getConnection();
            PreparedStatement prep_statement = conn.prepareStatement(statement);
            prep_statement.executeUpdate();
            prep_statement.close();
            conn.close();
        }catch(Exception e){
            end_process_message = e.getMessage();
        }
        System.out.println(end_process_message);
    }

    public static void insertInto(String table_name, String variables, String values) throws Exception{
        String end_process_message="Insert to "+table_name+" successful";
        try{
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO "+table_name+"("+variables+") VALUES ("+values+")");
            posted.executeUpdate();
            posted.close();
            con.close();
        }catch (Exception e){
            end_process_message = "\u001B[33m" + e.getMessage();
        }
        System.out.println(end_process_message);
    }

    public static void main(String[] args) throws Exception {
        //Connection connection = getConnection();
        //insertInto("questions","Question,Category", "'where did you study?', 'category 9'");
        //PreparedStatement("INSERT INTO questions(Question,Category) VALUES('When were you born?', 'category 3')");
    }


}
