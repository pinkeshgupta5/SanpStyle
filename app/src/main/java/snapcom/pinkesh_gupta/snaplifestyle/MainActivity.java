package snapcom.pinkesh_gupta.snaplifestyle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import snapcom.pinkesh_gupta.snaplifestyle.Fragment.BrowseFragment;
import snapcom.pinkesh_gupta.snaplifestyle.Fragment.HomeFragment;
import snapcom.pinkesh_gupta.snaplifestyle.Fragment.PaymentFragment;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.ScanResultReceiver;

import static snapcom.pinkesh_gupta.snaplifestyle.Fragment.FaceDetectionFragment.first;
import static snapcom.pinkesh_gupta.snaplifestyle.Fragment.FaceDetectionFragment.second;
import static snapcom.pinkesh_gupta.snaplifestyle.Fragment.FaceDetectionFragment.third;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView label;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        label = (TextView) findViewById(R.id.tv_mainLabel);
        toolbar.setTitle("SNAP");
        label.setText(toolbar.getTitle());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.container, homeFragment, "homescreen");
        fragmentTransaction.commit();

    }

    @SuppressLint("RestrictedApi")
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
        if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.container, homeFragment, "homescreen");
            fragmentTransaction.commit();
            toolbar.setTitle("SNAP");
            label.setText(toolbar.getTitle());
        } else if (id == R.id.nav_browse) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            BrowseFragment browseFragment = new BrowseFragment();
            if (browseFragment instanceof ScanResultReceiver) {
                //browseFragment.scanResultData(codeContent);
                browseFragment.facedetectionResultData(first, second, third);
            }
            fragmentTransaction.add(R.id.container, browseFragment, "browse");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack("null");
            fragmentTransaction.commit();
            toolbar.setTitle("BROWSE");
            label.setText(toolbar.getTitle());


        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_payment) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PaymentFragment paymentFragment = new PaymentFragment();
            fragmentTransaction.replace(R.id.container, paymentFragment, "paymentscreen");
            fragmentTransaction.commit();
            toolbar.setTitle("PAYMNET");
            label.setText(toolbar.getTitle());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
