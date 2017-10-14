package example.rankerandscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.zxing.Result;

import java.util.Collection;
import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {
    TextView rankedData=(TextView)findViewById(R.id.Rank);
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void scanCode(View v){
        scannerView=new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();
    }
    @Override
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }
    public void rank(View v){
        EntriesToTeamObjects.combineTeams();
        Ranker ranker=new Ranker();
        for(Team a:EntriesToTeamObjects.teams){
            a.rankScore=ranker.rankScore(ranker.teleopScore(a),ranker.autonomousScore(a));
        }
        Collections.sort(EntriesToTeamObjects.teams);
        String toDisplay="";
        for(Team a:EntriesToTeamObjects.teams){
            toDisplay+="Team:"+a.name+" Score:"+a.rankScore+"\n";
        }
        rankedData.setText(toDisplay);
    }
    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(Result result) {
            EntriesToTeamObjects.addEntry(result.getText());
            Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();
            EntriesToTeamObjects.combineTeams();
            setContentView(R.layout.activity_main);
            scannerView.stopCamera();
        }
    }
}
