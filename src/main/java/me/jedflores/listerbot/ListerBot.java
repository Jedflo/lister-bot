package me.jedflores.listerbot;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ListerBot {
    private static final String token = new Token().getToken();
    
    public static void main (String[] args){
        JDABuilder builder = JDABuilder.createDefault(token);
        JDA jda;
        PingPong pingpong = new PingPong();
        builder.addEventListeners(pingpong);
        try {
            jda = builder.build();
        } catch (LoginException ex) {
            Logger.getLogger(ListerBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
}
