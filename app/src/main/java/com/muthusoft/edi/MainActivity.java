package com.muthusoft.edi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muthusoft.edi.adapter.MentorAdapter;
import com.muthusoft.edi.helper.DataBaseHelper;
import com.muthusoft.edi.manager.Edimanager;
import com.muthusoft.edi.model.Mentor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHelper db;
    ListView mentorlist;
    private String MyPREFERENCES = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences mPrefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String sa = mPrefs.getString("LOGIN", null);
        System.out.println("SAHRED PREPARENCE : " + sa);
        if (sa == null) {
            Intent ia = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(ia);
        } else {

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    Intent ia = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(ia);
                }
            });
            mentorlist = (ListView) findViewById(R.id.mentorlist);

            final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
            swipeView.setEnabled(false);
            Log.v("MEntor List", String.valueOf(db.getMentorCount()));
            listdata();
            swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeView.setRefreshing(true);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mentorlist.setAdapter(null);
                            listdata();
                            swipeView.setRefreshing(false);

                        }
                    }, 3000);
                }
            });
            mentorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Mentor mentor = (Mentor) mentorlist.getItemAtPosition(position);
              /*  Intent viewComplaint = new Intent(MainActivity.this, ViewDetailedComplaint.class);
                viewComplaint.putExtra("ComplaintID", String.valueOf(compliant.getComplaint_id()));
                viewComplaint.putExtra("Completeddate", compliant.getCompleted_date());*/
                    // startActivity(viewComplaint);
                }
            });
            mentorlist.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {

                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem == 0)
                        swipeView.setEnabled(true);
                    else
                        swipeView.setEnabled(false);
                }
            });

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (Edimanager.isRefreshRequired == true) {
                        Edimanager.isRefreshRequired = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                mentorlist.setAdapter(null);
                                listdata();
                            }
                        });


                    }

                }
            }, 0, 1000);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent userprofile = new Intent(MainActivity.this, User_Profile.class);
            startActivity(userprofile);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.change_password) {
            Intent changepass = new Intent(MainActivity.this, ChangePassword.class);
            startActivity(changepass);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.logout) {
            Intent logout = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logout);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        Thread.interrupted();
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();


/*
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something here
                mentorlist.setAdapter(null);
                listdata();
            }
        }, 200);
*/


    }

    private void listdata() {
        db = new DataBaseHelper(MainActivity.this);
        List<Mentor> mentors = new ArrayList<Mentor>();
        for (Mentor mentor : db.getAllMentor()) {

            mentors.add(mentor);
        }
        MentorAdapter mentorAdapter = new MentorAdapter(MainActivity.this, mentors);
        mentorlist.setAdapter(mentorAdapter);
        db.close();
    }
}
