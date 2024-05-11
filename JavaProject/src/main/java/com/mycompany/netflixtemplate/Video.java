/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.netflixtemplate;

import java.io.Serializable;



/**
 *
 * @author wojte
 */
public class Video extends Watchable implements Serializable{
    private Boolean isPremium;
    private int date;
    private int videoLength;

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }
    public void playVideo(){
        //to do
    }
    public String getInfo(){
        String info="";
        info+=("Name: "+this.getVideoName())+"\n";
        info+=("Description: "+this.getDescription())+"\n";
        info+=("Channel: "+(this.getChannel()).getUserName())+"\n";
        info+=("Number of views: ")+String.valueOf(this.getNumberOfViewers())+"\n";
        info+=("Number of likes: ")+String.valueOf(this.getNumberOfLikes())+"\n";
        info+=("Time of appearence: "+String.valueOf(this.getDate()))+"\n";
        info+=("Video length: "+String.valueOf(this.getVideoLength()))+"\n";
        info+="Is it premium?: "+(isPremium==true ? "true" : "false")+"\n";
        return info;
    }
}
    

