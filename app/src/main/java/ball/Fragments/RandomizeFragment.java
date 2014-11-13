package ball.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.ball.ballgame.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;

public class RandomizeFragment extends Fragment {

    private ProgressBar spinner;
    private SQLiteHelper db;
    public ArrayList<Stunt> stunts = null;
    private TextView selectedStunt;
    private Button radomizerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_randomizer, container, false);
        spinner = (ProgressBar)root.findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        selectedStunt = (TextView) root.findViewById((R.id.randomStuntText));
        radomizerButton = (Button) root.findViewById((R.id.radomizeButton));
        radomizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                selectRandomStunt();
            }
        });
        return root;
    }

    public void selectRandomStunt() {
        spinner.setVisibility(View.VISIBLE);
        getStunts();
        Random random = new Random();
        Stunt randomStunt = stunts.get(random.nextInt(stunts.size()));
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        selectedStunt.setTextColor(color);
        selectedStunt.setText(randomStunt.stuntName.toString());
        spinner.setVisibility(View.GONE);
    }

    private void getStunts() {

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

        Collections.shuffle(stunts);
    }
}
