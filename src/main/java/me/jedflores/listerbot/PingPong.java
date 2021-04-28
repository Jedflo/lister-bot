package me.jedflores.listerbot;

import java.util.ArrayList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static me.jedflores.listerbot.Functions.getVariables;

public class PingPong extends ListenerAdapter {
    ArrayList<movie> MovieList = new ArrayList<>();
    ArrayList<movie> WatchHistory = new ArrayList<>();
    
    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getMessage().getContentRaw().equals("!ping")){
            e.getChannel().sendMessage("pong").queue();
        }
        if(e.getMessage().getContentRaw().startsWith("!Command")){
            String s=getVariables(e.getMessage().getContentRaw().toString());
            
            e.getChannel().sendMessage(s).queue();
        }
        
        if(e.getMessage().getContentRaw().startsWith("!add")){
            String s=getVariables(e.getMessage().getContentRaw().toString());
            movie mov=new movie();
            mov.setTitle(s);
            MovieList.add(mov);
            
            e.getChannel().sendMessage(s+" is added to movie list!").queue();
        }
        
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
        
        if(e.getMessage().getContentRaw().startsWith("!w ")){
            String s=getVariables(e.getMessage().getContentRaw());
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
    
        if(e.getMessage().getContentRaw().startsWith("!d ")){
            String s=getVariables(e.getMessage().getContentRaw());
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
        
        
        if(e.getMessage().getContentRaw().startsWith("!r ")){
            String s= getVariables(e.getMessage().getContentRaw());
            
               for(int i=0;i<MovieList.size();i++){
                    if(s.equalsIgnoreCase(MovieList.get(i).getTitle())){
                        MovieList.remove(i);
                    }
                }
           
            e.getChannel().sendMessage(s+" removed from **Movie List**").queue();
        }
        
        
        if(e.getMessage().getContentRaw().equals("!hl")){
            String history="";
            e.getChannel().sendMessage("**Watched List:**").queue();
            
            for(movie m:WatchHistory){
            
                history+= m.getTitle()+" watched on: "+m.getDateWatched()+"\n";
                
            }
            e.getChannel().sendMessage(history).queue();
        }
        
        
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
    
    
    
    }
    
}
