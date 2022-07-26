package me.jedflores.listerbot;

import java.awt.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.util.*;
import java.util.List;

import me.jedflores.listerbot.Tools.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static me.jedflores.listerbot.CommandManager.getAllCommands;
import static me.jedflores.listerbot.OneKQuestions.*;

/*
discord bot commands.
 */

public class OneKQuestionsEvents extends ListenerAdapter {
    User user;

    @Override
    public void onMessageReceived(MessageReceivedEvent e){

        String user_input = e.getMessage().getContentRaw();
        if(user_input.startsWith("!")){
            user_input=user_input.replace("!","");
            CommandManager.setInput(user_input);
            CommandManager.split();
            String command = CommandManager.getCommand();
            String args = CommandManager.getArguments().toLowerCase();
            user = e.getAuthor();
            System.out.println(user.getName()+": "+command);

            switch(command){
                case "rsq info":
                case "rsq i":
                    EmbedBuilder embed_builder = new EmbedBuilder();
                    embed_builder.setTitle("1000 Questions for Couples","https://docs.google.com/file/d/0BxAs4XCvSMxLeV9ja1AyQUZtUDQ/edit?resourcekey=0-vf8IEyqWhA9ypNqIGvlW-Q");
                    embed_builder.setAuthor("Michael Webb");
                    embed_builder.setDescription("\"Whether you have been dating 5 months or have been married for 500 months you absolutely must know the beloved’s answers to these questions.\" -Michael Webb");
                    embed_builder.setColor(Color.blue);
                    File cover = new File("lister resources\\OneKQuestions\\1kQFC_cover.png");
                    embed_builder.setImage("attachment://cover.png");
                    e.getChannel().sendMessageEmbeds(embed_builder.build()).addFile(cover,"cover.png").queue();
                    e.getChannel().sendMessage("All questions I use for the relationship questions (!rsq) came from this book.").queue();
                    break;
                case "rsq":
                    e.getChannel().sendMessage(getQuestion()).queue();
                    break;
                case "rsq undo":
                    e.getChannel().sendMessage(undoLatestQuestion()).queue();
                    break;
                case "rsq clear":

                    break;
                case "rsq count":
                    int unasked = getQuestions(0).size();
                    int asked = getQuestions(1).size();
                    e.getChannel().sendMessage(
                            "Category: "+getQuestionCategory()+"\n"
                            +asked+" questions asked out of "+(unasked+asked)
                    ).queue();
                    break;

                case "rsqa":
                    e.getChannel().sendMessage(getAnyQuestion()).queue();
                    break;

                case "rsq lc":
                case "rsq list categ":
                case "rsq list categories":
                case "rsq list category":
                    e.getChannel().sendMessage(listCategories()).queue();
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
                    }
                    else if(command.equals(args)){
                        e.getChannel().sendMessage("Current category is: "+getQuestionCategory()).queue();
                    }
                    else {
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
