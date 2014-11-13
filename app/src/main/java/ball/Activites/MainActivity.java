package ball.Activites;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.game.ball.ballgame.R;

import java.io.IOException;
import java.util.ArrayList;

import ball.DataSources.SQLiteHelper;
import ball.Fragments.RandomizeFragment;
import ball.Fragments.StuntListFragment;
import ball.Models.Stunt;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener
{
    public ArrayList<Stunt> stunts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
    }

    @SuppressWarnings("deprecation")
    private void initActionBar() {
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText("Stunts")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new StuntListFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
                    }));
            actionBar.addTab(actionBar.newTab()
                    .setText("Randomize")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new RandomizeFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
                    }));
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new StuntListFragment())
                        .commit();
                return true;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new RandomizeFragment())
                        .commit();
                return true;
            default:
                return false;
        }
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
            throw new Error("Unable to get stunts");
        }
    }

//    public void randomizeStunt(View view)
//    {
//        Toast.makeText(this, "you are currently stuntin'", Toast.LENGTH_LONG).show();
//        Random myRandomizer = new Random();
//        Stunt random = stunts.get(myRandomizer.nextInt(stunts.size()));
//        TextView stuntText  = (TextView) findViewById(R.id.stuntText);
//        stuntText.setText(random.stuntName);
////        new AlertDialog.Builder(this)
////                .setTitle("Here is your Stunt!")
////                .setMessage(random.stuntName)
////                .setPositiveButton("Keep this Stunt" , new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.cancel();
////                    }
////                })
////                .setNegativeButton("Delete this Stunt", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        //This is where the stunt will be deleted from the database
////                        //After deletion, the dialog will close the dialogue box
////                        //And show a toast saying stunt has been deleted
////                        Toast.makeText(getApplicationContext(), "Stunt has been deleted", Toast.LENGTH_SHORT).show();
////                        dialogInterface.cancel();
////                    }
////                })
////                .show();
//    }

//    public void editStuntList (View view)
//    {
//        Intent intent = new Intent(this, EditStuntList.class);
//        startActivity(intent);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.about) {
//            TextView content = (TextView) getLayoutInflater().inflate(R.layout.about_view, null);
//            content.setMovementMethod(LinkMovementMethod.getInstance());
//            content.setText(Html.fromHtml(getString(R.string.about_body)));
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.about)
//                    .setView(content)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).create().show();
//        }
        return super.onOptionsItemSelected(item);
    }
}
