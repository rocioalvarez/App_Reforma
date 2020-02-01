package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.soma.estadias2017.app_002.Huella.FingerprintMainSaldo;

import static com.soma.estadias2017.app_002.R.id.toolbar;

/**
 * Created by estadias2017 on 9/03/17.
 */

public class Saldos extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_saldos);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_sg = (Button) findViewById(R.id.btn_sg);
        btn_sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_sg){
                Intent inten1 = new Intent(getApplicationContext(), SaldoGlobal.class);
                System.out.print("entra");
                startActivity(inten1);
            }
        });

        Button btn_sd = (Button) findViewById(R.id.btn_sd);
        btn_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_sd){
                Intent inten2 = new Intent(getApplicationContext(), SaldoDetallado.class);
                startActivity(inten2);
            }
        });


    }





}