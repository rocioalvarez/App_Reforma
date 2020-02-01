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

import com.soma.estadias2017.app_002.Huella.FingerprintActivity;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


@SuppressWarnings("serial")
public class Login extends AppCompatActivity implements Serializable {

    public TextView resultado;
    public EditText usuario, cv_acceso;
    public Button btn_ingresar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "Login";
    String NAMESPACE = "http://webservices/";
   // String URL = "http://192.168.1.118:8080/AppMovilReforma/Login_ws?wsdl";
   String URL = "https://mail.reformasofipo.com/AplicacionMovilws/Login_ws?wsdl";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        resultado = findViewById(R.id.resultado);
        usuario = findViewById(R.id.usuario);
        cv_acceso = findViewById(R.id.cv_acceso);
        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        btn_ingresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String usu = usuario.getText().toString();
                String cv = cv_acceso.getText().toString();
                int op = 1;
                String[] res = InvocarLogin(usu, cv, op);
                String r = res[0];

                if (res[0].equals("0")) {
                    resultado.setText(res[1]);

                } else if (res[0].equals("1")) {
                    System.out.println("correcto");

                    Globals.setCuenta(usuario.getText().toString());

                    Intent intent3 = new Intent(v.getContext(), Ping.class);
                    startActivityForResult(intent3, 0);

                }
            }
        });
    }


    private String[] InvocarLogin(String usuario, String cv_acceso, int opcion) {

        System.out.println("Ejecutado3");

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("usuario", usuario);
            request.addProperty("cv_acceso", cv_acceso);
            //request.addProperty("cv_acceso", codifica(cv_acceso));
            request.addProperty("opc", opcion);
            System.out.println("usus " + usuario);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, envelope);

            SoapObject so = (SoapObject) envelope.bodyIn;

            String r = "";
            String r1 = "";
            String r2 = "";

            r = so.getProperty(0).toString();
            r1 = so.getProperty(1).toString();
            r2 = so.getProperty(2).toString();
            System.out.println("resultado " + r);
            System.out.println("resultado 1" + r1);
            System.out.println("resultado 2" + r2);


            Toast.makeText(this, String.valueOf(r1), Toast.LENGTH_LONG).show();

            String[] res = {r, r1, r2};

            return res;

        } catch (Exception e) {
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
}