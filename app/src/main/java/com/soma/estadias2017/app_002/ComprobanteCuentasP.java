package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ComprobanteCuentasP extends AppCompatActivity {

    String cantidad;
    Button btn_menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_comprobante_cuentasp);
        btn_menu = (Button) findViewById(R.id.btn_regresar);


        //Importamos campos de la otra actividad
        Bundle bundle = getIntent().getExtras();
        String cliente = bundle.getString("cliente");
        String cuenta = bundle.getString("cuenta");
        String cantidad = bundle.getString("cantidad");
        String concepto = bundle.getString("concepto");

        String usuPasado = cliente;
        TextView usu = (TextView) findViewById(R.id.cliente);
        usu.setText(usuPasado);

        String idePasado = cuenta;
        TextView ide = (TextView) findViewById(R.id.cuenta);
        ide.setText(idePasado);

        String canPasado = cantidad;
        TextView can = (TextView) findViewById(R.id.cantidad);
        can.setText(canPasado);

        String desPasado = concepto;
        TextView des = (TextView) findViewById(R.id.concepto);
        des.setText(desPasado);



        //Creamos un btn para poder regresar a la actividad principal desde el comprobante
        Button btn_menu = (Button) findViewById(R.id.btn_regresar);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View menu) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}

