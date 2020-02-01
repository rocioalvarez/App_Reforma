package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by estadias2017 on 14/02/17.
 */

public class Catalogos extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_catalogos);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_alta_bene = (Button) findViewById(R.id.btn_alta_bene);
        btn_alta_bene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_ab){
                Intent inten1 = new Intent(getApplicationContext(), AltaBeneficiario.class);
                startActivity(inten1);
            }
        });

        Button btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_ac){
                Intent inten2 = new Intent(getApplicationContext(), ActualizarBeneficiario.class);
                startActivity(inten2);
            }
        });

    }





}