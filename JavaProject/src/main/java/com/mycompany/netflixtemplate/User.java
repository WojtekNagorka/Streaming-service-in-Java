/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.netflixtemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Semaphore;
/**
 *
 * @author wojte
 */
public class User extends Thread implements Serializable {
    private Thumbnail thumbnail;
    private String userName;
    private Boolean isPremium;
    private String joinDate;
    private ArrayList<Channel> followedChannels;
    private Watchable currentlyWatched;
    private Queue<Watchable> moviesInQueue;
    private Simulation simulation;
    private int watchtime;
    private String typeOfWatchable;

    public String getTypeOfWatchable() {
        return typeOfWatchable;
    }

    public void setTypeOfWatchable(String typeOfWatchable) {
        this.typeOfWatchable = typeOfWatchable;
    }

    public int getWatchtime() {
        return watchtime;
    }

    public void setWatchtime(int watchtime) {
        this.watchtime = watchtime;
    }
    



    public Simulation getSimulation() {
        return simulation;
    }
    
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Queue<Watchable> getMoviesInQueue() {
        return moviesInQueue;
    }

    public void setMoviesInQueue(Queue <Watchable> moviesInQueue) {
        this.moviesInQueue = moviesInQueue;
    }
    
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public ArrayList<Channel> getFollowedChannels() {
        return followedChannels;
    }

    public void setFollowedChannels(ArrayList<Channel> followedChannels) {
        this.followedChannels = followedChannels;
    }

    public Watchable getCurrentlyWatched() {
        return currentlyWatched;
    }

    public void setCurrentlyWatched(Watchable currentlyWatched) {
        this.currentlyWatched = currentlyWatched;
    }
    
    public String getInfo(){
        String info = "";
        info += "Username: " + userName + "\n";
        info += "Join date: " + joinDate + "\n";
        info += "Is the user premium?: " + (isPremium ? "yes" : "no") + "\n";
        info += "Currently watched: " + (currentlyWatched != null ? currentlyWatched.getVideoName() : "Searching for sth to watch")+"\n";
        info += "Type of media: " +typeOfWatchable+"\n";
        //add type of watchable
        info += "Watchtime: "+ String.valueOf(watchtime)+"\n";
        return info;
    }
    
    
    @Override
     public void run() {
        int simTime;
        Random random=new Random();
        while (true) {
            synchronized (this.getSimulation()) {
                simTime = this.getSimulation().getTime();
                if(this.currentlyWatched!=null){
                    int rNum;
                    if(currentlyWatched instanceof Video){
                        Video video= (Video)currentlyWatched;
                        this.watchtime+=1;
                        if(video.getVideoLength()<=this.watchtime){
                            rNum=random.nextInt(10);
                            video.setNumberOfViewers(video.getNumberOfViewers()+1);
                            
                            if(rNum<5){
                                video.setNumberOfLikes(video.getNumberOfLikes()+1);
                            }
                            this.currentlyWatched=null;
                            this.typeOfWatchable="None";
                            watchtime=0;
                        }
                    }else{
                        Stream stream=(Stream)currentlyWatched;
                        this.watchtime+=1;
                        if(stream.getIsOver()){
                            this.currentlyWatched=null;
                            this.typeOfWatchable="None";
                            watchtime=0;
                        }else if(this.watchtime==5){
                            rNum=random.nextInt(10);
                            if(rNum<5){
                                stream.setNumberOfLikes(stream.getNumberOfLikes()+1);
                            }
                        }
                    }
                }else{
                    this.currentlyWatched=this.moviesInQueue.poll();
                    watchtime=0;
                    if(this.currentlyWatched instanceof Video){
                        Video video=(Video)currentlyWatched;
                        this.typeOfWatchable="Video";
                        if(video.getIsPremium()==true && this.isPremium==false){
                            System.out.println("Can't access video, it is premium");
                            this.typeOfWatchable="None";
                        }
                    }else if(this.currentlyWatched instanceof Stream){
                        Stream stream=(Stream)currentlyWatched;
                        this.typeOfWatchable="Stream";
                        if(stream.getIsOver()){
                            System.out.println("Too late, stream is over!");
                            this.currentlyWatched=null;
                            this.typeOfWatchable="None";
                        }else{
                            stream.setNumberOfViewers(stream.getNumberOfViewers()+1);
                        }
                    }
                }
                this.simulation.setCounter(this.simulation.getCounter()+1);
                try {
                    this.getSimulation().wait(); // Wait for the simulation time to change
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void watchVideo(Video video){
        
    }
    public void watchStream(Stream stream){
        
    }
    public void enqueue(Watchable media){
        
    }
}
