package ball.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.game.ball.ballgame.R;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;

public class MainActivity extends Activity
{
    private List<Stunt> stunts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String packageName = getApplicationContext().getPackageName();
        initializeDataSources();
    }

    private void initializeDataSources() {

        SQLiteHelper db = new SQLiteHelper(this);
        // copy assets DB to app DB.
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        // get all stunts
        if ( db.open() ) {

            stunts = db.getStunts();

        } else {
            // error opening DB.
        }
    }

    public void randomizeStunt(View view)
    {
        Toast.makeText(this, "you are currently stuntin'", Toast.LENGTH_LONG).show();
        Random myRandomizer = new Random();
        Stunt random = stunts.get(myRandomizer.nextInt(stunts.size()));
        TextView stuntText  = (TextView) findViewById(R.id.stuntText);
        stuntText.setText(random.stuntName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
