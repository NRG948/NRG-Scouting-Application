package com.competitionapp.nrgscouting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragment extends Fragment {

int[] teams={948,949,950};

    public MatchFragment(){
     //Set the valid team numbers

    }

    public void search(View view){

        EditText textBox=(EditText)view.findViewById(R.id.editText2);
        for (int i:teams){
            if (Integer.toString(i).equals(textBox.getText()) ){
                System.out.println("SUCCESS!");
                //return true;
            }
        }
        //if it gets to this point, it means that the search failed
        System.out.println("FAILED!!");
        //return false;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_match, container, false);
    }

}
