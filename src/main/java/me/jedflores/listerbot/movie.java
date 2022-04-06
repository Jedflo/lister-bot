/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jedflores.listerbot;
import java.io.Serializable;
import java.time.LocalDate;


public class movie {
private String title;
private boolean downloaded=false;
private boolean watched=false;
private String dateWatched;

    public String getTitle(){
        return title;
    }

    public boolean isWatched(){
        return watched;
    }
    
    public boolean isDownloaded(){
        return downloaded;
    }
    
    public String getDateWatched(){
        return dateWatched;
    }
    
    public void setTitle(String s){
        title=s;
    }
    
    public void downloaded(){
        downloaded=true;
    }
    
    public void watched(){
        dateWatched=LocalDate.now().toString();
        watched=true;
    }
    
    
   
}

