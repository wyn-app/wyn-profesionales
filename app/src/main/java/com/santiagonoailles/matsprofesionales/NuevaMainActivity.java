package com.santiagonoailles.matsprofesionales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.santiagonoailles.matsprofesionales.fragment.HomeFragment;
import com.santiagonoailles.matsprofesionales.fragment.InformesFragment;
import com.santiagonoailles.matsprofesionales.fragment.PresupuestosFragment;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.NotificacionesListener;

public class NuevaMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NotificacionesListener {

    private int selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DummyData.cargarProfesionales(this);

        String token = MatsSettings.getToken(this);
        if (!token.equals("")) {
            FirebaseHelper.setToken(MatsSettings.getProfessionalId(this), token);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menuSelected(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (this.selectedFragment != R.id.nav_home) {
            this.menuSelected(R.id.nav_home);
        } else {
            super.onBackPressed();
        }
    }

    private void menuSelected(int menuId) {
        this.selectedFragment = menuId;
        Fragment currentFragment = null;
        if (menuId == R.id.nav_home) {
            currentFragment = new HomeFragment();
            ((HomeFragment) currentFragment).loadListener(this);
        } else {
            if (menuId == R.id.nav_trabajos) {
                currentFragment = InformesFragment.newInstance(InformesFragment.INFORME_TYPE_HISTORIAL);
            } else if (menuId == R.id.nav_turnos) {
                currentFragment = InformesFragment.newInstance(InformesFragment.INFORME_TYPE_SOLICITADO);
            } else if (menuId == R.id.nav_presupuestos) {
                currentFragment = new PresupuestosFragment();
            } else if (menuId == R.id.nav_logout) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.cerrar_sesion_title));
                builder.setMessage(getString(R.string.cerrar_sesion_message));
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        MatsSettings.setProfessionalId("", NuevaMainActivity.this);
                        NuevaMainActivity.this.finish();
                        startActivity(new Intent(NuevaMainActivity.this, SignupActivity.class));
                    }
                });
                builder.show();
            } else if (menuId == R.id.nav_edit){
                startActivity(new Intent(this, EditInformationActivity.class));
            }
        }

        if (currentFragment != null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.beginTransaction().replace(R.id.framelayout, currentFragment).commitAllowingStateLoss();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        menuSelected(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTurnosNotificacionesClick() {
        menuSelected(R.id.nav_turnos);
    }

    @Override
    public void onPresupuestosNotificacionesClick() {
        menuSelected(R.id.nav_presupuestos);
    }
}
