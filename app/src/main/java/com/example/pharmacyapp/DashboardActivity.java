package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

}