package com.soma.estadias2017.app_002;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ListaSaldoDetallado extends AppCompatActivity {

    private ListView li_movimiento;
    Toolbar toolbar;
    TextView sp_movimiento;
    TextView resultado;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sdetallado";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/SaldoDetallado_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/SaldoDetallado_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lista_saldosdet);
        li_movimiento = (ListView) findViewById(R.id.li_movimiento);
        resultado = (TextView) findViewById(R.id.resultado);
        sp_movimiento = (TextView) findViewById(R.id.sp_movimiento);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String sp_movimiento = bundle.getString("sp_movimiento");
            System.out.println("EL MOVIMIENTO ES:   " + resultado);

            String PasaMovimiento = sp_movimiento;
            TextView mov = (TextView) findViewById(R.id.sp_movimiento);
            mov.setText(PasaMovimiento);

            System.out.println("dato:  " + PasaMovimiento);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        //InvocaSDetallado(sp_movimiento);
    }

    //Metodo en el que le pasa el parametro a donde esta consultando
    public void InvocaSDetallado(String sp_movimiento){
        System.out.println("Entra al metodo:    " + sp_movimiento);
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("sp_movimiento", sp_movimiento);

            System.out.println("nombre del movimiento:   " + sp_movimiento);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// utilizar la version que corresponda:11 o 12
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
            Collections.reverse(regi); //Invierte el orden en el que se muestran los datos.
            li_movimiento.setAdapter(adaptador);


            //li_movimiento.setSelection(resp.getPropertyCount() -1); //En esta linea indicamos que el focus al listview se realice en el ultimo elemento de este.

        }

        catch (Exception e){
        }
        return;
    }

}