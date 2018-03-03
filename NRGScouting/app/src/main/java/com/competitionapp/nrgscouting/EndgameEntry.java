package com.competitionapp.nrgscouting;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import static java.lang.Integer.parseInt;

/**
 * Created by Peyton Lee on 2/10/2018.
 */

public class EndgameEntry extends Fragment {
    EditText matchNumber;
    EditText penalities;
    Spinner teamPosition;
    RadioGroup defensiveStrategy;
    CheckBox death;
    CheckBox soloClimb;
    CheckBox astClimb;
    CheckBox needAstClimb;
    CheckBox needLevitate;
    CheckBox cardYellow;
    CheckBox cardRed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_endgame, container, false);

    }

    public void onStart() {
        super.onStart();

        teamPosition = (Spinner) getView().findViewById(R.id.teamPosition);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(sharedPref.contains("DefaultTeamPosition")) {
            teamPosition.setSelection(sharedPref.getInt("DefaultTeamPosition", 0));
        }

        matchNumber = (EditText) getView().findViewById(R.id.matchNumber);
        penalities = (EditText) getView().findViewById(R.id.penalties);

        defensiveStrategy = (RadioGroup) getView().findViewById(R.id.defensiveStrategyStrength);
        death = (CheckBox) getView().findViewById(R.id.death);
        soloClimb = (CheckBox) getView().findViewById(R.id.soloClimb);
        astClimb = (CheckBox) getView().findViewById(R.id.astClimb);
        needAstClimb = (CheckBox) getView().findViewById(R.id.needAstClimb);
        needLevitate = (CheckBox) getView().findViewById(R.id.needLevitate);
        soloClimb = (CheckBox) getView().findViewById(R.id.soloClimb);
        cardYellow = (CheckBox) getView().findViewById(R.id.cardyellow);
        cardRed = (CheckBox) getView().findViewById(R.id.cardred);
    }

    public void loadFromEntry (Entry newEntry) {
        matchNumber.setText(String.valueOf(newEntry.matchNumber));
        penalities.setText(String.valueOf(newEntry.penalties));
        teamPosition.setVerticalScrollbarPosition(newEntry.position);
        defensiveStrategy.check(defensiveStrategy.getChildAt(newEntry.defensiveStrategy).getId());
        death.setChecked(newEntry.death);
        soloClimb.setChecked(newEntry.soloClimb);
        astClimb.setChecked(newEntry.astClimb);
        needAstClimb.setChecked(newEntry.needAstClimb);
        needLevitate.setChecked(newEntry.needLevitate);
        cardYellow.setChecked(newEntry.cardYellow);
        cardRed.setChecked(newEntry.cardRed);
    }

    public Entry saveToEntry(Entry entry) {

        try {
            entry.matchNumber = Integer.parseInt(String.valueOf(matchNumber.getText()));
            entry.penalties = Integer.parseInt(String.valueOf(penalities.getText()));
        } catch (NumberFormatException p_ex) {
            entry.matchNumber = -1;
            entry.penalties = -1;
        }
        entry.position = teamPosition.getSelectedItemPosition();
        entry.defensiveStrategy = defensiveStrategy.indexOfChild(
                defensiveStrategy.findViewById(defensiveStrategy.getCheckedRadioButtonId()));
        entry.death = death.isChecked();
        entry.soloClimb = soloClimb.isChecked();
        entry.astClimb = astClimb.isChecked();
        entry.needAstClimb = needAstClimb.isChecked();
        entry.needLevitate = needLevitate.isChecked();
        entry.cardYellow = cardYellow.isChecked();
        entry.cardRed = cardRed.isChecked();
        return entry;
    }

}
