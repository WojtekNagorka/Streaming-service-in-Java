/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.netflixtemplate;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author wojte
 */
public class Stream extends Watchable implements Serializable{
    private int startTime;
    private Boolean isOver;

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }
    
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public String getInfo(){
        String info="";
        info+=("Name: "+this.getVideoName())+"\n";
        info+=("Description: "+this.getDescription())+"\n";
        info+=("Channel: "+(this.getChannel()).getUserName())+"\n";
        info+=("Number of viewers: ")+String.valueOf(this.getNumberOfViewers())+"\n";
        info+=("Number of likes: ")+String.valueOf(this.getNumberOfLikes())+"\n";
        info+=("Start of the stream: "+String.valueOf(this.getStartTime()))+"\n";
        info+="Is it over?: "+(isOver==true ? "true" : "false")+"\n";
        return info;
    }
}
