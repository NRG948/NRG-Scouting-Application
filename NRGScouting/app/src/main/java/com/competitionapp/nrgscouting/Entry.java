package com.competitionapp.nrgscouting;

import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by valli on 8/2/17. Read/Approved by Acchindra.
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
    boolean death;//True if it died
    boolean crossedBaseline;
    boolean climbsRope;
    boolean yellowCard;
    boolean redCard;
    //0 is weak, 1 is average, 2 is strong.
    int defensiveStrategy;
    boolean chainProblems;
    boolean disconnectivity;
    boolean otherProblems;

    int foulPoints;

    @Override
    public String toString(){
        return "match:"+matchNumber+"\t"+"gearsRet:"+gearsRetrieved+"\t"+"autoGearsRet:"+autoGearsRetrieved+"\t"+"ballsShot:"+ballsShot+"\t"
                +"autoBallsShot:"+autoBallsShot+"\t"+"foulPoints:" + foulPoints + "\t" +"death:"+death+"\t"+"crossedBaseline:"+crossedBaseline+"\t"
                +"climbsRope:"+climbsRope+"\t"+"Name:"+teamName+"\t"+"position:"+position+"\t"+"yellowCard:"+yellowCard+ "\t"
                + "redCard:" + redCard + "\t" + "defensiveStrategy:" + defensiveStrategy + "\t"
                + "chainProblems:" + chainProblems + "\t" + "disconnectivity:" + disconnectivity + "\t"
                + "otherProblems:" + otherProblems + "\t" + "n";
    }
    public Entry(Position pos,String name,int match,int gears,int balls,int autoG,int autoB,
                 boolean death,boolean crossedBaseline,boolean rope,boolean yCard, boolean rCard, int dStrategy,
                 boolean chains, boolean disconnect, boolean other, int foulPointsAwarded){
        matchNumber=match;
        ballsShot=balls;
        gearsRetrieved=gears;
        autoGearsRetrieved=autoG;
        autoBallsShot=autoB;
        this.death=death;
        this.crossedBaseline=crossedBaseline;
        climbsRope=rope;
        teamName=name;
        position=pos;
        yellowCard=yCard;
        redCard = rCard;
        defensiveStrategy=dStrategy;
        chainProblems = chains;
        disconnectivity = disconnect;
        otherProblems = other;
        foulPoints = foulPointsAwarded;
    }
    public Entry(){

    }
}
