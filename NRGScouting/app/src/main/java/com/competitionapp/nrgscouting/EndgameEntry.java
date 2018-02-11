package com.competitionapp.nrgscouting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

/**
 * Created by Peyton Lee on 2/10/2018.
 */

public class EndgameEntry extends Fragment {

    RadioGroup defensiveStrategyStrength;
    CheckBox death;
    CheckBox soloClimb;
    CheckBox astClimb;
    CheckBox needAstClimb;
    CheckBox powerClimb;
    CheckBox needLevitate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_endgame, container, false);

    }

    public void onStart() {
        super.onStart();

        defensiveStrategyStrength = (RadioGroup) getView().findViewById(R.id.defensiveStrategyStrength);
        death = (CheckBox) getView().findViewById(R.id.death);
        soloClimb = (CheckBox) getView().findViewById(R.id.soloClimb);
        astClimb = (CheckBox) getView().findViewById(R.id.astClimb);
        needAstClimb = (CheckBox) getView().findViewById(R.id.needAstClimb);
        soloClimb = (CheckBox) getView().findViewById(R.id.defensiveStrategyStrength);
        soloClimb = (CheckBox) getView().findViewById(R.id.defensiveStrategyStrength);

    }

    public Entry saveToEntry(Entry entry) {



        return entry;
    }

}
