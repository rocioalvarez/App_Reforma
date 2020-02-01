package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;

/**
 * Created by estadias2017 on 11/03/17.
 */

@SuppressWarnings("serial")
public class Ping extends AppCompatActivity implements Serializable {

    public TextView resultado;
    public EditText ping;
    public Button   btn_confirmar;


    private String SOAP_ACTION = "";
    private String METHOD_NAME = "ValidarPing";
    private String NAMESPACE = "http://webservices/";
    //private String URL = "http://192.168.1.118:8080/AppMovilReforma/ValidaPing_ws?wsdl";
    private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/ValidaPing_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        Globals.getCuenta();
        System.out.println("cuenta "+Globals.getCuenta());
        resultado = (TextView) findViewById(R.id.resultado);
        ping = (EditText) findViewById(R.id.ping);
        btn_confirmar = (Button) findViewById(R.id.btn_confirmar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("Ejecutado2");

        //Mensaje que muestra al abrir
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Se ha enviado un codigo a su correo", Toast.LENGTH_SHORT);
        toast1.show();


        btn_confirmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String pi = ping.getText().toString();
                String[] res= InvocarPing(pi);

                if(res[0].equals("0")) {
                    resultado.setText(res[1]);

                }else if(res[0].equals("1")){
                    int id=Integer.parseInt(res[2]);
                    System.out.println("correcto");
                    Globals.setIdusuario(id);

                    Intent intent3 = new Intent(v.getContext(), MainActivity.class);
                    startActivityForResult(intent3, 0);


                }
            }
        });
    }


    private String[] InvocarPing(String ping) {
        System.out.println("Ejecutado3");

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("usuario", Globals.getCuenta());
            request.addProperty("clave", ping);
            System.out.println("usus" + ping);

            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            srl.setOutputSoapObject(request);
            System.out.println("Ejecutado4");

            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, srl);
            System.out.println("Ejecutado7");
            SoapObject so = (SoapObject) srl.bodyIn;
            String result=so.toString();
            System.out.println("Ejecutado5 "+result);
            String r = "";
            String r1 = "";
            String r2 = "";

            r = so.getProperty(0).toString();
            r1 = so.getProperty(1).toString();
            r2 = so.getProperty(2).toString();
            System.out.println("resultado " + r);
            System.out.println("resultado 1" + r2);

            Toast.makeText(this, String.valueOf(r1), Toast.LENGTH_LONG).show();

            String[]  res = {r, r1,r2};

            return res;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
