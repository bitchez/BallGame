package ball.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.game.ball.ballgame.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import ball.Adapters.CustomArrayAdapter;
import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;


public class RandomizeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_randomizer, container, false);
        return root;
    }

}
