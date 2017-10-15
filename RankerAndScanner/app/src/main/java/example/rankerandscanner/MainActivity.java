package example.rankerandscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.zxing.Result;

import java.util.Collection;
import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {
    private ZXingScannerView scannerView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView=(TextView)findViewById(R.id.text_view);
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
        textView=(TextView)findViewById(R.id.text_view);
        EntriesToTeamObjects.combineTeams();
        Ranker ranker=new Ranker();
        for(Team a:EntriesToTeamObjects.teams){
            a.rankScore=ranker.rankScore(ranker.teleopScore(a),ranker.autonomousScore(a));
        }
        System.out.print(EntriesToTeamObjects.teams);
        Collections.sort(EntriesToTeamObjects.teams);
        String toDisplay="";
        for(Team a:EntriesToTeamObjects.teams){
            toDisplay+="Team:"+a.name+" Score:"+a.rankScore+"\n";
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setLines(100);
        textView.setText(toDisplay);
        System.out.print(EntriesToTeamObjects.teams);
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