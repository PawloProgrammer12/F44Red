package com.f44red;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Contains drawer tools for re-using navigation drawer in another activities without multiplying it.
 * @author Paweł Turoń
 */

public class DrawerBaseActivity extends AppCompatActivity {
    private DrawerBaseActivity drawerBaseActivity;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_base);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Application toolbar
        toolbar = (Toolbar) findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);

        // Drawer layoout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        // Hamburger icon color
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.hamburgerWhiteColor));

        // NavigationView listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final String appPkgName = getPackageName();
                Intent i = null;
                switch (item.getItemId()){
                    case R.id.news:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.aktualnosci:
                        i = new Intent(getApplicationContext(), News.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.about_us:
                        i = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.contact:
                        i = new Intent(getApplicationContext(), Contact.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.settings:
                        i = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.errata:
                        showDialogReportIssue();
                        break;
                    case R.id.about_app:
                        AboutAppBottom about = AboutAppBottom.newInstance();
                        about.show(getSupportFragmentManager(), "about_app_bottom");
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    protected void showDialogReportIssue(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Zgłoś błąd aplikacji");
        dialog.setMessage("Jeśli doświadczasz nieprawidłowego działania aplikacji, proszę zgłaszać usterki pod adresem: xxx");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
    }
}
