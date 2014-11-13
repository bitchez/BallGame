package ball.Activites;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import com.game.ball.ballgame.R;

import java.io.IOException;
import java.util.ArrayList;

import ball.Adapters.TabsPagerAdapter;
import ball.DataSources.SQLiteHelper;
import ball.Models.Stunt;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener
{
    public ArrayList<Stunt> stunts = null;

    private ViewPager viewPager;
    private TabsPagerAdapter pagerAdapter;
    private ActionBar actionBar;
    private String[] tabs = { "Stunts", "Randomize"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

         //on swiping the viewpager make respective tab selected
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
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

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
