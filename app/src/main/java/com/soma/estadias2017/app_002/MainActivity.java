package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.soma.estadias2017.app_002.Fragments.*;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Boton Saldos
        Button img2 = (Button)findViewById(R.id.btn_saldos);
        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vs) {
                Intent intent = new Intent(vs.getContext(), Saldos.class);
                startActivityForResult(intent, 0);
            }
        });

        //Boton Credito
        Button img3 = (Button)findViewById(R.id.btn_saldos_cre);
        img3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vc) {
                Intent intent1 = new Intent(vc.getContext(), Creditos.class);
                startActivityForResult(intent1, 0);
            }
        });

        //Boton Catalogos
        Button img4 = (Button)findViewById(R.id.btn_catalogos);
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vca) {
                Intent intent2 = new Intent(vca.getContext(), Catalogos.class);
                startActivityForResult(intent2, 0);
            }
        });

        //Boton traspasos
        Button img5 = (Button)findViewById(R.id.btn_transf);
        img5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vt) {
                Intent intent3 = new Intent(vt.getContext(), Transferencias.class);
                startActivityForResult(intent3, 0);
            }
        });

        //Boton Inversion
        Button img6 = (Button) findViewById(R.id.btn_inversion);
        img6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vin) {

                Intent intent4 = new Intent(vin.getContext(), Inversion.class);
                startActivityForResult(intent4, 0);

            }
        });

        //Boton SPEI
        Button img7 = (Button) findViewById(R.id.btn_spei);
        img7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View spei) {

                Intent intent5 = new Intent(spei.getContext(), PrincipalSpei.class);
                startActivityForResult(intent5, 0);

            }
        });

        //Boton Servicios
        Button img8 = (Button) findViewById(R.id.btn_servicios);
        img8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View serv) {

                Intent intent6 = new Intent(serv.getContext(), PagoServicios.class);
                startActivityForResult(intent6, 0);

            }
        });

        //Boton Recargas Electronicas
        Button img9 = (Button) findViewById(R.id.btn_recargast);
        img9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View rec){
                Intent intent7 = new Intent(rec.getContext(), RecargasElectronicas.class);
                startActivityForResult(intent7, 0);
            }
        });



/*
        //Generador de PDF
        Button btn = (Button) findViewById(R.id.PDF);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View spei) {

                Intent intent6 = new Intent(spei.getContext(), Main.class);
                startActivityForResult(intent6, 0);

            }
        });
*/



/*
        //Btn Salir

        Button btn_salir = (Button) findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent6 = new Intent(v.getContext(), Login.class);
                startActivityForResult(intent6, 0);

            }
        });
*/

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        // setFragment(0);

    }

    //Evitar que se cierre la actividad principal
    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear the backstack
        startActivity(myIntent);
        finish();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_saldos:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_creditos:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_traspasos:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_catalogo:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_inversion:
                                menuItem.setChecked(true);
                                setFragment(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_log_out:
                                menuItem.setChecked(true);
                                setFragment(5);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });

        View header = navigationView.getHeaderView(0);
        TextView cliente = (TextView)header.findViewById(R.id.cliente);
        cliente.setText(Globals.getCuenta());
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);


    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SaldosFragment saldosFragment = new SaldosFragment();
                fragmentTransaction.replace(R.id.fragment, saldosFragment);

                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CreditosFragment creditosFragment = new CreditosFragment();
                fragmentTransaction.replace(R.id.fragment, creditosFragment);

                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                DetalladoFragment catalogoFragment = new DetalladoFragment();
                fragmentTransaction.replace(R.id.fragment, catalogoFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                GSPEIFragment traspasosFragment = new GSPEIFragment();
                fragmentTransaction.replace(R.id.fragment, traspasosFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                InversionFragment inversionFragment = new InversionFragment();
                fragmentTransaction.replace(R.id.fragment, inversionFragment);
                fragmentTransaction.commit();
                break;

            /*Cerrar sesion*/
            case 5:

                //ALERTA CERRAR SESION
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Estar a punto de cerrar sesión ¿Desea continuar?")
                        .setTitle("Cerrar Sesión")
                        .setCancelable(true)
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                })
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        //System.runFinalization();
                                        //System.exit(1);


                                        //Intent intent = new Intent(MainActivity.this, Login.class);
                                        //startActivity(intent);
                                        //System.exit(0);

                                        //Intent intent = new Intent(Intent.ACTION_MAIN);
                                        //intent.addCategory(Intent.CATEGORY_HOME);
                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //startActivity(intent);
                                        //finish();

                                        startActivity(new Intent(getBaseContext(), Login.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        finish();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

        }
    }
}