/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.netflixtemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author wojte
 */
public class Channel extends User implements Serializable{
    private ArrayList<Video> videos ;
    private ArrayList<User> followers ;
    private Stream myStream;
    

    public void endStream(){
        if (myStream!=null){
            this.myStream.setIsOver(true);
            this.myStream=null;
        }else{
            System.out.println("No stream to end");
        }

    }
    
    @Override
    public void run(){
        int simTime;
        Random random=new Random();
        while (true) {
            synchronized (this.getSimulation()) {
                simTime = this.getSimulation().getTime();
                int randNum=random.nextInt(100);
                if(randNum<5){
                    Simulation sim=this.getSimulation();
                    int mediaCounter=sim.getMediaCounter();
                    Video video=sim.createVideo(this, mediaCounter, simTime);
                    if(video!=null){
                        this.videos.add(video);
                        ArrayList<Video>vids=sim.getVideos();
                        vids.add(video);
                        sim.setVideos(vids);
                        sim.setMediaCounter(mediaCounter+1);
                        sim.alertSubs(this,video);
                    }
                }
                if(myStream!=null){
                    randNum=random.nextInt(100);
                    if(randNum<1){
                        System.out.println("Stream has ended");
                        this.endStream();
                    }
                }else{
                    randNum=random.nextInt(100);
                    if(randNum<3){
                        Simulation sim=this.getSimulation();
                        int mediaCounter=sim.getMediaCounter();
                        Stream stream=sim.createStream(this, mediaCounter, simTime);
                        if(stream!=null){
                            this.myStream=stream;
                            ArrayList<Stream>streams=sim.getStreams();
                            streams.add(stream);
                            sim.setStreams(streams);
                            sim.setMediaCounter(mediaCounter+1);
                            sim.alertSubs(this,stream);
                            System.out.println("New stream has appeared!");
                        }
                    }
                }
                this.getSimulation().setChannelCounter(this.getSimulation().getChannelCounter()+1);
                try {
                    this.getSimulation().wait(); // Wait for the simulation time to change
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }        
    }
    
    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }


    public Stream getMyStream() {
        return myStream;
    }

    public void setMyStream(Stream myStream) {
        this.myStream = myStream;
    }
    
    public Video getMostLikedVideo(){
        int maxLikes=-1;
        Video bestVideo=null;
        if(videos!=null){
            for(Video video:videos){
                if (video.getNumberOfLikes()>maxLikes){
                    bestVideo=video;
                    maxLikes=video.getNumberOfLikes();
                }
            }
            return bestVideo;
        }
        return null;
    }
    
    @Override
    public String getInfo(){
        String info = "";
        info += "Username: " + this.getUserName() + "\n";
        info += "Join date: " + this.getJoinDate() + "\n";
        info+=(myStream != null ? "I am streaming "+myStream.getVideoName() : "Not streaming") + "\n";
        info += "Number of followers: " + String.valueOf(followers.size())+ "\n";
        info += "Newest video: " + (videos.get(videos.size()-1)).getVideoName() + "\n";
        info += "Most liked video: " + (this.getMostLikedVideo().getVideoName());
        return info;
    }

}
