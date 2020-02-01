package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 29/03/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Inversion extends AppCompatActivity {

    Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_inversion);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button btn_invertir = (Button) findViewById(R.id.btn_invertir);
        btn_invertir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vn) {


                Intent inten = new Intent(vn.getContext(), Invertir.class);
                startActivityForResult(inten, 0);
            }
        });


        Button btn_reporte = (Button) findViewById(R.id.btn_reporte);
        btn_reporte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vr) {
                Intent inten7 = new Intent(vr.getContext(), ReporteInversion.class);
                startActivityForResult(inten7, 0);

            }
        });

        Button btn_retiro_in = (Button) findViewById(R.id.btn_retiro_in);
        btn_retiro_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vrt) {
                Intent intent8 = new Intent(vrt.getContext(), RetiroInteres.class);
                startActivityForResult(intent8, 0);


            }
        });

    }
}