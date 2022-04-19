package me.jedflores.listerbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static me.jedflores.listerbot.CommandManager.getAllCommands;
import static me.jedflores.listerbot.OneKQuestions.*;

/*
discord bot commands.
 */

public class PingPong extends ListenerAdapter {
    ArrayList<movie> MovieList = new ArrayList<>();
    ArrayList<movie> WatchHistory = new ArrayList<>();
    Map<String, Map>  masterList = new HashMap<>();
    Map<Integer, Task> generalList = new HashMap<>();
    String[] COMMAND_LIST = getAllCommands();



    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        masterList.put("Todo", generalList);
        String user_input = e.getMessage().getContentRaw();
        if(user_input.startsWith("!")){
            user_input=user_input.replace("!","");
            CommandManager.setInput(user_input);
            CommandManager.split();
            String command = CommandManager.getCommand();
            String args = CommandManager.getArguments();
            System.out.println(command);

            switch(command){
                case "":


                    break;
                case "create list":
                    Map<String,Task> new_list = new HashMap<>();
                    masterList.put(args,new_list);
                    break;
                case "delete list":
                    masterList.remove(args);
                    break;
                case "rename list":

                    break;
                case "rsq":
                    e.getChannel().sendMessage(getQuestion()).queue();
                    break;
                case "rsq undo":
                    List<Integer> indexes_list = getUsedIndexes();
                    /*System.out.println("Index array size:" + indexes_list.size());
                    System.out.println("deleted item at index " + (indexes_list.size()-1) + " from index tracker");*/
                    List<String> questions = loadQuestions();
                    /*System.out.println("question: \"" + questions.get(indexes_list.get(indexes_list.size()-1)) + ("\" will be restored to the pool of questions"));*/
                    e.getChannel().sendMessage("question: **\"" +questions.get(indexes_list.get(indexes_list.size()-1)) + ("\"** will be restored to the pool of questions")).queue();
                    deleteIndex(indexes_list.get(indexes_list.size()-1));
                    break;
                case "rsq clear":
                    String INDEX_TRACKER_FILE = "used-indexes.bin";
                    Utilities.deleteFile(INDEX_TRACKER_FILE);
                    e.getChannel().sendMessage("All questions has been restored").queue();
                    break;
                case "rsq count":
                    e.getChannel().sendMessage("There are a total of " + loadQuestions().size() + " questions").queue();
                case "rsq count asked":
                    e.getChannel().sendMessage("I have asked you "+getUsedIndexes().size() + " questions in total").queue();
                    break;
                case "test":
                    e.getChannel().sendMessage("test success").queue();
                    break;
            } //switch end
        }// if user starts with "!" end







    
    }
    
}
