package com.soma.estadias2017.app_002;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;


/**
 * Created by estadias2017 on 3/03/17.
 */

public class AbonoCreditos extends AppCompatActivity {

    public TextView resultado;
    public EditText total, capital, interes, moratorio, iva, cvoperacion;
    private Button btn_abono;
    Toolbar toolbar;
    Spinner cuenta;
    Spinner creditoid;

    CreditoDetallado credito = new CreditoDetallado();

    //Consumir Web Service
    private String SOAP_ACTION = "";
    private String METHOD_NAME = "abono";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/AbonoCredito_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/AbonoCredito_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_abono_creditos);
        resultado = (TextView) findViewById(R.id.resultado);
        total = (EditText) findViewById(R.id.total);
        capital = (EditText) findViewById(R.id.capital);
        interes = (EditText) findViewById(R.id.interes);
        moratorio = (EditText) findViewById(R.id.moratorio);
        iva = (EditText) findViewById(R.id.iva);
        btn_abono=(Button)findViewById(R.id.btn_abono);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);

        //Consulta be_sps_catalogo_cuentasd(3) - Web service CatalogoCuentas
        CatalogoCuentas cuentas=new CatalogoCuentas();

        ArrayList<Cuenta> datos = cuentas.consumir2(3);
        final ArrayAdapter<Cuenta> adapter  = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        cuenta = (Spinner)findViewById(R.id.cuenta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);


        //Consulta Spinner creditoid
        CreditosExistentes creditos = new CreditosExistentes();

        final ArrayList<CreditosE> datos1 = creditos.consumir2 (1);
        final ArrayAdapter<CreditosE> adapter1 = new ArrayAdapter<CreditosE>(this,
                android.R.layout.simple_spinner_item, datos1);

        creditoid = (Spinner)findViewById(R.id.creditoid);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creditoid.setFocusable(true);
        creditoid.setAdapter(adapter1);

        creditoid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

             /*   String items=creditoid.getSelectedItem().toString();
                Log.i("Selected item : ",items);*/
                /*ArrayList<CreditoD> ldc = credito.consume (1);
                for(int i=0;i<ldc.size();i++){
                    total.setText(ldc.get(i).getTotal());
                }*/

                int id= (int) creditoid.getSelectedItemId();

                int creditoid=datos1.get(id).getId_credito();
                //System.out.println("id credito es "+creditoid);

                sps_detalles(creditoid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        //Mensaje al abrir el formulario
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Sigue los pasos para realizar el abono", Toast.LENGTH_SHORT);
        toast1.show();

        //Barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_abono.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Cuenta c= adapter.getItem(cuenta.getSelectedItemPosition());
                        CreditosE cr= adapter1.getItem(creditoid.getSelectedItemPosition());

                        String tota = total.getText().toString();
                        String capita = capital.getText().toString();
                        String intere = interes.getText().toString();
                        String moratori = moratorio.getText().toString();
                        String iv = iva.getText().toString();
                        String cvo = cvoperacion.getText().toString();

                        if (cvo.isEmpty()){
                            cvoperacion.setError("Campo vacio");
                            cvoperacion.requestFocus();
                        }else {

                            InvocarAbono(c.getIdcuenta(), cr.getId_credito(), tota, capita,
                                    intere, moratori, iv, cvo);

                            total.setText("");
                            capital.setText("");
                            interes.setText("");
                            moratorio.setText("");
                            iva.setText("");
                            cvoperacion.setText("");
                        }
                    }
                });

    }


    private void InvocarAbono(String cuenta, int creditoid, String total,  String capital,
                              String interes, String moratorio, String iva, String cvoperacion) {

        try {

            Log.d("InvocarAbono ", String.valueOf(iva));
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("cliente",Globals.getCuenta());
            Request.addProperty("idcliente",Globals.getIdusuario());
            Request.addProperty("cuenta", cuenta);
            Request.addProperty("creditoid", creditoid);
            Request.addProperty("total", total);
            Request.addProperty("capital", capital);
            Request.addProperty("interes", interes);
            Request.addProperty("moratorio", moratorio);
            Request.addProperty("iva", iva);
            Request.addProperty("cvoperacion", cvoperacion);

            Log.d("Request ", Request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(Request);
            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, envelope);

            SoapObject respuesta = (SoapObject) envelope.bodyIn;

            String r = respuesta.getProperty(1).toString();
            resultado.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

            }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sps_detalles(int idcredito){
        CreditoDetallado n=new CreditoDetallado();
        SoapObject credito = n.spsabonosCredito(idcredito);

        for (int i = 0; i < credito.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    credito.getProperty(i);

            total.setText(result2.getProperty("total").toString());
            capital.setText(result2.getProperty("capital").toString());
            interes.setText(result2.getProperty("interes").toString());
            moratorio.setText(result2.getProperty("moratorio").toString());
            iva.setText(result2.getProperty("iva").toString());

            }
    }

}