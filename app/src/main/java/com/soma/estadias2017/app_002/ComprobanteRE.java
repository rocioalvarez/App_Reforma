package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SOMA-ROCIO on 24/04/2018.
 */

public class ComprobanteRE extends AppCompatActivity {

    String notelefono;
    String carecargasmtc;
    String cuenta;
    String fecha;
    String folio;
    Button btn_menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_recarga_realizada);
        btn_menu = (Button) findViewById(R.id.btn_regresar);
        notelefono = getIntent().getExtras().getString("notelefono");
        carecargasmtc = getIntent().getExtras().getString("carecargasmtc");
        cuenta = getIntent().getExtras().getString("cuenta");
        fecha = getIntent().getExtras().getString("fecha");
        folio = getIntent().getExtras().getString("folio");


        TextView textView = (TextView) findViewById(R.id.textView);
        /*
        textView.setText(  "Operacion Exitosa" + '\n' +
                '\n'+
                "Número" + '\n'+
                notelefono + '\n'+
                "Importe:             " + carecargasmtc + '\n' +
                "Cuenta de retiro:    " + cuenta + '\n' +
                "Fecha:               " + fecha + '\n' +
                "Folio:               " + folio);
        */


        //Importamos campos de la otra actividad
        Bundle bundle = getIntent().getExtras();
        String notelefono = bundle.getString("notelefono");
        String carecargasmtc = bundle.getString("carecargasmtc");
        String cuenta = bundle.getString("cuenta");
        String fecha = bundle.getString("fecha");
        String folio = bundle.getString("folio");

        String ntelPasado = notelefono;
        TextView ntel = (TextView) findViewById(R.id.notelefono);
        ntel.setText(ntelPasado);

        String carePasado = carecargasmtc;
        TextView care = (TextView) findViewById(R.id.carecargasmtc);
        care.setText(carePasado);

        String cuenPasado = cuenta;
        TextView cuen = (TextView) findViewById(R.id.cuenta);
        cuen.setText(cuenPasado);

        String fechPasado = fecha;
        TextView fech = (TextView) findViewById(R.id.fecha);
        fech.setText(fechPasado);

        String foliPasado = folio;
        TextView foli = (TextView) findViewById(R.id.folio);
        foli.setText(foliPasado);




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
