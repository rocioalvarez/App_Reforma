package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by SOMA-YAZMIN on 08/12/2017.
 */

public class FormRetiroInteres extends AppCompatActivity {

    public EditText descripcion, monto, f_inicial, f_final, interes, cvoperacion;
    public Button btn_enviar;
    public TextView result;
    Toolbar toolbar;

    public String SOAP_ACTION = "";
    public String NAMESPACE = "";
    public String METHOD_NAME = "";
    public String URL = "";

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_form_retiro_int);
        descripcion = (EditText) findViewById(R.id.descripcion);
        monto = (EditText) findViewById(R.id.monto);
        f_inicial = (EditText) findViewById(R.id.f_inicial);
        f_final = (EditText) findViewById(R.id.f_final);
        interes = (EditText) findViewById(R.id.interes);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        result = (TextView) findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();

        String des = extras.getString("inversion");
        String mon = extras.getString("deposito");
        String fei = extras.getString("fecha");
        String fef = extras.getString("vencimiento");
        String in = extras.getString("interes");

        descripcion.setText(des);
        monto.setText(mon);
        f_inicial.setText(fei);
        f_final.setText(fef);
        interes.setText(in);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FormRetiroInteres.this);
                builder.setMessage("Estas a punto de hacer un retiro ¿Desea continuar?")
                        .setTitle("Alerta")
                        .setCancelable(true)
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                })
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String des = descripcion.getText().toString();
                                        String mon = monto.getText().toString();
                                        String fei = f_inicial.getText().toString();
                                        String fef = f_final.getText().toString();
                                        String in = interes.getText().toString();

                                        String cv = cvoperacion.getText().toString();

                                        if (cv.isEmpty()){
                                            cvoperacion.setError("Campo vacio");
                                            cvoperacion.requestFocus();
                                        }else {

                                            Retirar(des, mon, fei, fef, in, cv);
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void Retirar(String descripcion, String monto, String f_inicial, String f_final,
                        String interes, String cvoperacion){
        try{
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(Request);
            Request.addProperty("descripcion", descripcion);
            Request.addProperty("monto", monto);
            Request.addProperty("f_inicial", f_inicial);
            Request.addProperty("f_final", f_final);
            Request.addProperty("interes", interes);
            Request.addProperty("cvoperacion", cvoperacion);

            Log.d("Request ", Request.toString());
            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(Request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);
            System.out.print(HttpTransport.getServiceConnection()); //imprime en consola la conexion

            SoapObject so = (SoapObject) envelope.bodyIn;

            String r = "";
            r = so.getProperty(1).toString();
            result.setText(r);

            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
