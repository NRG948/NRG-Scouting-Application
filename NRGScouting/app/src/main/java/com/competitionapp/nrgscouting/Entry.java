package com.competitionapp.nrgscouting;

import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by valli on 8/2/17.
 * Represents a Entry that can be written to the memory card
 */

public class Entry {
    enum Position{RED1,RED2,RED3,BLUE1,BLUE2,BLUE3};
    Position position;
    String teamName;
    int matchNumber;
    int gearsRetrieved;
    int ballsShot;
    int autoGearsRetrieved;
    int autoBallsShot;
    double rating;
    boolean death;//True if it died
    boolean crossedBaseline;
    boolean climbsRope;
    boolean yellowOrRedCard;
    @Override
    public String toString(){
        return "match:"+matchNumber+"\t"+"gearsRet:"+gearsRetrieved+"\t"+"autoGearsRet:"+autoGearsRetrieved+"\t"+"ballsShot:"+ballsShot+"\t"
                +"autoBallsShot:"+autoBallsShot+"\t"+"rating:"+rating+"\t"+"death:"+death+"\t"+"crossedBaseline:"+crossedBaseline+"\t"+"climbsRope:"
                +climbsRope+"\t"+"Name:"+teamName+"\t"+"position:"+position+"\t"+"yellowOrRedCard:"+yellowOrRedCard+"\t"+"n";
    }
    public Entry(Position pos,String name,int match,int gears,int balls,int autoG,int autoB,double rating,boolean death,boolean crossedBaseline,boolean rope,boolean card){
        matchNumber=match;
        ballsShot=balls;
        gearsRetrieved=gears;
        autoGearsRetrieved=autoG;
        autoBallsShot=autoB;
        this.rating=rating;
        this.death=death;
        this.crossedBaseline=crossedBaseline;
        climbsRope=rope;
        teamName=name;
        position=pos;
        yellowOrRedCard=card;
    }
    public Entry(){

    }
}
