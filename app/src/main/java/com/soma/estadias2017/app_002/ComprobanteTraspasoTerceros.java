package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ComprobanteTraspasoTerceros extends AppCompatActivity {

    Button btn_menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_comprobante_terceros);
        btn_menu = (Button) findViewById(R.id.btn_menu);


        //Importamos campos de la otra actividad
        Bundle bundle = getIntent().getExtras();

        String cuenta = bundle.getString("cuenta");
        String beneficiario = bundle.getString("beneficiario");
        String nombrebe = bundle.getString("nombrebe");
        String rfcbene = bundle.getString("rfcbene");
        String cbeneficiario = bundle.getString("cbeneficiario");
        String cantidad = bundle.getString("cantidad");
        String concepto = bundle.getString("concepto");

        String cuePasado = cuenta;
        TextView cue = (TextView) findViewById(R.id.cuenta);
        cue.setText(cuePasado);

        String benPasado = beneficiario;
        TextView ben = (TextView) findViewById(R.id.beneficiario);
        ben.setText(benPasado);

        String nomPasado = nombrebe;
        TextView nom = (TextView) findViewById(R.id.nombrebe);
        nom.setText(nomPasado);

        String rfcPasado = rfcbene;
        TextView rfc = (TextView) findViewById(R.id.rfcbene);
        rfc.setText(rfcPasado);

        String cbePasado = cbeneficiario;
        TextView cbe = (TextView) findViewById(R.id.cbeneficiario);
        cbe.setText(cbePasado);

        String canPasado = cantidad;
        TextView can = (TextView) findViewById(R.id.cantidad);
        can.setText(canPasado);

        String desPasado = concepto;
        TextView des = (TextView) findViewById(R.id.concepto);
        des.setText(desPasado);


        Button btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View menu) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

