package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReporteInversion extends AppCompatActivity {

    private ListView ListaReporte;
    Toolbar toolbar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "ReporteInversion";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/ReporteInversion_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/ReporteInversion_ws?wsdl";

    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.vista_lista_reporte_inversion);
        ListaReporte = (ListView) findViewById(R.id.ListaRI);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

                InvocaReporteInversion();

        }

    public void InvocaReporteInversion(){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            request.addProperty("cliente",Globals.getCuenta());
            request.addProperty("idcliente",  Globals.getIdusuario());

            srl.setOutputSoapObject(request);

            HttpTransportSE  HttpTransport = new HttpTransportSE(URL);
            System.out.println("WEB SERVICE "+URL);
            HttpTransport.call(SOAP_ACTION, srl);

            SoapObject so = (SoapObject) srl.bodyIn;

            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;


            if (so.getPropertyCount() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("NO HAY DATOS A LISTAR")
                        .setTitle("Aviso")
                        .setNegativeButton("ACEPTAR",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }
            for(int i= 0; i< so.getPropertyCount(); i++) {

                String[] testValues = new String[so.getPropertyCount()];
                testValues[i] = so.getProperty(i).toString();
                System.out.print(testValues[i]);
                SoapObject object = (SoapObject) so.getProperty(i);
                registro = new HashMap<String, String>();
                registro.put("deposito", object.getProperty("deposito").toString());
                registro.put("dias", object.getProperty("dias").toString());
                registro.put("fecha", object.getProperty("fecha").toString());
                registro.put("interes",  object.getProperty("interes").toString());
                registro.put("inversion", object.getProperty("inversion").toString());
                registro.put("inversionid", object.getProperty("inversionid").toString());
                registro.put("vencimiento", object.getProperty("vencimiento").toString());

                registros.add(registro); }

            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(), registros,
                    R.layout.vista_reporte_inver,
                    new String[]{"deposito", "dias", "fecha", "interes", "inversion",
                                 "inversionid", "vencimiento"},

                    new int[]{R.id.cordenante, R.id.dias, R.id.fecha, R.id.interes, R.id.plazo,
                              R.id.inversionid, R.id.vencimiento});

            Collections.reverse(registros);
            ListaReporte.setAdapter(adaptador);
            }

        catch (Exception ex){
        }
    }
}