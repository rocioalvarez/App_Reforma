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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SOMA-YAZMIN on 08/12/2017.
 */

public class RetiroInteres extends AppCompatActivity {

    public ListView lista_retiro;
    public Toolbar toolbar;

    public String SOAP_ACTION = "";
    public String METHOD_NAME = "retiro_inv";
    public String NAMESPACE = "http://webservices/";
    //public String URL = "http://192.168.1.118:8080/AppMovilReforma/RetiroInteres_ws?wsdl";
    public String URL = "https://mail.reformasofipo.com/AplicacionMovilws/RetiroInteres_ws?wsdl";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lista_retiro_int);

        lista_retiro = (ListView) findViewById(R.id.lista_retiro);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        invokeList();
    }

    private void invokeList() {

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            //envelope.dotNet = true; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection()); //imprime en consola la conexion
            SoapObject result = (SoapObject)envelope.bodyIn;

            //obtener los datos mediante una lista
            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;



            if (result.getPropertyCount() == 0){

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
            for(int i= 0; i< result.getPropertyCount(); i++) {

                String[] testValues = new String[result.getPropertyCount()];
                testValues[i] = result.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject result3 = (SoapObject) result.getProperty(i);
                registro = new HashMap<String, String>();

                registro.put("deposito", result3.getProperty("deposito").toString());
                registro.put("dias", result3.getProperty("dias").toString());
                registro.put("fecha", result3.getProperty("fecha").toString());
                registro.put("interes", result3.getProperty("interes").toString());
                registro.put("inversion", result3.getProperty("inversion").toString());
                registro.put("inversionid", result3.getProperty("inversionid").toString());
                registro.put("tasa", result3.getProperty("tasa").toString());
                registro.put("vencimiento", result3.getProperty("vencimiento").toString());

                registros.add(registro);
            }


            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(),
                    registros, R.layout.vista_retiro_interes,
                    new String[]{"deposito", "dias", "fecha", "interes", "inversion", "inversionid",
                                    "tasa", "vencimiento"},

                    new int[]{ R.id.deposito, R.id.dias, R.id.fecha, R.id.interes, R.id.inversion,
                                R.id.inversionid, R.id.tasa, R.id.vencimiento});

            Collections.reverse(registros);
            lista_retiro.setAdapter(adaptador);
            lista_retiro.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent intento = new Intent(getApplicationContext(),
                            FormRetiroInteres.class);

                    Bundle bun = new Bundle();

                    TextView deposito = (TextView) arg1.findViewById(R.id.deposito);
                    bun.putString("deposito", deposito.getText().toString());
                    intento.putExtra("deposito", deposito.getText().toString());
                    intento.putExtras(bun);

                    intento.putExtra("deposito", deposito.getText().toString());

                    TextView dias = (TextView) arg1.findViewById(R.id.dias);
                    intento.putExtra("dias", dias.getText().toString());

                    TextView fecha = (TextView) arg1.findViewById(R.id.fecha);
                    intento.putExtra("fecha", fecha.getText().toString());

                    TextView interes = (TextView) arg1.findViewById(R.id.interes);
                    intento.putExtra("interes", interes.getText().toString());

                    TextView inversion = (TextView) arg1.findViewById(R.id.inversion);
                    intento.putExtra("inversion", inversion.getText().toString());

                    TextView inversionid = (TextView) arg1.findViewById(R.id.inversionid);
                    intento.putExtra("inversionid", inversionid.getText().toString());

                    TextView tasa = (TextView) arg1.findViewById(R.id.tasa);
                    intento.putExtra("tasa", tasa.getText().toString());

                    TextView vencimiento = (TextView) arg1.findViewById(R.id.vencimiento);
                    intento.putExtra("vencimiento", vencimiento.getText().toString());

                    startActivity(intento);
                }
            });

        }

        catch (Exception e){

        }
    }
}
