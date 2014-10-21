package ball.Activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

    public void randomizeStunt(final View view)
    {
        Random myRandomizer = new Random();
        Stunt stunt = stunts.get(myRandomizer.nextInt(stunts.size()));
        TextView stuntText  = (TextView) findViewById(R.id.stuntText);
<<<<<<< HEAD
        stuntText.setText(stunt.stuntName);
        new AlertDialog.Builder(this)
                .setTitle("Here is your Stunt!")
                .setMessage(stunt.stuntName)
=======
        stuntText.setText(random.stuntName);
        new AlertDialog.Builder(this)
                .setTitle("Here is your Stunt!")
                .setMessage(random.stuntName)
>>>>>>> 8123e4f3495c0cc71f808a4540140f53952a8d53
                .setPositiveButton("Keep this Stunt" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Delete this Stunt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //This is where the stunt will be deleted from the database
                        //After deletion, the dialog will close the dialogue box
                        //And show a toast saying stunt has been deleted
                        Toast.makeText(getApplicationContext(), "Stunt has been deleted", Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                })
                .show();
<<<<<<< HEAD
=======

>>>>>>> 8123e4f3495c0cc71f808a4540140f53952a8d53
    }

    public void addStunt(final View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText stuntInput = new EditText(this);

        alert.setTitle("Add New Stunt");
        alert.setMessage("Type in a new Stunt and select ok to save.");
        alert.setView(stuntInput);
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (stuntInput.getText().toString().trim().equals("")) {
<<<<<<< HEAD
                    stuntInput.setError("Stunt field cannot be null");
                }
                else {
                    String newStunt = stuntInput.getText().toString();
                    //Add the new Stunt to the database
=======
                    Toast.makeText(getApplicationContext(), "Stunt field cannont be blank! Please try again.", Toast.LENGTH_LONG).show();
                    dialogInterface.cancel();
                }
                else {
                    String newStunt = stuntInput.getText().toString();

                    //Add the new Stunt to the database

                    Toast.makeText(getApplicationContext(), "Stunt had been added!", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
>>>>>>> 8123e4f3495c0cc71f808a4540140f53952a8d53
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
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
