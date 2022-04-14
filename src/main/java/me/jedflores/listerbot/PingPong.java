package me.jedflores.listerbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static me.jedflores.listerbot.StringHandling.*;

/*
discord bot commands.
 */

public class PingPong extends ListenerAdapter {
    ArrayList<movie> MovieList = new ArrayList<>();
    ArrayList<movie> WatchHistory = new ArrayList<>();
    Map<String, Map>  masterList = new HashMap<>();
    Map<Integer, Task> generalList = new HashMap<>();




    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        masterList.put("Todo", generalList);
        String user_input = e.getMessage().getContentRaw();
        if(user_input.startsWith("!")){
            e.getChannel().sendMessage(user_input.replace("!","")).queue();
        }
    
    
    }
    
}
