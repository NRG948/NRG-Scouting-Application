package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class SwitchAlgorithm extends Algorithm {

    public SwitchAlgorithm() {
        this.switchScoreWeight = -1;
    }

    @Override
    public String rankType() {
        return "Switch";
    }
}
