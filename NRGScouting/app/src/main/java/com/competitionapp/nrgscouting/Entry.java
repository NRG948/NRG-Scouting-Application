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
    public void writeEntry(File entries) throws FileNotFoundException {
        PrintStream printStream=new PrintStream(entries);
        printStream.print("match:"+matchNumber+"\t");
        printStream.print("position:"+position);
        printStream.print("gearsRet:"+gearsRetrieved+"\t");
        printStream.print("autoGearsRet:"+autoGearsRetrieved+"\t");
        printStream.print("ballsShot:"+ballsShot+"\t");
        printStream.print("autoBallsShot:"+autoBallsShot+"\t");
        printStream.print("rating:"+rating+"\t");
        printStream.print("death:"+death+"\t");
        printStream.print("crossedBaseline:"+crossedBaseline+"\t");
        printStream.print("climbsRope:"+climbsRope+"\t");
        printStream.print("name:"+teamName+"\t");
        printStream.println();//One entry per line
    }
    public Entry(Position pos,String name,int match,int gears,int balls,int autoG,int autoB,double rating,boolean death,boolean crossedBaseline,boolean rope){
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
    }
    public Entry(){

    }
}
