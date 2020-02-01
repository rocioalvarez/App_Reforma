package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by estadias2017 on 29/04/17.
 */

public class PrincipalSpei extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_spei);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_nuevo = (Button) findViewById(R.id.btn_nuevo);
        btn_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View nuevo) {
                Intent inten1 = new Intent(getApplicationContext(), NuevoSpei.class);
                startActivity(inten1);
            }
        });

        Button btn_sd = (Button) findViewById(R.id.btn_gestion);
        btn_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_sd) {
                Intent inten2 = new Intent(getApplicationContext(), GestionSpei.class);
                startActivity(inten2);
            }
        });

        Button btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_en) {
                Intent inten3 = new Intent(getApplicationContext(), EnviarSpei.class);
                startActivity(inten3);
            }
        });

       /* Button btn_speilista = (Button) findViewById(R.id.speilist);
        btn_speilista.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v_sl) {
                Intent intent4 = new Intent(getApplicationContext(), SpeiLista.class);
                startActivity(intent4);
            }
        });
*/

        Button btn_comprobant = (Button) findViewById(R.id.btn_comprobant);
        btn_comprobant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v_cm) {
                Intent intent6 = new Intent(getApplicationContext(), ComprobanteSpei.class);
                startActivity(intent6);
            }
        });

    }
}
