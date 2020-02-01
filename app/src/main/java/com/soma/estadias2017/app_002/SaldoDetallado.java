package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.estadias2017.app_002.Huella.FingerprintMainMovimientos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rocio Alvarez on 8/02/17.
 *
 * Función: consultar saldo detallado mediante una lista desplegable.
 */

public class SaldoDetallado extends AppCompatActivity{

    private ListView li_movimiento;
    private Button btn_consultar;
    Toolbar toolbar;
    Spinner sp_movimiento;
    TextView resultado;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sdetallado";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/SaldoDetallado_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/SaldoDetallado_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lista_saldosdet);
        li_movimiento = (ListView) findViewById(R.id.li_movimiento);
        btn_consultar = (Button) findViewById(R.id.btn_consultar);
        resultado = (TextView) findViewById(R.id.resultado);

        //Arreglo para llenar Spinner cuentas
        CatalogoCuentas cuentas=new CatalogoCuentas();
        ArrayList<Cuenta> datos = cuentas.consumir2(4);
        final ArrayAdapter<Cuenta> adapter  = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        sp_movimiento = (Spinner)findViewById(R.id.sp_movimiento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_movimiento.setAdapter(adapter);

        //Mensaje que muestra al abrir el formulario
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Seleccione la cuenta a consultar", Toast.LENGTH_LONG);
        toast1.show();

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Boton para consultar
        btn_consultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Cuenta c = adapter.getItem(sp_movimiento.getSelectedItemPosition());
                //System.out.println("cuenta "+c.getIdcuenta()+ " DES "+c.getDescripcion());

                    InvocaSDetallado(c.getIdcuenta());

                    //Intent intento = new Intent (getApplicationContext(), FingerprintMainMovimientos.class);
                    //intento.putExtra("sp_movimiento", String.valueOf(adapter.getItem(sp_movimiento.getSelectedItemPosition())) );
                   // startActivity(intento);


            }
        });
    }

    //Metodo en el que le pasa el parametro a donde esta consultando
    private String[] InvocaSDetallado(String movimiento){
        System.out.println("Entra al metodo:    " + movimiento);
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("movimiento", movimiento);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// utilizar la version que corresponda:11 o 12
            envelope.dotNet = false; // para WS ASMX, sólo si fue construido con .Net
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            System.out.println("Envelope:   " +envelope);
            SoapObject resp = (SoapObject) envelope.bodyIn;

                List<HashMap<String,String>> regi = new ArrayList<HashMap<String, String>>();
                System.out.println("List:   " + regi);
                HashMap<String, String> regis;

            if (resp.getPropertyCount() == 0){
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

            for(int i= 0; i< resp.getPropertyCount(); i++) {

                System.out.println("Entra al for:    " +resp);
                SoapObject object = (SoapObject) resp.getProperty(i);

                String a = object.getProperty("fecha").toString();
                System.out.println("Fecha:  " +a);
                String b = object.getProperty("deposito").toString();
                String c = object.getProperty("retiro").toString();
                String d = object.getProperty("descripcion").toString();
                String e = object.getProperty("referencia").toString();
                System.out.print(a+b+c+d);

                regis = new HashMap<String, String>();
                regis.put("fecha",                a);
                regis.put("deposito", b);
                regis.put("retiro", c);
                regis.put("descripcion", d);
                regis.put("referencia", e);

                regi.add(regis);
            }

            //Adaptador para traer los datos a la lista

            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(),
                    regi, R.layout.vista_saldo_detallado,
                    new String[]{"fecha", "deposito", "retiro", "descripcion", "referencia"},
                    new int[]{R.id.fecha,R.id.deposito,  R.id.retiro, R.id.descripcion,
                              R.id.referencia});
            Collections.reverse(regi); //Invierte el orden de los datos.
            li_movimiento.setAdapter(adaptador);


            //li_movimiento.setSelection(resp.getPropertyCount() -1); //En esta linea indicamos que el focus al listview se realice en el ultimo elemento de este.

            }

        catch (Exception e){
        }
        return new String[0];
    }
}
