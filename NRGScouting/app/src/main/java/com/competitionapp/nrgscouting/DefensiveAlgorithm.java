package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class DefensiveAlgorithm extends Algorithm {

    public DefensiveAlgorithm() {
        this.DefenseWeight = 40;
    }

    @Override
    public String rankType() {
        return "Defense";
    }
}
