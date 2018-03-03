package com.competitionapp.nrgscouting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


/**
 * Created by nipunchhajed on 7/22/17.
 */

public class TeamSearchPop extends AppCompatActivity implements ActivityUtility{
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    boolean isEdit = false;

    String[] teams = {"360 - The Revolution", "488 - Team XBot", "492 - Titan Robotics Club", "568 - Nerds of the North",
            "753 - High Desert Droids", "847 - PHRED", "948 - NRG (Newport Robotics Group)", "949 - Wolverine Robotics",
            "955 - CV Robotics", "957 - SWARM", "997 - Spartan Robotics", "1258 - SeaBot", "1294 - Top Gun", "1318 - Issaquah Robotics Society",
            "1359 - Scalawags", "1425 - Error Code Xero", "1432 - Metal Beavers", "1510 - Wildcats", "1540 - Flaming Chickens",
            "1571 - CALibrate Robotics", "1595 - The Dragons", "1778 - Chill Out", "1899 - Saints Robotics",
            "1983 - Skunk Works Robotics", "2002 - Tualatin Robotics", "2046 - Bear Metal", "2147 - CHUCK", "2148 - Mechaknights",
            "2149 - CV Bearbots", "2374 - Crusader Bots", "2411 - Rebel @lliance", "2412 - Robototes", "2471 - Team Mean Machine",
            "2521 - SERT", "2522 - Royal Robotics", "2550 - Skynet", "2555 - RoboRams", "2557 - SOTAbots", "2605 - Sehome Seamonsters",
            "2635 - Lake Monsters", "2733 - Pigmice", "2811 - StormBots", "2898 - Flying Hedgehogs", "2903 - NeoBots",
            "2906 - Sentinel Prime Robotics", "2907 - Lion Robotics", "2910 - Jack in the Bot", "2915 - Pandamonium",
            "2923 - Aggies", "2926 - Robo Sparks", "2928 - Viking Robotics", "2929 - JAGBOTS", "2930 - Sonic Squirrels",
            "2942 - Panda ER", "2944 - Titanium Tigers", "2976 - Spartabots", "2980 - The Whidbey Island Wild Cats",
            "2990 - Hotwire", "3024 - My Favorite Team", "3049 - BremerTron", "3070 - Team Pronto", "3131 - Gladiators",
            "3218 - Panther Robotics", "3219 - TREAD", "3220 - Mechanics of Mayhem", "3223 - Robotics Of Central Kitsap",
            "3238 - Cyborg Ferrets", "3268 - Vahallabots", "3393 - Horns of Havoc", "3574 - HIGH TEKERZ", "3575 - Okanogan FFA",
            "3588 - the Talon", "3636 - Generals", "3663 - CPR - Cedar Park Robotics", "3673 - C.Y.B.O.R.G. Seagulls",
            "3674 - 4-H / CAM Academy CloverBots", "3684 - Electric Eagles", "3693 - GearHead Pirates", "3711 - Iron Mustang",
            "3712 - RoboCats", "3781 - Cardinal Robotics", "3786 - Chargers", "3789 - On Track Robotics", "3812 - Bits & Bots",
            "3826 - Sequim Robotics Federation \"SRF\"", "3876 - Mabton LugNutz", "4043 - NerdHerd", "4051 - Sabin-Sharks",
            "4057 - STEAMPUNK", "4060 - S.W.A.G.", "4061 - SciBorgs", "4077 - M*A*S*H", "4089 - Stealth Robotics",
            "4104 - Blackhawks", "4110 - DEEP SPACE NINERS", "4120 - Jagwires", "4125 - Confidential", "4127 - LoggerBots",
            "4131 - Iron Patriots", "4132 - Scotbots", "4173 - Bulldogs", "4180 - Iron Riders", "4205 - ROBOCUBS",
            "4309 - 4-H Botsmiths", "4450 - Olympia Robotics Federation", "4461 - Ramen", "4469 - R.A.I.D. (Raider Artificial Intelligence Division)",
            "4488 - Shockwave", "4495 - Kittitas County Robotics/ Team Haywire", "4512 - BEARbots", "4513 - Circuit Breakers",
            "4579 - RoboEagles", "4608 - Duct Tape Warriors", "4662 - Byte Sized Robotics", "4681 - Murphy's law",
            "4682 - BraveBots", "4683 - Full-metal Robotics", "4692 - Metal Mallards", "4726 - Robo Dynasty",
            "4911 - CyberKnights", "4915 - Spartronics", "4918 - The Roboctopi", "4980 - Canine Crusaders", "5085 - LakerBots",
            "5111 - SaxonBots", "5198 - Knight Tech", "5295 - Aldernating Current", "5450 - St. Helens Robotics and Engineering Club",
            "5468 - Chaos Theory", "5495 - Aluminati", "5588 - Holy Names Academy", "5683 - RAVE", "5748 - Octo π Rates",
            "5803 - Apex Robotics", "5827 - Code Purple", "5920 - VIKotics", "5937 - MI-Robotics", "5941 - SPQL",
            "5942 - Warriors", "5956 - Falcons", "5970 - BeaverTronics", "5975 - Beta Blues", "5977 - Rosemary Anderson H S/POIC",
            "6076 - Mustangs", "6129 - Shadle Park", "6343 - Steel Ridge Robotics", "6350 - J.E.D.I.",
            "6437 - The Pacific Quakers", "6442 - Modern Americans", "6443 - Falcons", "6445 - CTEC Robotics",
            "6456 - Oregon Trail Academy Wi-Fires", "6465 - Decipher", "6503 - Iron Dragon", "6696 - Cardinal Dynamics",
            "6845 - River Bots", "6959 - Centralia Circuit Breakers #6959", "7034 - 2B Determined", "7118 - ScotBots"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team_search);

        lv = (ListView)findViewById(R.id.teams_list);
        sv = (SearchView)findViewById(R.id.searchView);

        isEdit = getIntent().getBooleanExtra("isEdit",false);

        if(isEdit && getIntent().hasExtra("retrieveFrom")) {
            Intent intent = new Intent(TeamSearchPop.this, TabbedActivity.class);
            intent.putExtra("isEdit", true);
            intent.putExtra("retrieveFrom", getIntent().getStringExtra("retrieveFrom"));
            startActivityForResult(intent, 0);
        }


        adapter = new ArrayAdapter<String>(TeamSearchPop.this, android.R.layout.simple_list_item_1, teams);
        lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String teamName=(String)((AppCompatTextView)(view)).getText();
                        Intent intent = new Intent(TeamSearchPop.this, TabbedActivity.class);
                        intent.putExtra("isEdit", false);
                        intent.putExtra("teamName", teamName);

                        startActivityForResult(intent, 0);

                    }
                });

        //SearchView set up
        sv.setQueryHint("Search...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getIntExtra("status", 1) == -1) {
            //ERROR!!!
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uh oh!");
            builder.setCancelable(true);
            if(!isEdit) {
                builder.setMessage("There was an error loading the team name. Try again, please!");
            } else {
                builder.setMessage("There was an error loading this entry. Sorry about that!");
            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad = builder.create();
            ad.show();
        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
