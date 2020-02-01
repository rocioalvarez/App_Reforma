package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 14/02/17.
 */


public class SaldosCredito extends AppCompatActivity {

    ListView lsc;
    Toolbar toolbar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "Creditos";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/CreditosExistentes_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CreditosExistentes_ws?wsdl";


    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.vista_lista_saldocre);
        lsc = (ListView) findViewById(R.id.lsc);

        //Barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Politicas
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InvocaSCreditoW();

    }


    private void InvocaSCreditoW(){
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());

            envelope.setOutputSoapObject(Request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection());
            SoapObject resp = (SoapObject) envelope.bodyIn;

            List<HashMap<String,String>> reg = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> regist;

            if (resp.getPropertyCount() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("NO HAY DATOS A LISTAR")
                        .setTitle("Aviso")
                        .setNegativeButton("ACEPTAR",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                        startActivity(new Intent(getBaseContext(), Creditos.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        finish();
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }

            for (int i =0; i<resp.getPropertyCount(); i++){
                String[] testValues = new String[resp.getPropertyCount()];
                testValues[i] = resp.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject object = (SoapObject) resp.getProperty(i);
                regist = new HashMap<String, String>();
                regist.put("creditoid", object.getProperty("creditoid").toString());
                regist.put("referencia", object.getProperty("referencia").toString());
                regist.put("fechaotorgamiento", object.getProperty("fechaotorgamiento").toString());
                regist.put("montocredito", object.getProperty("montocredito").toString());

                regist.put("interes", object.getProperty("interes").toString());
                regist.put("moratorio", object.getProperty("moratorio").toString());
                regist.put("total", object.getProperty("total").toString());
                regist.put("iva", object.getProperty("iva").toString());

                reg.add(regist);
            }

            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(), reg,
                    R.layout.vista_saldos_credito,
                    new String[]{"creditoid", "referencia", "fechaotorgamiento", "montocredito"

                    , "interes", "iva","moratorio", "total", "iva"},

                    new int[]{R.id.creditoid, R.id.referencia, R.id.fechaotorgamiento,
                            R.id.montocredito
            , R.id.interes, R.id.iva, R.id.moratorio, R.id.total, R.id.iva});
            Collections.reverse(reg); //Ordenar registro
            lsc.setAdapter(adaptador);
            lsc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    Intent intento = new Intent(getApplicationContext(),
                            SaldosCreditoD.class);


                    Bundle bun = new Bundle();


                    TextView interes = (TextView) arg1.findViewById(R.id.interes);
                    bun.putString("interes", interes.getText().toString());

                    intento.putExtra("interes", interes.getText().toString());
                    intento.putExtras(bun);

                    //intento.putExtra("iva", iva.getText().toString());

                    TextView iva =  arg1.findViewById(R.id.iva);
                    intento.putExtra("iva", iva.getText().toString());

                    TextView moratorio =  arg1.findViewById(R.id.moratorio);
                    intento.putExtra("moratorio", moratorio.getText().toString());

                    TextView montocredito =  arg1.findViewById(R.id.montocredito);
                    intento.putExtra("montocredito", montocredito.getText().toString());

                    TextView total =  arg1.findViewById(R.id.total);
                    intento.putExtra("total", total.getText().toString());


                    startActivity(intento);
                }
            });

        }
        catch (Exception e){

        }
    }


}
