package com.muthusoft.edi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.muthusoft.edi.adapter.RecyclerAdapter;
import com.muthusoft.edi.helper.DataBaseHelper;
import com.muthusoft.edi.model.Users;

import java.util.ArrayList;
import java.util.List;


public class User_Profile extends AppCompatActivity {

    DataBaseHelper db;
    private List<Users> stringArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setData(); //adding data to array list
        adapter = new RecyclerAdapter(this, stringArrayList);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                        LayoutInflater li = LayoutInflater.from(User_Profile.this);
                        View promptsView = li.inflate(R.layout.alertbox_profileview, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(User_Profile.this);
                        alertDialogBuilder.setView(promptsView);
                        final AlertDialog alertDialog = alertDialogBuilder.create();
                        ImageButton dialogButton = (ImageButton) promptsView.findViewById(R.id.btnClose);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });
                        // create alert dialog

                        // show it
                        alertDialog.show();
                    }
                });
            }
        });
    }

    private void setData() {
        stringArrayList = new ArrayList<>();
        db = new DataBaseHelper(User_Profile.this);
        Users users = db.getUser();
        stringArrayList.add(new Users(users.getName(), "NAME  "));
        stringArrayList.add(new Users(users.getEmail(), "Email  "));
        stringArrayList.add(new Users(users.getMobile(), "Mobile No "));

    }
}
