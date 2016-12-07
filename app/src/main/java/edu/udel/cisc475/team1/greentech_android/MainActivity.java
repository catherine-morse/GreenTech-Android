package edu.udel.cisc475.team1.greentech_android;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseConnection firebaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseConnection = new FirebaseConnection();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment newFragment = new MapsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_1));

        transaction.replace(R.id.activity_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseConnection.firebaseOnStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseConnection.firebaseOnStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseConnection.firebaseSignIn();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment newFragment = new MapsFragment();
        if (id == R.id.nav_maps) {
            newFragment = new MapsFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_1));

        } else if (id == R.id.nav_educational) {
            newFragment = new EducationalFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_2));

        } else if (id == R.id.nav_search) {
            newFragment = new SearchFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_3));

        } else if (id == R.id.nav_upload) {
            newFragment = new UploadFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_4));

        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.activity_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
