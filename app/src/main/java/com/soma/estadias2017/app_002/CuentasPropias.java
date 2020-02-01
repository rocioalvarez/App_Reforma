package com.soma.estadias2017.app_002;

import android.content.Intent;
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

/**
 * Created by estadias2017 on 29/03/17.
 */

public class CuentasPropias extends AppCompatActivity {

    public TextView resultado;
    public EditText cantidad, concepto, cvoperacion;
    public Button Enviar;
    Spinner cuenta;
    Toolbar toolbar;

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "CuentasP";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/CuentasPropias_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CuentasPropias_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_traspaso_propias);

        resultado = (TextView) findViewById(R.id.resultado);
        cantidad = (EditText) findViewById(R.id.total);
        concepto = (EditText) findViewById(R.id.concepto);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        Enviar = (Button) findViewById(R.id.btn_enviar);

        //Mensaje que muestra al abrir el formulario
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Sigue los pasos para hacer un traspaso a cuentas propias",
                        Toast.LENGTH_SHORT);
        toast1.show();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Arreglo Spinner
        final CatalogoCuentas cuentas=new CatalogoCuentas();

        ArrayList<Cuenta> datos = cuentas.consumir2(1);
        final ArrayAdapter<Cuenta> adapter  = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        cuenta = (Spinner)findViewById(R.id.cordenante);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cuenta c = adapter.getItem(cuenta.getSelectedItemPosition());
                String can = cantidad.getText().toString();
                String con = concepto.getText().toString();
                String cv = cvoperacion.getText().toString();

                System.out.println("cuenta "+c.getIdcuenta()+ " DES "+c.getDescripcion());

                if (can.isEmpty()){
                    cantidad.setError("Campo vacio");
                    cantidad.requestFocus();
                }else if (con.isEmpty()){
                    concepto.setError("Campo vacio");
                    concepto.requestFocus();
                }else if (cv.isEmpty()){
                    cvoperacion.setError("Campo vacio");
                    cvoperacion.requestFocus();
                }else {

                    InvocarCuentasPropias(c.getIdcuenta(), can, con, cv);

                    Intent intent= new Intent(CuentasPropias.this, ComprobanteCuentasP.class);
                    intent.putExtra("cliente", Globals.getCuenta());
                    intent.putExtra("cuenta", c.getDescripcion());
                    intent.putExtra("cantidad", cantidad.getText().toString());
                    intent.putExtra("concepto", concepto.getText().toString());
                    startActivity(intent);

                    cuenta.setAdapter(adapter);
                    cantidad.setText("");
                    concepto.setText("");
                    cvoperacion.setText("");
                }
            }
        });
    }

    private void InvocarCuentasPropias(String cuenta, String cantidad, String concepto,
                                       String cvoperacion) {

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idcliente", Globals.getIdusuario());
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("cuenta", cuenta);
            request.addProperty("cantidad", cantidad);
            request.addProperty("concepto", concepto);
            request.addProperty("cvoperacion", codifica(cvoperacion));

            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);

            SoapObject so = (SoapObject) srl.bodyIn;

            String r = "";
            r = so.getProperty(1).toString();

            resultado.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();//imprime resultado
            }

        catch (Exception e) {
            e.printStackTrace();
        }
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
}
