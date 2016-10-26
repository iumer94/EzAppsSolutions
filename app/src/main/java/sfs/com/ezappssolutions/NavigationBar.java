package sfs.com.ezappssolutions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import sfs.com.ezappssolutions.Fragments.BussinessApp;
import sfs.com.ezappssolutions.Fragments.ChooseEntityType;
import sfs.com.ezappssolutions.Fragments.EINNumberFragment;
import sfs.com.ezappssolutions.Fragments.OtherServicesFragment;
import sfs.com.ezappssolutions.Fragments.ProfileFragment;

public class NavigationBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Boolean exit = false;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);

        if(checkIfUserExist()) {
            // set new title to the MenuItem
            nav_logout.setTitle("Logout");
        }else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_bar_drawer2);
        }


        // add NavigationItemSelectedListener to check the navigation clicks
        navigationView.setNavigationItemSelectedListener(this);

        ChooseEntityType chose_entity_type = new ChooseEntityType();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_navigation_bar,chose_entity_type).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_Profile) {
            ProfileFragment profileFargment = new ProfileFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_navigation_bar,profileFargment).commit();
            Toast.makeText(this, "View Profile", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_EIN_number) {
            EINNumberFragment ein_number= EINNumberFragment.newInstance("1","2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_navigation_bar,ein_number,ein_number.getTag()).commit();
            Toast.makeText(this, "EIN_Number", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_bussiness_app) {

            BussinessApp bussinessApp= BussinessApp.newInstance("1","2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_navigation_bar,bussinessApp,bussinessApp.getTag()).commit();
            Toast.makeText(this, "Business App", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_other_services) {
            OtherServicesFragment otherServices= OtherServicesFragment.newInstance("1","2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_navigation_bar,otherServices,otherServices.getTag()).commit();
            Toast.makeText(this, "Other Services", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {
            if(checkIfUserExist()){
                logOutUser();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
            }


        } else if (id == R.id.nav_message) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_frequent) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
    private boolean checkIfUserExist() {
        Boolean flag = true;
        pref = getApplicationContext().getSharedPreferences(getString(R.string.userSession), 0); // 0 - for private mode
        String name = pref.getString(getString(R.string.userName), null);
        String pass = pref.getString(getString(R.string.userPass), null);
        if (name == null || pass == null) {
            flag = false;
        }
        return flag;
    }
    public void logOutUser()
    {


        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    finish();
                    SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.userSession), 0); // 0 - for private mode
                    SharedPreferences.Editor editor = SharedPreferences.edit();
                    editor.clear();
                    editor.commit();


                }

            }
        });
    }
}
