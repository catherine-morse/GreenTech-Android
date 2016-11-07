package edu.udel.cisc475.team1.greentech_android;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment f = getFragmentManager().findFragmentById(R.id.activity_main);
                if (f instanceof EducationalFragment) {
                    // do something with f
                    Snackbar.make(view, "Add button pressed in educational tab", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //inflater.inflate(R.layout.add_entry_educational, f, false);
                } else {
                    Snackbar.make(view, "Add button pressed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Create new fragment and transaction
        Fragment newFragment = new MapsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_item_1));

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.activity_main, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        GoogleApiClient.OnConnectionFailedListener onfail = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        };

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity ,
                onfail /* OnConnectionFailedListener ).addApi(Drive.API).addScope(Drive.SCOPE_FILE).build();
    }*/

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
        getMenuInflater().inflate(R.menu.main, menu);
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

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.activity_main, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.googleApiClient;
    }
}
