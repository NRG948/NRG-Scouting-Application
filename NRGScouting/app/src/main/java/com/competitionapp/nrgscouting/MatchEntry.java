package com.competitionapp.nrgscouting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchEntry extends Fragment {
    private EditText editText, editText1, editText2, editText3, editText4;
    private Button button, button1, button2, button3, button4, button5, button6, button7;
    private Spinner spinner;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private RatingBar ratingBar;

// hi dudes
    public MatchEntry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_entry, container, false);
    }

}
//sup dudes