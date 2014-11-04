package ball.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.game.ball.ballgame.R;
import com.melnykov.fab.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;
import ball.Adapters.CustomArrayAdapter;
import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;

public class StuntListFragment extends Fragment {

    public ArrayList<Stunt> stunts = null;
    private ListView listView;
    public Stunt selectedStunt;
    private int selectedStuntIndex;
    private SQLiteHelper db;
    private static CustomArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stunt_list_fragement, container, false);
        initializeDataSources();

        listView = (ListView) root.findViewById(R.id.stuntList);
        adapter = new CustomArrayAdapter(getActivity(), stunts);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                addStunt(v);
            }
         });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                selectedStuntIndex = position;
                selectedStunt = stunts.get((position));
                createDialog(selectedStunt);
            }
        });

        return root;
    }

    private void initializeDataSources() {

        db = new SQLiteHelper(getActivity());
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

    private void createDialog(final Stunt selectedStunt)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        final EditText input = new EditText(getActivity());
        input.setText(selectedStunt.stuntName);
        alert.setView(input);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                selectedStunt.stuntName = input.getText().toString();
                if (db.updateStunt(selectedStunt))
                    Toast.makeText(getActivity(), "Stunt has been updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "Stunt update failed.", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (db.deleteStunt(selectedStunt)) {
                    adapter.removeStunt(selectedStuntIndex);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Delete successful.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Delete failed.", Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

    public void addStunt(final View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText stuntInput = new EditText(getActivity());

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

                    Toast.makeText(getActivity(), "Stunt had been added", Toast.LENGTH_SHORT).show();
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
}