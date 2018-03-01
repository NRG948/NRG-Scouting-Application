package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class ScaleAlgorithm extends Algorithm {

    public ScaleAlgorithm() {
        this.autoScaleWeight = 8;
        this.scaleWeight = 4;
    }

    @Override
    public String rankType() {
        return "Scale";
    }

}
