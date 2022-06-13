package me.jedflores.listerbot;

import java.util.*;

import me.jedflores.listerbot.Tools.Utilities;
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
    static ArrayList<String> rsqCategories = new ArrayList<String>(
            Arrays.asList(
                    "",
                    "attractions",
                    "favorites",
                    "health and food",
                    "morals and convictions",
                    "personality and emotions",
                    "pets",
                    "religion and beliefs",
                    "vacation"
            )
    ) ;


    private static String change1kQuestionsCategory(String category){
        if(category.isEmpty()||!rsqCategories.contains(category)){
            return "";
        }
        setQuestionCategory("Question Categories\\" + category + ".txt");
        setCategoryProgressTracking("Category Trackers\\" + category + " Tracker.bin");
        return "Question category is now set to "+category;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        masterList.put("Todo", generalList);
        String user_input = e.getMessage().getContentRaw();
        if(user_input.startsWith("!")){
            user_input=user_input.replace("!","");
            CommandManager.setInput(user_input);
            CommandManager.split();
            String command = CommandManager.getCommand();
            String args = CommandManager.getArguments().toLowerCase();
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
//=============================1k questions=====================================
                case "rsq":
                    e.getChannel().sendMessage(getQuestion()).queue();
                    break;
                case "rsq undo":
                    List<Integer> indexes_list = getUsedIndexes();
                    List<String> questions = loadQuestions();
                    e.getChannel().sendMessage("question: **\"" +questions.get(indexes_list.get(indexes_list.size()-1)) + ("\"** will be restored to the pool of questions")).queue();
                    deleteIndex(indexes_list.get(indexes_list.size()-1));
                    break;
                case "rsq clear":
                    String INDEX_TRACKER_FILE = getCategoryProgressTracking();
                    Utilities.deleteFile(INDEX_TRACKER_FILE);
                    e.getChannel().sendMessage("All questions has been restored").queue();
                    break;
                case "rsq count":
                    e.getChannel().sendMessage(
                            "Category: "+ getCategories() + "\n" +
                                    "There are a total of " + loadQuestions().size() + " questions\n" +
                                    "I have asked you "+getUsedIndexes().size() + " questions in total").queue();

                    break;

                case "rsqa":
                    Random rn_category_selector = new Random();
                    //randomly select category
                    int category_index = rn_category_selector.nextInt(rsqCategories.size());
                    change1kQuestionsCategory(rsqCategories.get(category_index));
                    //get question from category then send to user
                    e.getChannel().sendMessage("Category:" + getCategories() + "\n" + getQuestion()).queue();
                    break;

                case "rsq lc":
                case "rsq list categ":
                case "rsq list categories":
                case "rsq list category":
                    String reply = "**Categories:**\n";
                    List<String> categories = getCategories();
                    for (String category:categories) {
                        reply+=category+"\n";
                    }
                    e.getChannel().sendMessage(reply).queue();
                    break;

                case "rsq category":
                case "rsq categ":
                case "rsq cat":
                    System.out.println("command:"+command);
                    System.out.println("args:"+args);
                    List<String> category_check= getCategories();
                    if(category_check.contains(args)){
                        setQuestionCategory(args);
                        e.getChannel().sendMessage("Category set to "+args).queue();
                    }else {
                        e.getChannel().sendMessage("could not find a category called "+args+" ").queue();
                    }
                    break;
                case "test":
                    e.getChannel().sendMessage("test success").queue();
                    break;
            } //switch end
        }// if user starts with "!" end







    
    }
    
}
