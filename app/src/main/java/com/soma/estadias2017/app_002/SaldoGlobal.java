package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ROCIO-SOMA on 9/03/17.
 * Correo: rocio.alvarez@somatecnologia.com.mx
 * Función: Consulta Saldo Global
 */

public class SaldoGlobal extends AppCompatActivity {

    private ListView listaSg;
    Toolbar toolbar;
    Button btn_consultar;
    TextView nombre, saldo;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sglobal";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/SaldoGlobal_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/SaldoGlobal_ws?wsdl";

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.vista_lista_saldog);
        listaSg = (ListView) findViewById(R.id.lsg);
        //nombre = (TextView) findViewById(R.id.nombre);
        //saldo = (TextView) findViewById(R.id.saldo);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Declarar metodo para invocar los datos
        InvocaSGlobalW();
        System.out.println("HOLA 1");
    }

    //Metodo que invoca
    public void InvocaSGlobalW(){
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); //  la version que corresponutilizarda:11 o 12
            envelope.dotNet = false; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());

            System.out.println("HOLA 2" + envelope);
            //request.addProperty("nombre", nombre);
            //request.addProperty("saldo", saldo);
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection()); //imprimir en consola la conexion
            SoapObject result = (SoapObject)envelope.bodyIn;

            //obtener los datos mediante una lista
            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;

            String[] testValues = new String[result.getPropertyCount()];

            for(int i= 0; i< result.getPropertyCount(); i++) {
                testValues[i] = result.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject result3 = (SoapObject) result.getProperty(i);
                registro = new HashMap<String, String>();
                registro.put("nombre", result3.getProperty("nombre").toString());
                registro.put("saldo",  result3.getProperty("saldo").toString());

                System.out.println("nombre:    " + nombre);

                registros.add(registro);

                System.out.println("regis     ++++++++       " + registro);
            }


            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(),
                    registros, R.layout.vista_saldo_global,
                    new String[]{"nombre", "saldo"},
                    new int[]{R.id.nombre, R.id.saldo});


            listaSg.setAdapter(adaptador);

            /*
            listaSg.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {

                           Intent intento = new Intent(getApplicationContext(),
                                  PruebaMovi.class);

                           Bundle bun = new Bundle();

                           TextView nombre = (TextView) arg1.findViewById(R.id.nombre);
                           bun.putString("nombre", nombre.getText().toString());
                           intento.putExtra("nombre", nombre.getText().toString());
                           intento.putExtras(bun);

                           intento.putExtra("nombre", nombre.getText().toString());

                           TextView saldo = (TextView) arg1.findViewById(R.id.saldo);
                           intento.putExtra("saldo", saldo.getText().toString());

                           startActivity(intento);
                    }
            }); */

            } catch (Exception e){

        }
    }


}