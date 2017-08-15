package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.FragmentManager;

import static com.competitionapp.nrgscouting.R.id.matchEntry;
import static com.competitionapp.nrgscouting.R.id.toolbar;

/**
 * Created by nipunchhajed on 7/22/17.
 */

public class TeamSearchPop extends AppCompatActivity {
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"3238 - Cyborg Ferrets", "2471 - Team Mean Machine", "2046 - Bear Metal", "1595 - The Dragons",
            "3663 - CPR - Cedar Park Robotics", "5803 - Apex Robotics", "1778 - Chill Out", "492 - Titan Robotics Club",
            "4488 - Shockwave", "4662 - Byte Sized Robotics", "997 - Spartan Robotics", "2910 - Jack in the Bot",
            "1425 - Error Code Xero", "2907 - Lion Robotics", "2928 - Viking Robotics", "4915 - Spartronics",
            "3024 - My Favorite Team", "4469 - R.A.I.D. (Raider Artificial In...", "1540 - Flaming Chickens",
            "948 - NRG (Newport Robotics Group)", "4125 - Confidential", "5295 - Aldernating Current", "4061 - SciBorgs",
            "3674 - 4-H / CAM Academy CloverBots", "6445 - CTEC Robotics", "2550 - Skynet", "4513 - Circuit Breakers",
            "2990 - Hotwire", "1318 - Issaquah Robotics Society", "5970 - BeaverTronics", "753 - High Desert Droids",
            "5920 - VIKotics", "488 - Team XBot", "2147 - CHUCK", "5588 - Holy Names Academy", "2930 - Sonic Squirrels",
            "5468 - Chaos Theory", "1294 - Top Gun", "3219 - TREAD", "4911 - CyberKnights", "4918 - The Roboctopi",
            "955 - CV Robotics", "6343 - Steel Ridge Robotics", "2557 - SOTAbots", "5827 - Code Purple", "2811 - StormBots",
            "4450 - Olympia Robotics Federation", "2374 - Crusader Bots", "5198 - Knight Tech", "957 - SWARM",
            "3223 - Robotics Of Central Kitsap", "5975 - Beta Blues", "3711 - Iron Mustang", "5450 - St. Helens Robotics and Engine...",
            "4173 - Bulldogs", "3070 - Team Pronto", "5085 - LakerBots", "4495 - Kittitas County Robotics/ Team...",
            "3693 - GearHead Pirates", "4131 - Iron Patriots", "3393 - Horns of Havoc", "2521 - SERT", "6350 - The Enumclaw Hornets",
            "5937 - MI-Robotics", "2148 - Mechaknights", "1432 - Metal Beavers", "3826 - Sequim Robotics Federation \"SR...",
            "3588 - the Talon", "6076 - Mustangs", "4681 - Murphy's law", "2605 - Sehome Seamonsters", "4309 - 4-H Botsmiths",
            "4077 - M*A*S*H", "360 - The Revolution", "4060 - S.W.A.G.", "6129 - Shadle Park", "1359 - Scalawags",
            "4110 - DEEP SPACE NINERS", "1899 - Saints Robotics", "1983 - Skunk Works Robotics", "2412 - Robototes",
            "5942 - Warriors", "4980 - Canine Crusaders", "4043 - NerdHerd", "2635 - Lake Monsters", "3131 - Gladiators",
            "5495 - Aluminati", "4089 - Stealth Robotics", "2980 - The Whidbey Island Wild Cats", "5977 - Rosemary Anderson H S/POIC",
            "5748 - Octo π Rates", "2915 - Pandamonium", "3636 - Generals", "4104 - Blackhawks", "2923 - Aggies", "2903 - NeoBots",
            "6696 - Cardinal Dynamics", "3220 - Mechanics of Mayhem", "847 - PHRED", "2976 - Spartabots", "6443 - Falcons",
            "3574 - HIGH TEKERZ", "3218 - Panther Robotics", "5941 - SPQL", "6442 - Modern Americans", "6503 - Iron Dragon",
            "4692 - Metal Mallards", "4608 - Duct Tape Warriors", "2411 - Rebel @lliance", "3575 - Okanogan FFA",
            "2944 - Titanium Tigers", "6437 - The Pacific Quakers", "2149 - CV Bearbots", "6465 - Decipher",
            "2522 - Royal Robotics", "2929 - JAGBOTS", "3786 - Chargers", "4682 - BraveBots", "2898 - Flying Hedgehogs",
            "6456 - Oregon Trail Academy Wi-Fires", "4512 - BEARbots", "3812 - Bits & Bots", "2906 - Sentinel Prime Robotics",
            "1510 - Wildcats", "4579 - RoboEagles", "4051 - Sabin-Sharks", "3268 - Vahallabots", "1258 - SeaBot",
            "3789 - On Track Robotics", "568 - Nerds of the North", "4057 - STEAMPUNK", "2733 - Pigmice", "3781 - Cardinal Robotics",
            "2926 - Robo Sparks", "5683 - RAVE", "4127 - LoggerBots", "4461 - Ramen", "4205 - ROBOCUBS", "3684 - Electric Eagles",
            "3049 - BremerTron", "2555 - RoboRams", "4180 - Iron Riders", "2942 - Panda ER", "5111 - SaxonBots", "4726 - Robo Dynasty",
            "3876 - Mabton LugNutz", "3673 - C.Y.B.O.R.G. Seagulls", "4132 - Scotbots", "1571 - CALibrate Robotics",
            "4120 - Jagwires", "2002 - Tualatin Robotics", "4683 - Full-metal Robotics", "949 - Wolverine Robotics",
            "3712 - RoboCats", "5956 - Falcons"};
    
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team_search);

        lv = (ListView)findViewById(R.id.teams_list);
        sv = (SearchView)findViewById(R.id.searchView);

        adapter = new ArrayAdapter<String>(TeamSearchPop.this, android.R.layout.simple_list_item_1, teams);
        lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MatchEntry matchEntry = new MatchEntry();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.special_container, matchEntry);
                        fragmentTransaction.commit();
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
}
