package me.jedflores.listerbot;

import java.util.*;

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
                    /*System.out.println("Index array size:" + indexes_list.size());
                    System.out.println("deleted item at index " + (indexes_list.size()-1) + " from index tracker");*/
                    List<String> questions = loadQuestions();
                    /*System.out.println("question: \"" + questions.get(indexes_list.get(indexes_list.size()-1)) + ("\" will be restored to the pool of questions"));*/
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
                            "Category: "+ getCategory() + "\n" +
                                    "There are a total of " + loadQuestions().size() + " questions\n" +
                                    "I have asked you "+getUsedIndexes().size() + " questions in total").queue();

                    break;

                case "rsqa":
                    Random rn_category_selector = new Random();
                    //randomly select category
                    int category_index = rn_category_selector.nextInt(rsqCategories.size());
                    change1kQuestionsCategory(rsqCategories.get(category_index));
                    //get question from category then send to user
                    e.getChannel().sendMessage("Category:" + getCategory() + "\n" + getQuestion()).queue();
                    break;

                case "rsq category":
                case "rsq categ":
                case "rsq cat":
                    int question_category_index= 0;// set to 5 because default is personality and emotions in OneKQuestions.java
                    System.out.println("command:"+command);
                    System.out.println("args: "+args);
                    switch (args){
                        case "":
                            question_category_index= 0;
                            break;

                        case "attractions":
                        case "att":
                        case "1":
                            question_category_index= 1;
                            break;

                        case"favorites":
                        case"f":
                        case"2":
                            question_category_index= 2;
                            break;

                        case"health and food":
                        case"hf":
                        case"3":
                            question_category_index= 3;
                            break;

                        case"morals and convictions":
                        case"mc":
                        case"4":
                            question_category_index= 4;
                            break;

                        case"personality and emotions":
                        case"pe":
                        case"5":
                            question_category_index= 5;
                            break;

                        case"pets":
                        case"pet":
                        case"6":
                            question_category_index= 6;
                            break;

                        case"religion and beliefs":
                        case"rb":
                        case"7":
                            question_category_index= 7;
                            break;

                        case"vacations":
                        case"vac":
                        case"8":
                            question_category_index= 8;
                            break;
                        default:
                            if(args.equalsIgnoreCase(command)){
                                e.getChannel().sendMessage("current category: "+getCategory()).queue();
                                question_category_index = 0;
                            }
                            else{
                                e.getChannel().sendMessage("can't find category "+args).queue();
                            }
                    }
                    e.getChannel().sendMessage(change1kQuestionsCategory(rsqCategories.get(question_category_index))).queue();
                    break;
                case "test":
                    e.getChannel().sendMessage("test success").queue();
                    break;
            } //switch end
        }// if user starts with "!" end







    
    }
    
}
