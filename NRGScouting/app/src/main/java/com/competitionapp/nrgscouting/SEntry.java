package com.competitionapp.nrgscouting;

/**
 * Created by Peyton Lee on 9/21/2017. 
 */

public class SEntry {

    enum Position{RED1,RED2,RED3,BLUE1,BLUE2,BLUE3};
    Position position;
    String teamName;
    int matchNumber;
    int pilotFouls;
    int intentFouls;
    double driverSkill;
    boolean autoGear;
    String autoGearComment = "";
    double GPRating;
    double reliability;
    double antagonism;
    double ropeDropTime;
    String specComments = "";

    public SEntry(Position pos, String name, int match, int pFouls, int iFouls, double dSkill,
                  boolean aGear, String aGearComment, double GPRating, double reliability,
                  double antagonism, double ropeDropTime, String specComments) {
        this.position = pos;
        this.teamName = name;
        this.matchNumber = match;
        this.pilotFouls = pFouls;
        this.intentFouls = iFouls;
        this.driverSkill = dSkill;
        this.autoGear = aGear;
        this.autoGearComment = aGearComment;
        this.GPRating = GPRating;
        this.reliability = reliability;
        this.antagonism = antagonism;
        this.ropeDropTime = ropeDropTime;
        this.specComments = specComments;
    }

    @Override
    public String toString() {
        return "match:" + matchNumber + "\tposition:" + position + "\tName:" + teamName + "\tpilotFouls:" + pilotFouls + "\tintentFouls:" + intentFouls
                + "\tdriverSkill:" + driverSkill + "\tautoGear:" + autoGear + "\tautoGearComment:" + autoGearComment + "\tGPRating:" + GPRating +
                "\treliability:" + reliability + "\tantagonism:" + antagonism + "\tropeDropTime:" + ropeDropTime +
                "\tspecComments:" + specComments + "\tn";
    }

    public static SEntry Deserialize(String input) {
        String[] properties = input.split("\t");
        SEntry entry = new SEntry();
        //properties[1].split(":")[1]
        entry.matchNumber = Integer.parseInt(properties[0].split(":")[1]);
        entry.position = SEntry.getPosition(properties[1].split(":")[1]);
        entry.teamName = properties[2].split(":")[1];
        entry.pilotFouls = Integer.parseInt(properties[3].split(":")[1]);
        entry.intentFouls = Integer.parseInt(properties[4].split(":")[1]);
        entry.driverSkill = Double.parseDouble(properties[5].split(":")[1]);
        entry.autoGear = Boolean.parseBoolean(properties[6].split(":")[1]);
        //if no string is found, the app crashes
        if(properties[7].split(":")[0].length() < properties[7].length() - 1) {
            entry.autoGearComment = String.valueOf(properties[7].split(":")[1]);
        } else {
            entry.autoGearComment = "";
        }
        entry.GPRating = Double.parseDouble(properties[8].split(":")[1]);
        entry.reliability = Double.parseDouble(properties[9].split(":")[1]);
        entry.antagonism = Double.parseDouble(properties[10].split(":")[1]);
        entry.ropeDropTime = Double.parseDouble(properties[11].split(":")[1]);
        if(properties[12].split(":")[0].length() < properties[12].length() - 1) {
            entry.specComments = String.valueOf(properties[12].split(":")[1]);
        } else {
            entry.specComments = "";
        }

        return entry;
    }

    public static SEntry.Position getPosition(int row){
        if (row == 0)
            return SEntry.Position.RED1;
        else if (row == 1)
            return SEntry.Position.RED2;
        else if (row == 2)
            return SEntry.Position.RED3;
        else if (row == 3)
            return SEntry.Position.BLUE1;
        else if (row == 4)
            return SEntry.Position.BLUE2;
        return SEntry.Position.BLUE3;
    }

    public static SEntry.Position getPosition(String str){
        if (str.equals("RED1"))
            return SEntry.Position.RED1;
        else if (str.equals("RED2"))
            return SEntry.Position.RED2;
        else if (str.equals("RED3"))
            return SEntry.Position.RED3;
        else if (str.equals("BLUE1"))
            return SEntry.Position.BLUE1;
        else if (str.equals("BLUE2"))
            return SEntry.Position.BLUE2;
        return SEntry.Position.BLUE3;
    }

    public SEntry() {

    }

}
