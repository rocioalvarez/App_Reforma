package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.soma.estadias2017.app_002.Huella.FingerprintMainCreditos;

/**
 * Created by estadias2017 on 17/02/17.
 */

public class Creditos extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_creditos);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_saldos_cre = (Button) findViewById(R.id.btn_sc);
        btn_saldos_cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_sc){
                Intent inten1 = new Intent(getApplicationContext(), SaldosCredito.class);
                System.out.print("entra");
                startActivity(inten1);
            }
        });

        Button btn_ab = (Button) findViewById(R.id.btn_ab);
        btn_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_ab){
                Intent inten2 = new Intent(getApplicationContext(), AbonoCreditos.class);
                startActivity(inten2);
            }
        });

    }
}