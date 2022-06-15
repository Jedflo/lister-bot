package me.jedflores.listerbot;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;

import me.jedflores.listerbot.Tools.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class ListerBot {
    //private static String token = "";
    
    public static void main (String[] args){
        String token = Utilities.readFile("token.txt");
        token = token.replace("\n", "").replace("\r", "");

        JDABuilder builder = JDABuilder.createDefault(token);
        JDA jda;
        OneKQuestionsEvents pingpong = new OneKQuestionsEvents();
        builder.addEventListeners(pingpong);
        try {
            jda = builder.build();
        } catch (LoginException ex) {
            Logger.getLogger(ListerBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
}
