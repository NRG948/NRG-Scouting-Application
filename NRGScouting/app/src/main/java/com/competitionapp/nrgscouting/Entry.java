package com.competitionapp.nrgscouting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by valli on 8/2/17.
 */

public class Entry {
    String teamName;
    int matchNumber;
    int gearsRetrieved;
    int ballsShot;
    int autoGearsRetrieved;
    int autoBallsShot;
    int rating;
    boolean death;//True if it died
    boolean crossedBaseline;
    boolean climbsRope;
    public void writeEntry(File entries) throws FileNotFoundException {
        PrintStream printStream=new PrintStream(entries);
        printStream.print("match:"+matchNumber+"\t");
        printStream.print("gearsRet:"+gearsRetrieved+"\t");
        printStream.print("autoGearsRet:"+autoGearsRetrieved+"\t");
        printStream.print("ballsShot:"+ballsShot+"\t");
        printStream.print("autoBallsShot:"+autoBallsShot+"\t");
        printStream.print("rating:"+rating+"\t");
        printStream.print("death:"+death+"\t");
        printStream.print("crossedBaseline:"+crossedBaseline+"\t");
        printStream.print("climbsRope:"+climbsRope+"\t");
        printStream.print("name:"+teamName+"\t");
        printStream.println();
    }
    public Entry(String name,int match,int gears,int balls,int autoG,int autoB,int rating,boolean death,boolean crossedBaseline,boolean rope){
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
    }
    public Entry(){

    }
}
