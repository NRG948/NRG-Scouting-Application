package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class ScaleAlgorithm extends Algorithm {

    public ScaleAlgorithm() {
        this.scaleScoreWeight = 1;
    }
    @Override
    public String rankType() {
        return "Scale";
    }

}
