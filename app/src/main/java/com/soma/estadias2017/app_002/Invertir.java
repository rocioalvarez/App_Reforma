package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 29/03/17.
 */

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Invertir extends AppCompatActivity {

    public TextView resultado;
    public EditText cantidad, cvoperacion, fecha, vencimiento, taza;
    public Button fin_inversion;
    Spinner cuenta, plazo;
    Toolbar  toolbar;

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "InvertirW";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/Invertir_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/Invertir_ws?wsdl";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_invertir);
        resultado = (TextView) findViewById(R.id.resultado);
        fin_inversion = (Button) findViewById(R.id.fin_inversion);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        cantidad = (EditText) findViewById(R.id.cantidad);

        CatalogoCuentas cuentas = new CatalogoCuentas();
        final ArrayList<Cuenta> datos = cuentas.consumir2(3);
        final ArrayAdapter<Cuenta> adapter = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);
        cuenta = (Spinner) findViewById(R.id.inversion);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);


        CatalogoPlazo Plazo = new CatalogoPlazo();
        final ArrayList<Plazo> datos2 = Plazo.consumir2(1);
        final ArrayAdapter<Plazo> adapter2 = new ArrayAdapter<Plazo>(this,
                android.R.layout.simple_spinner_item, datos2);
        plazo = (Spinner) findViewById(R.id.plazo);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plazo.setFocusable(true);
        plazo.setAdapter(adapter2);

        /*
        plazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int id= (int) plazo.getSelectedItemId();
                int idcliente= datos2.get(id).getIdcliente();
                sps_plazo(idcliente);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });*/

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        fin_inversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cuenta c = adapter.getItem(cuenta.getSelectedItemPosition());
                Plazo p = adapter2.getItem(plazo.getSelectedItemPosition());
                String can = cantidad.getText().toString();
                String cv = cvoperacion.getText().toString();

               // String[] res = InvocarInvertir(c.getIdcuenta(), p.getPlazo(), can, cv);
                //String r = res[0];



                Pattern pa= Pattern.compile("[0-9][0-9][0-9][0-9]");

                if(pa.matcher (cantidad.getText().toString()).matches() == false){

                    //resultado.setText(res[0]);
                    cantidad.setError("Inversión minima $1000");
                    cantidad.requestFocus();

                    Toast.makeText(Invertir.this, "Transacción erronea", Toast.LENGTH_LONG).show();

                }else {

                    cantidad.setError(null);

                    InvocarInvertir(c.getIdcuenta(), p.getPlazo(), can, cv);
                    Toast.makeText(Invertir.this, "Inversión realizada", Toast.LENGTH_LONG).show();

                    cantidad.setText("");
                    cvoperacion.setText("");
                }
            }
        });
    }

    private String[] InvocarInvertir(String cuenta, String plazo, String cantidad, String cvoperacion) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cantidad", cantidad);
            request.addProperty("plazo", plazo);
            request.addProperty("cvoperacion", codifica(cvoperacion));
            request.addProperty("cuenta", cuenta);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());

            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);
            SoapObject so = (SoapObject) srl.bodyIn;

            String r = "";
            String r1 = "";
            String r2 = "";

            r = so.getProperty(0).toString();
            r1 = so.getProperty(1).toString();
            r2 = so.getProperty(2).toString();
            System.out.println("resultado " + r);
            System.out.println("resultado 1" + r1);
            System.out.println("resultado 2" + r2);
            r = so.getProperty(1).toString();

            resultado.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

            String[] res = {r, r1, r2};

            return res;

            }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String codifica(String cadena) {
        //Se crea objeto de messageDigest que permite crear instancia hacia el tipo de encriptacion..
        MessageDigest messageDigest = null;

        //Se crea objeto de string buffer para almacenar la concatenacion de los datos del arreglo.
        StringBuffer sb = null;
        try {
            //Se inicializa el objeto y se le pasa el tipo de encriptacion deseada
            messageDigest = MessageDigest.getInstance("SHA-512");

            //Se extraen los bytes que contiene la cadena
            byte[] encripta = messageDigest.digest(cadena.getBytes());

            //Se inicializa un objeto tipo String buffer
            sb = new StringBuffer();

            //Se recorre el arreglo de bytes que genero messageDigest
            for (byte hashB : encripta) {
                // se concatena los datos obtenidos del arreglo
                sb.append(String.format("%02x", hashB));
            }

        } catch (Exception e) {
//            log.info(e.getMessage());
//            errorService.createError(e.getMessage(), "Error encriptando la cadena: "+ cadena);
        }

        return sb.toString();
    }

    /*
    public void sps_plazo(int idcliente){
        CatalogoPlazo n=new CatalogoPlazo();
        SoapObject plazo1 = n.spsPlazo(idcliente);

        for (int i = 0; i < plazo1.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    plazo1.getProperty(i);

            fecha.setText(result2.getProperty("fecha").toString());
            vencimiento.setText(result2.getProperty("fechafinal").toString());
            taza.setText(result2.getProperty("taza").toString());

        }
    }*/
}