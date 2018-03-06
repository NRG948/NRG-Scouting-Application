package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class SwitchAlgorithm extends Algorithm {

    public SwitchAlgorithm() {
        this.autoAllySwitchWeight = 4;
        this.autoOppSwitchWeight = 8;
        this.allySwitchWeight = 2;
        this.oppSwitchWeight = 4;
        this.dropNoneScore = noneScore += ((dropNone - cube) / numDropNone);
    }

    @Override
    public String rankType() {
        return "Switch";
    }
}
