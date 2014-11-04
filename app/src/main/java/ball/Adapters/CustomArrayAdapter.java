package ball.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.game.ball.ballgame.R;
import java.util.ArrayList;
import ball.Models.Stunt;

public class CustomArrayAdapter extends BaseAdapter {

    private ArrayList<Stunt> items;
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<Stunt> stunts) {
        super();
        this.items = stunts;
        this.context = context;
    }

    @Override
    public int getCount() {
        int size = items.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeStunt (int position) {
        this.items.remove(position);
    }

    public void addStunt (Stunt stunt) {
        this.items.add(stunt);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.stunt_row, parent, false);
    }

        Stunt stunt = items.get(position);
        if (stunt != null)
        {
            TextView stuntText = (TextView) row.findViewById(R.id.stuntText);

            if (stuntText != null) {
                stuntText.setText(stunt.stuntName);
            }
        }

        return row;
    }
}