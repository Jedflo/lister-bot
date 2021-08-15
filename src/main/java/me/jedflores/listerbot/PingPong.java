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
        //test
        if(e.getMessage().getContentRaw().equals("!ping")){
            e.getChannel().sendMessage("pong").queue();
        }

        //list all command, unfinished.
        if(e.getMessage().getContentRaw().startsWith("!Command")){
            String s= trim(e.getMessage().getContentRaw().toString());
            
            e.getChannel().sendMessage(s).queue();
        }

        //add movie to movie list
        if(e.getMessage().getContentRaw().startsWith("!add")){
            String s= trim(e.getMessage().getContentRaw().toString());
            movie mov=new movie();
            mov.setTitle(s);
            MovieList.add(mov);
            
            e.getChannel().sendMessage(s+" is added to movie list!").queue();
        }

        //show movie list
        if(e.getMessage().getContentRaw().equals("!ml")){
            String list="";
            e.getChannel().sendMessage("**Movie List:**").queue();
            
            for(movie m:MovieList){
            
                if(m.isWatched()){
                    list+="~~"+m.getTitle()+"~~"+"\n";
                }
                else if(m.isDownloaded()){
                    list+="__"+m.getTitle()+"__"+"\n";
                }
                else{
                    list+=m.getTitle()+"\n";
                }
                
            }
            e.getChannel().sendMessage(list).queue();
        }

        //set movie in list to watched
        if(e.getMessage().getContentRaw().startsWith("!w ")){
            String s= trim(e.getMessage().getContentRaw());
            boolean result=false;
            for(int i=0;i<MovieList.size();i++){
                if(MovieList.get(i).getTitle().equalsIgnoreCase(s)){
                    MovieList.get(i).watched();
                    WatchHistory.add(MovieList.get(i));
                    MovieList.remove(i);
                    result=true;
                }
            }
            if(!result){
                e.getChannel().sendMessage("Movie: " +"**"+s+"**"+ " not found!").queue();
            }
            else{
                 e.getChannel().sendMessage("Movie: " +"**"+s+"**"+ " Watched!").queue();
            }
            
        }

        //set movie in movie list as downloaded
        if(e.getMessage().getContentRaw().startsWith("!d ")){
            String s= trim(e.getMessage().getContentRaw());
            boolean result=false;
            for(int i=0;i<MovieList.size();i++){
                if(MovieList.get(i).getTitle().equalsIgnoreCase(s)){
                    MovieList.get(i).downloaded();
                    result=true;
                }
            }
            if(!result){
                e.getChannel().sendMessage("Movie: " +"**"+s+"**"+ " not found!").queue();
            }
             else{
                 e.getChannel().sendMessage("Movie: " +"**"+s+"**"+ " downloaded!").queue();
            }
            
        }
        
        //remove movie from list
        if(e.getMessage().getContentRaw().startsWith("!r ")){
            String s= trim(e.getMessage().getContentRaw());
            
               for(int i=0;i<MovieList.size();i++){
                    if(s.equalsIgnoreCase(MovieList.get(i).getTitle())){
                        MovieList.remove(i);
                    }
                }
           
            e.getChannel().sendMessage(s+" removed from **Movie List**").queue();
        }
        
        //show history of watched movies from movie list
        if(e.getMessage().getContentRaw().equals("!hl")){
            String history="";
            e.getChannel().sendMessage("**Watched List:**").queue();
            
            for(movie m:WatchHistory){
            
                history+= m.getTitle()+" watched on: "+m.getDateWatched()+"\n";
                
            }
            e.getChannel().sendMessage(history).queue();
        }
        
        //set movie as downloaded
        if(e.getMessage().getContentRaw().equals("!dl")){
            String downloads="";
            e.getChannel().sendMessage("**Movie List:**").queue();
            
            for(movie m:MovieList){
            
                if(m.isDownloaded()){
                    downloads+="__"+m.getTitle()+"__"+"\n";
                }
                
            }
            e.getChannel().sendMessage(downloads).queue();
        }

        if(e.getMessage().getContentRaw().startsWith("!todo")||e.getMessage().getContentRaw().startsWith("!t")){
            String command = trim(e.getMessage().getContentRaw().toString());
            Map todo = masterList.get("Todo");
            int size = todo.size();
            if(command.equalsIgnoreCase("!todo")||command.equalsIgnoreCase("!t")){
                if(size==0){
                    e.getChannel().sendMessage("Todo is empty").queue();
                }
                else {
                    String outList="";
                    for (int c = 0; c < size; c++) {
                        Task t = (Task) todo.get(c);
                        outList+=(c+1)+"|"+t.getTaskName()+"\n";
                    }
                    e.getChannel().sendMessage(outList).queue();
                }
            }
            else {
                String[] commandArray = stringToArray(command);

                Task newtask = new Task();
                try {
                    if (commandArray[0] != null) {
                        newtask.setTaskName(commandArray[0]);
                    }
                    if (commandArray[1] != null) {
                        newtask.setDescription(commandArray[1]);
                    }
                    if (commandArray[2] != null) {
                        newtask.setDeadline(commandArray[2]);
                    }

                    todo.put(size,newtask);
                    e.getChannel().sendMessage("Added '" + commandArray[0] + "' to Todo").queue();

                } catch (Exception ex) {
                    e.getChannel().sendMessage("An Error Occurred").queue();
                    ex.printStackTrace();
                }
            }



        }
    
    
    
    }
    
}
