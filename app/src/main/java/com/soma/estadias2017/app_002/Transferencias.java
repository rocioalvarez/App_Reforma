package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Transferencias extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_transferencias);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Boton  Traspaso a Terceros
        Button img2 = (Button) findViewById(R.id.btn_terceros);
        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v1) {
                Intent intent2 = new Intent(v1.getContext(), TraspasoTerceros.class);
                startActivityForResult(intent2, 0);
            }
        });

        //Boton Cuentas Propias
        Button img4 = (Button) findViewById(R.id.btn_propias);
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vp) {
                Intent intent4 = new Intent(vp.getContext(), CuentasPropias.class);
                startActivityForResult(intent4, 0);
            }
        });
    }
}
