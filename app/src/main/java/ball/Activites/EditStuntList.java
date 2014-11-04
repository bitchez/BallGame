package ball.Activites;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import ball.Adapters.CustomArrayAdapter;
import com.game.ball.ballgame.R;
import android.widget.ImageButton;
import com.melnykov.fab.FloatingActionButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;

public class EditStuntList extends FragmentActivity  {

    public ArrayList<Stunt> stunts = null;
    private ListView listView;
    public Stunt selectedStunt;
    private int selectedStuntIndex;
    private SQLiteHelper db;
    private static CustomArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stunt_list);

        initializeDataSources();

        listView = (ListView) findViewById(R.id.stuntList);
        adapter = new CustomArrayAdapter(this, stunts);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                selectedStuntIndex = position;
                selectedStunt = stunts.get((position));
                createDialog(selectedStunt);
            }
        });
    }

    private void createDialog(final Stunt selectedStunt)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setText(selectedStunt.stuntName);
        alert.setView(input);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                selectedStunt.stuntName = input.getText().toString();
                if (db.updateStunt(selectedStunt))
                    Toast.makeText(getApplicationContext(), "Stunt has been updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Stunt update failed.", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (db.deleteStunt(selectedStunt)) {
                    adapter.removeStunt(selectedStuntIndex);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Delete successful.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Delete failed.", Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

    private void initializeDataSources() {

        db = new SQLiteHelper(this);
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

    public void addStunt(final View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText stuntInput = new EditText(this);

        alert.setTitle("New Stunt");
        alert.setView(stuntInput);
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (stuntInput.getText().toString().trim().equals("")) {
                    String newStunt = stuntInput.getText().toString();
                    stuntInput.setError("You need to actually type something");
                }
                else
                {
                    Stunt newStunt = new Stunt();
                    newStunt.stuntName = stuntInput.getText().toString();
                    int stuntId = stunts.size() + 1;
                    newStunt.stuntId = stuntId;

                    try
                    {
                        db.insertStunt(newStunt);
                        adapter.addStunt(newStunt);
                    }
                    catch (SQLiteConstraintException e)  {
                        Log.e("TaG", "SQLiteException:" + e.getMessage());
                    }

                    Toast.makeText(getApplicationContext(), "Stunt had been added", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
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

    public void exitList (View view)
    {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_stunt_list, menu);
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


