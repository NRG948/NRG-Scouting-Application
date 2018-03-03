package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class ClimbAlgorithm extends Algorithm {

    public ClimbAlgorithm() {
        this.soloClimbWeight = 30;
        this.astClimbWeight = 60;
        this.neededAstClimbWeight = 10;
        this.levitateWeight = 20;
    }

    @Override
    public String rankType() {
        return "Climb";
    }
}
