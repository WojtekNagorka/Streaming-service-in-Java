/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.netflixtemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author wojte
 */
public class Simulation implements Serializable{
    private ArrayList<Channel> channels;
    private ArrayList<User> users;
    private ArrayList<Stream> streams;
    private ArrayList<Video> videos;
    private volatile int time;
    private int startTime;
    private volatile Boolean isStopped;
    private static int numberOfChannels=10;
    private static int numberOfUsers=100;
    private static int videosPerChannel=12;
    private int mediaCounter;
    private Boolean objectsCreated;
    private transient Gui gui;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Gui getGui() {
        return gui;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public Boolean getObjectsCreated() {
        return objectsCreated;
    }

    public void setObjectsCreated(Boolean objectsCreated) {
        this.objectsCreated = objectsCreated;
    }

   
   public Simulation(){
       objectsCreated=false;
   }
   
    public int getMediaCounter() {
        return mediaCounter;
    }

    public void setMediaCounter(int mediaCounter) {
        this.mediaCounter = mediaCounter;
    }
    private ArrayList <String> watchableNames;
    private ArrayList <String> dates;
    private static int publishingTimeLimit=1000;
    private volatile int counter=0;
    private volatile int channelCounter;

    
    public Boolean getIsStopped(){
        return isStopped;
    }

    public void setIsStopped(Boolean isStopped) {
        this.isStopped = isStopped;
    }
    
    public int getChannelCounter() {
        return channelCounter;
    }

    public void setChannelCounter(int channelCounter) {
        this.channelCounter = channelCounter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    
    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
    public ArrayList<String> getWatchableNames() {
        return watchableNames;
    }

    public void setWatchableNames(ArrayList<String> watchableNames) {
        this.watchableNames = watchableNames;
    }
    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public void addTime(){
        
    }
    
    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public void setStreams(ArrayList<Stream> streams) {
        this.streams = streams;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
    
    public <T>void alertSubs(Channel channel,T t){
        if(t instanceof Video || t instanceof Stream){
            ArrayList<User>subs=channel.getFollowers();
            for(int i=0;i<subs.size();i++){
                User sub=subs.get(i);
                Queue<Watchable>queue=sub.getMoviesInQueue();
                queue.add((Watchable) t);
                sub.setMoviesInQueue(queue);
            }
        }
    }
    
    public User searchUserByName(String name){
        if(users!=null){
            for(User user: users){
                if(user.getUserName().equals(name)){
                    return user;
                }
            }
        }
        return null;
    }
    
    public Channel searchChannelByName(String name){
        if(channels!=null){
            for(Channel channel: channels){
                if(channel.getUserName().equals(name)){
                    return channel;
                }
            }
        }
        return null;
    }
    
    public Video searchVideoByName(String name){
        if(videos!=null){
            for(Video video: videos){
                if(video.getVideoName().equals(name)){
                    return video;
                }
            }
        }
        return null;
    }
    
    public Stream searchStreamByName(String name){
        if(streams!=null){
            for(Stream stream: streams){
                if(stream.getVideoName().equals(name)){
                    return stream;
                }
            }
        }
        return null;
    }
    
    public String getEntityByName(String name){
        User user=searchUserByName(name);
        if(user!=null){
            return user.getInfo();
        }
        Channel channel=searchChannelByName(name);
        if(channel!=null){
            return channel.getInfo();
        }
        Video video=searchVideoByName(name);
        if(video!=null){
            return video.getInfo();
        }
        Stream stream=searchStreamByName(name);
        if(stream!=null){
            return stream.getInfo();
        }
        return "No entity with this name found!";
    }
    
    public void addSubscriptions(User user){
        if (this.channels!=null){
            Random random=new Random();
            ArrayList<Channel> channelsToSub=new ArrayList <Channel>();
            for(int i=0;i<channels.size();i++){
                int rNum=random.nextInt(100);
                if (rNum<50){
                    Channel channel=channels.get(i);
                    channelsToSub.add(channel);
                    ArrayList<User> followers=channel.getFollowers();
                    if(followers==null){
                        followers=new ArrayList<User>();
                    }
                    followers.add(user);
                    channel.setFollowers(followers);
                }
            }
            user.setFollowedChannels(channelsToSub);
        }else{
            System.out.println("Create some channels first!");
        }
    }
    public User createUser(String name){
        User user=new User();
        user.setDaemon(true);
        user.setUserName(name);
        Random random = new Random();
        int rNum=random.nextInt(100);
        if (rNum<10){
            user.setIsPremium(true);
        }else{
            user.setIsPremium(false);
        }
        String joinDate=dates.get(random.nextInt(dates.size()));
        user.setJoinDate(joinDate);
        user.setThumbnail(new Thumbnail());
        user.setSimulation(this);
        user.setCurrentlyWatched(null);
        user.setWatchtime(0);
        user.setTypeOfWatchable("None");
        if(this.videos!=null&& this.streams!=null){
            Queue <Watchable> queue=new LinkedList<Watchable>();
            int numberOfVideosToWatch=random.nextInt(videos.size()/2);
            for(int i=0;i<numberOfVideosToWatch;i++){
                rNum=random.nextInt(videos.size());
                Video video=videos.get(rNum);
                if(queue.contains(video)==false){
                    queue.add(video);
                }
            }
            rNum=random.nextInt(streams.size());
            queue.add(streams.get(rNum));
            LinkedList<Watchable> temp = new LinkedList<>(queue);
            Collections.shuffle(temp);
            queue.clear();
            queue.addAll(temp);
            user.setMoviesInQueue(queue);
        }
        return user;
    }
    
    
    public Video createVideo(Channel channel,int id,int publishTime){
        if(id>=watchableNames.size()){
            System.out.println("Too large id selected for video name");
            return null;
        }
        Video vid=new Video();
        vid.setChannel(channel);
        vid.setVideoName(watchableNames.get(id));
        Random random = new Random();
        int rNum=random.nextInt(100);
        if (rNum<5){
            vid.setIsPremium(true);
        }else{
            vid.setIsPremium(false);
        }
        String description="Hello, welcome to my channel "+(vid.getChannel()).getUserName()+", hope you enjoy. If so remember to leave "
                + "a like";
        vid.setDescription(description);
        vid.setNumberOfLikes(0);
        vid.setNumberOfViewers(0);
        vid.setThumbnail(new Thumbnail());
        vid.setDate(publishTime);
        int duration=random.nextInt(5)+1;
        vid.setVideoLength(duration);
        return vid;
    }
    public Stream createStream(Channel channel,int id,int startTime){
        if(id>=watchableNames.size()){
            System.out.println("Too large id selected for stream name");
            return null;
        }
        Stream stream=new Stream();
        stream.setChannel(channel);
        stream.setVideoName(watchableNames.get(id));
        Random random = new Random();
        String description="Hello, welcome to my channel "+(stream.getChannel()).getUserName()+", hope you enjoy the stream. If so remember to leave "
                + "a like";
        stream.setDescription(description);
        stream.setNumberOfLikes(0);
        stream.setNumberOfViewers(0);
        stream.setThumbnail(new Thumbnail());
        stream.setStartTime(startTime);
        stream.setIsOver(false);
        return stream;
    }
    public Channel createChannel(String channelName){
        Channel channel=new Channel();
        channel.setUserName(channelName);
        channel.setDaemon(true);
        Random random = new Random();
        int rNum=random.nextInt(100);
        if (rNum<10){
            channel.setIsPremium(true);
        }else{
            channel.setIsPremium(false);
        }
        String joinDate=dates.get(random.nextInt(dates.size()));
        channel.setJoinDate(joinDate);
        channel.setThumbnail(new Thumbnail());
        channel.setSimulation(this);
        return channel;
    }
    
    public void createNameBase(){
        String filePath = "classes\\name.txt"; 
        ArrayList <String> names= new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setWatchableNames(names);
    }
    public void createDateBase(){
        String filePath = "classes\\dates.txt"; 
        ArrayList <String> dates= new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dates.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setDates(dates);
    }
    
    public void createObjects(){
        this.mediaCounter=0;
        this.createNameBase();
        this.createDateBase();
        ArrayList <Channel> channels= new ArrayList <Channel>();
        ArrayList <Video> videos= new ArrayList <Video>();
        ArrayList <User> users= new ArrayList <User>();
        ArrayList <Stream> streams= new ArrayList <Stream>();
        Random random = new Random();
        String adjectives[]={"puffy","petite","auspicious","scandalous","alert","scintillating","onerous","exultant",
        "nebulous","deserted"};
        String nouns[]={"tooth","importance","town","inspector","affair","village","shirt","recording","map","director"};
        String channelNames[]={"PixelVibesTV","TechTonicTales","CreativeCanvasShow","MindfulMunchiesTube","AdventureAwaitsHQ","CosmixCrazeChannel",
        "WellnessWaveTV","RetroRhythmRecords","NatureNurtureNetwork","DIYDynastyZone"};
        for(int i=0;i<numberOfChannels;i++){
            Channel channel= createChannel(channelNames[i]);

            ArrayList <Video> channelVideos= new ArrayList <Video>();
            for(int j=0;j<videosPerChannel;j++){
                Video video=createVideo(channel,mediaCounter,0);
                this.mediaCounter+=1;
                videos.add(video);
                channelVideos.add(video);
            }
            channel.setVideos(channelVideos);
            
            
            int streamOrNot=random.nextInt(10);
            if(streamOrNot<9){
                Stream stream=createStream(channel,mediaCounter,0);
                channel.setMyStream(stream);
                streams.add(stream);
                this.mediaCounter+=1;
            }
            channels.add(channel);
        }
        this.setChannels(channels);
        this.setStreams(streams);
        this.setVideos(videos);
        for(int i=0;i<numberOfUsers;i++){
            int randomNumberOne = random.nextInt(10);
            int randomNumberTwo = random.nextInt(10);
            String randomName=adjectives[randomNumberOne]+nouns[randomNumberTwo]+String.valueOf(i);
            User user=createUser(randomName);
            users.add(user);
            addSubscriptions(user);
        }
        this.setUsers(users);
        objectsCreated=true;
    }
    public void startSimulation() throws InterruptedException{
        if (objectsCreated==false){
            return;
        }
        while (time < 500) {
            synchronized (this) {
                this.time += 1;
                gui.updateVideoInfo();
                gui.updateStreamInfo();
                gui.updateStreamList();
                gui.updateVideosList();
                gui.updateUserInfo();
                gui.updateChannelInfo();
                
                System.out.println("Simulation Time: " + this.time);
                this.counter=0;
                this.channelCounter=0;
                this.notifyAll();
                System.out.println(this.mediaCounter);
            }
            if(time==startTime){
                for(int i=0;i<numberOfUsers;i++){
                    User user =this.users.get(i);
                    user.start();
                }
                for(int i=0;i<channels.size();i++){
                    Channel channel =this.channels.get(i);
                    channel.start();
                }
            }
            while(this.isStopped==true){
               
            }
            while(counter<numberOfUsers && channelCounter<channels.size()){
                //wait
            }
            
            Thread.sleep(1000);
            while(this.isStopped==true){
               
            }
//            try {
//                Thread.sleep(1000); // Simulate one second passing in the simulation
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        for(int i=0;i<videos.size();i++){
            Video vid=videos.get(i);
            System.out.println(vid.getVideoName());
            System.out.println(vid.getNumberOfViewers());
            System.out.println(vid.getNumberOfLikes());
            System.out.println(vid.getVideoLength());
        }
        System.out.println("streams");
        for(int i=0;i<streams.size();i++){
            Stream str=streams.get(i);
            System.out.println(str.getVideoName());
            System.out.println(str.getNumberOfViewers());
            System.out.println(str.getNumberOfLikes());
        }
        this.gui.setSimulation(null);
        this.gui.setRunning(false);
    }
    public void stopSimulation(){
        
    }
    public void resumeSimulation(){
        
    }
}
