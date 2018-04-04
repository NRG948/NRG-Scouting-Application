package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 4/4/18.
 */

public class ExchangeAlgorithm extends Algorithm {

    public ExchangeAlgorithm() {
        this.exhangeScoreWeight = 1;
    }

    @Override
    public String rankType() {
        return "Exchange";
    }
}
